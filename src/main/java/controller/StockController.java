package controller;

import dao.*;
import model.Barcode;
import model.Stock;
import model.TypeStock;
import util.ObjectMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class StockController implements Controller {

    private ObjectMethod methodObj = new ObjectMethod();
    private List<Stock> listStock = new ArrayList<Stock>();
    private Long id = null;
    private boolean barcode;

    @Override
    public String runController(HttpServletRequest req, HttpServletResponse res) throws Exception {
        Connection connection = (Connection) req.getAttribute("connection");
        HttpSession session = req.getSession(true);
        String action = (String) req.getParameter("action");
        StockDAO dao = new StockDAO(connection);
        CustomerDAO daoCustomer = new CustomerDAO(connection);
        BarcodeDAO daoBarcode = new BarcodeDAO(connection);
        PriceDAO daoPrice = new PriceDAO(connection);
        barcode = false;
        req.setAttribute("barcode", barcode);
        listStock = dao.getList();
        req.setAttribute("liststock", listStock);
        req.setAttribute("liststockSize", listStock.size());
        List<Long> listBarcodeStock = daoBarcode.getListIdStockBarcode();
        req.setAttribute("listBarcodeStock", listBarcodeStock);
        req.setAttribute("listBarcodeStockSize", listBarcodeStock.size());

        if (action == null) {
            action = "view";
        } else {
            String[] divAction = action.split("_");
            if (divAction.length > 1) {
                action = divAction[0];
                id = Long.valueOf(divAction[1]);
            } else {
                action = divAction[0];
            }
        }

        String resultado = "";
        switch (action) {
            case "new":
                resultado = newStock(session, req, dao, daoBarcode, daoCustomer, daoPrice);
                break;
            case "edit":
                resultado = editStock(session, req, dao, daoBarcode, daoCustomer, daoPrice);
                break;
            case "barcode":
                resultado = barcodeStock(session, req, dao, daoBarcode, id);
                break;
            case "view":
                resultado = "/WEB-INF/jsp/product/stock.jsp";
                break;
            case "viewedit":
                resultado = viewEdit(session, req, dao, id);
                break;
            case "delete":
                resultado = deleteStock(session, req, dao, id);
                break;
        }

        return resultado;
    }

    public String newStock(HttpSession session, HttpServletRequest req, StockDAO dao, BarcodeDAO daoBarcode,
                           CustomerDAO daoCustomer, PriceDAO daoPrice) throws Exception {
        try {
            Stock stock = new Stock();


            try {
                Long idCustomer = Long.parseLong((String) methodObj.verifyNullEmpty(req, "txtCustomer", "Customer", false));
                stock.setCustomer(daoCustomer.search(idCustomer));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Nome do Erro");
            }

            try {
                Long idPrice = Long.parseLong((String) methodObj.verifyNullEmpty(req, "txtPrice", "Price", false));
                stock.setPrice(daoPrice.search(idPrice));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Nome do Erro");
            }

            String amount = (String) methodObj.verifyNullEmpty(req, "txtAmount", "Preco", true);
            if (amount != null) {
                stock.setAmount(Double.parseDouble(amount));
            } else {
                stock.setAmount((double) 0);
            }
            String type = req.getParameter("txtType");
            stock.setType(TypeStock.valueOf(type));

            Long id = dao.addLong(stock);

            Barcode barcode = new Barcode();
            stock.setId(id);
            barcode.setStock(stock);
            for (int i = 0; i < stock.getAmount(); i++) {
                daoBarcode.add(barcode);
            }


            return new RedirectLogic().redirect(
                    req,
                    "Stock",
                    "view"
            );

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String editStock(HttpSession session, HttpServletRequest req, StockDAO dao, BarcodeDAO daoBarcode,
                            CustomerDAO daoCustomer, PriceDAO daoPrice) throws Exception {
        try {
            Stock stock = new Stock();

            try {
                stock.setId(Long.parseLong((String) methodObj.verifyNullEmpty(req, "txtId", "Nome do Erro", false)));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Nome do Erro");
            }

            try {
                Long idCustomer = Long.parseLong((String) methodObj.verifyNullEmpty(req, "txtCustomer", "Customer", false));
                stock.setCustomer(daoCustomer.search(idCustomer));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Nome do Erro");
            }

            try {
                Long idPrice = Long.parseLong((String) methodObj.verifyNullEmpty(req, "txtPrice", "Price", false));
                stock.setPrice(daoPrice.search(idPrice));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Nome do Erro");
            }

            String amount = (String) methodObj.verifyNullEmpty(req, "txtAmount", "Preco", true);
            if (amount != null) {
                stock.setAmount(Double.parseDouble(amount));
            } else {
                stock.setAmount((double) 0);
            }
            String type = req.getParameter("txtType");
            stock.setType(TypeStock.valueOf(type));

            daoBarcode.deleteRow(stock.getId());

            Barcode barcode = new Barcode();
            barcode.setStock(stock);
            for (int i = 0; i < stock.getAmount(); i++) {
                daoBarcode.add(barcode);
            }

            dao.edit(stock);

            return new RedirectLogic().redirect(
                    req,
                    "Stock",
                    "view"
            );

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String deleteStock(HttpSession session, HttpServletRequest req, StockDAO dao, Long id) throws Exception {
        dao.delete(id);
        listStock = dao.getList();
        req.setAttribute("liststock", listStock);
        req.setAttribute("liststockSize", listStock.size());
        return "/WEB-INF/jsp/product/stock.jsp";
    }

    public String viewEdit(HttpSession session, HttpServletRequest req, StockDAO dao, Long id) throws Exception {
        Stock stockEdit = dao.search(id);
        boolean edit = true;
        req.setAttribute("stockEdit", stockEdit);
        req.setAttribute("edit", edit);
        return "/WEB-INF/jsp/product/stock.jsp";
    }

    public String barcodeStock(HttpSession session, HttpServletRequest req, StockDAO dao, BarcodeDAO daoBarcode, Long id) throws Exception {
        Stock stock = dao.search(id);
        barcode = true;
        req.setAttribute("stockBarcode", stock);
        req.setAttribute("barcodeStock", barcode);

        List<Barcode> listBarcode = daoBarcode.getListByStock(stock);
        req.setAttribute("listBarcode", listBarcode);
        req.setAttribute("listBarcodeSize", listBarcode.size());
        return "/WEB-INF/jsp/product/stock.jsp";
    }
}
