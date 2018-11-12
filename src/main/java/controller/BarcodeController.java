package controller;

import dao.BarcodeDAO;
import dao.CustomerDAO;
import dao.PriceDAO;
import dao.StockDAO;
import model.Barcode;
import model.Stock;
import model.TypeStock;
import util.ObjectMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.*;

public class BarcodeController implements Controller {

    private ObjectMethod methodObj = new ObjectMethod();
    private List<Barcode> listBarcode = new ArrayList<Barcode>();
    private Long id = null;

    @Override
    public String runController(HttpServletRequest req, HttpServletResponse res) throws Exception {
        Connection connection = (Connection) req.getAttribute("connection");
        HttpSession session = req.getSession(true);
        String action = (String) req.getParameter("action");
        BarcodeDAO dao = new BarcodeDAO(connection);
        StockDAO daoStock = new StockDAO(connection);
        listBarcode = dao.getList();
        req.setAttribute("listbarcode", listBarcode);
        req.setAttribute("listbarcodeSize", listBarcode.size());

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
                resultado = newBarcode(session, req, dao, daoStock);
                break;
            case "edit":
                resultado = editBarcode(session, req, dao, daoStock);
                break;
            case "view":
                resultado = "/jsp/product/barcode.jsp";
                break;
            case "viewedit":
                resultado = viewEdit(session, req, dao, id);
                break;
            case "delete":
                resultado = deleteBarcode(session, req, dao, id);
                break;
        }

        return resultado;
    }

    public String newBarcode(HttpSession session, HttpServletRequest req, BarcodeDAO dao,
                             StockDAO daoStock) throws Exception {
        try {

            String arrayMultiple = req.getParameter("arrayMultiple");
            String arrayMultipleIdBarcode = req.getParameter("arrayMultipleIdBarcode");

            String[] arraySplit = arrayMultiple.split(",");
            String[] arraySplitId = arrayMultipleIdBarcode.split(",");
            Map<Long,String> listBarcode = new HashMap<>();
            for (int x = 0; x < arraySplitId.length; x++){
                listBarcode.put(Long.valueOf(arraySplitId[x]),arraySplit[x]);
            }

            Long idStock = null;
            try {
                idStock = Long.parseLong((String) methodObj.verifyNullEmpty(req, "txtStock", "Stock", false));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Nome do Erro");
            }

            for (Map.Entry<Long, String> barcodeEntry : listBarcode.entrySet()) {
                Barcode barcode = new Barcode();
                barcode.setId(barcodeEntry.getKey());
                barcode.setStock(daoStock.search(idStock));
                barcode.setBarcode(barcodeEntry.getValue());
                dao.edit(barcode);
            }

            return new RedirectLogic().redirect(
                    req,
                    "Barcode",
                    "view"
            );

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String editBarcode(HttpSession session, HttpServletRequest req, BarcodeDAO dao,
                              StockDAO daoStock) throws Exception {
        try {
            Barcode barcode = new Barcode();

            try {
                barcode.setId(Long.parseLong((String) methodObj.verifyNullEmpty(req, "txtId", "Nome do Erro", false)));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Nome do Erro");
            }

            try {
                Long idStock = Long.parseLong((String) methodObj.verifyNullEmpty(req, "txtStock", "Stock", false));
                barcode.setStock(daoStock.search(idStock));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Nome do Erro");
            }

            barcode.setBarcode((String) methodObj.verifyNullEmpty(req, "txtBarcode", "Barcode", true));
            dao.edit(barcode);
            return new RedirectLogic().redirect(
                    req,
                    "Barcode",
                    "view"
            );

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String deleteBarcode(HttpSession session, HttpServletRequest req, BarcodeDAO dao, Long id) throws Exception {
        dao.delete(id);
        listBarcode = dao.getList();
        req.setAttribute("listbarcode", listBarcode);
        req.setAttribute("listbarcodeSize", listBarcode.size());
        return "/jsp/product/barcode.jsp";
    }

    public String viewEdit(HttpSession session, HttpServletRequest req, BarcodeDAO dao, Long id) throws Exception {
        Barcode barcodeEdit = dao.search(id);
        boolean edit = true;
        req.setAttribute("barcodeEdit", barcodeEdit);
        req.setAttribute("edit", edit);
        return "/jsp/product/barcode.jsp";
    }
}
