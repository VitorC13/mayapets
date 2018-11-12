package controller;

import dao.CollectionDAO;
import dao.PriceDAO;
import dao.ProductDAO;
import model.Price;
import model.Product;
import util.ObjectMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class PriceController implements Controller {

    private ObjectMethod methodObj = new ObjectMethod();
    private List<Price> listPrice = new ArrayList<Price>();
    private Long id = null;

    @Override
    public String runController(HttpServletRequest req, HttpServletResponse res) throws Exception {
        Connection connection = (Connection) req.getAttribute("connection");
        HttpSession session = req.getSession(true);
        String action = (String) req.getParameter("action");
        PriceDAO dao = new PriceDAO(connection);
        ProductDAO daoProduct = new ProductDAO(connection);
        CollectionDAO daoCollection = new CollectionDAO(connection);
        listPrice = dao.getList();
        req.setAttribute("listprice", listPrice);
        req.setAttribute("listpriceSize", listPrice.size());

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
                resultado = newPrice(session, req, dao, daoProduct, daoCollection);
                break;
            case "edit":
                resultado = editPrice(session, req, dao, daoProduct, daoCollection);
                break;
            case "view":
                resultado = "/jsp/product/price.jsp";
                break;
            case "viewedit":
                resultado = viewEdit(session, req, dao, id);
                break;
            case "delete":
                resultado = deletePrice(session, req, dao, id);
                break;
        }

        return resultado;
    }

    public String newPrice(HttpSession session, HttpServletRequest req, PriceDAO dao,
                           ProductDAO daoProduct, CollectionDAO daoCollection) throws Exception {
        try {
            Price price = new Price();

            priceP(req, price);
            priceVar(req, price);
            priceAtac(req, price);

            try {
                Long idProduct = Long.parseLong((String) methodObj.verifyNullEmpty(req, "txtProduct", "Nome do Erro", false));
                price.setProduct(daoProduct.search(idProduct));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Nome do Erro");
            }

            try {
                Long idCollection = Long.parseLong((String) methodObj.verifyNullEmpty(req, "txtCollection", "Nome do Erro", false));
                price.setCollection(daoCollection.search(idCollection));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Nome do Erro");
            }

            dao.add(price);
            return new RedirectLogic().redirect(
                    req,
                    "Price",
                    "view"
            );

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String editPrice(HttpSession session, HttpServletRequest req, PriceDAO dao,
                            ProductDAO daoProduct, CollectionDAO daoCollection) throws Exception {
        try {
            Price price = new Price();

            try {
                price.setId(Long.parseLong((String) methodObj.verifyNullEmpty(req, "txtId", "Nome do Erro", false)));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Nome do Erro");
            }

            priceP(req, price);
            priceVar(req, price);
            priceAtac(req, price);

            try {
                Long idProduct = Long.parseLong((String) methodObj.verifyNullEmpty(req, "txtProduct", "Nome do Erro", false));
                price.setProduct(daoProduct.search(idProduct));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Nome do Erro");
            }

            try {
                Long idCollection = Long.parseLong((String) methodObj.verifyNullEmpty(req, "txtCollection", "Nome do Erro", false));
                price.setCollection(daoCollection.search(idCollection));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Nome do Erro");
            }

            dao.edit(price);
            return new RedirectLogic().redirect(
                    req,
                    "Price",
                    "view"
            );

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String deletePrice(HttpSession session, HttpServletRequest req, PriceDAO dao, Long id) throws Exception {
        dao.delete(id);
        listPrice = dao.getList();
        req.setAttribute("listprice", listPrice);
        req.setAttribute("listpriceSize", listPrice.size());
        return "/jsp/product/price.jsp";
    }

    public String viewEdit(HttpSession session, HttpServletRequest req, PriceDAO dao, Long id) throws Exception {
        Price priceEdit = dao.search(id);
        boolean edit = true;
        req.setAttribute("priceEdit", priceEdit);
        req.setAttribute("edit", edit);
        return "/jsp/product/price.jsp";
    }

    private void priceAtac(HttpServletRequest req, Price price) throws Exception {
        try {
            String priceAtac = (String) methodObj.verifyNullEmpty(req, "txtPriceAtac", "Preco", true);
            if (priceAtac != null) {
                price.setPriceAtc(Double.parseDouble(priceAtac));
            } else {
                price.setPriceAtc((double) 0);
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Preco Invalida.");
        }
    }

    private void priceVar(HttpServletRequest req, Price price) throws Exception {
        try {
            String priceVar = (String) methodObj.verifyNullEmpty(req, "txtPriceVar", "Preco", true);
            if (priceVar != null) {
                price.setPriceVar(Double.parseDouble(priceVar));
            } else {
                price.setPriceVar((double) 0);
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Preco Invalido.");
        }
    }

    private void priceP(HttpServletRequest req, Price price) throws Exception {
        try {
            String priceP = (String) methodObj.verifyNullEmpty(req, "txtPrice", "Preco", true);
            if (price != null) {
                price.setPrice(Double.parseDouble(priceP));
            } else {
                price.setPrice((double) 0);
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Preco Invalido.");
        }
    }
}
