package controller;

import dao.ProductDAO;
import model.Product;
import util.ObjectMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ProductController implements Controller {

    private ObjectMethod methodObj = new ObjectMethod();
    private List<Product> listProduct = new ArrayList<Product>();
    private Long id = null;

    @Override
    public String runController(HttpServletRequest req, HttpServletResponse res) throws Exception {
        Connection connection = (Connection) req.getAttribute("connection");
        HttpSession session = req.getSession(true);
        String action = (String) req.getParameter("action");
        ProductDAO dao = new ProductDAO(connection);
        listProduct = dao.getList();
        req.setAttribute("listproduct", listProduct);
        req.setAttribute("listproductSize", listProduct.size());

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
                resultado = newProduct(session, req, dao);
                break;
            case "edit":
                resultado = editProduct(session, req, dao);
                break;
            case "view":
                resultado = "/WEB-INF/jsp/product/product.jsp";
                break;
            case "viewedit":
                resultado = viewEdit(session, req, dao, id);
                break;
            case "delete":
                resultado = deleteProduct(session, req, dao, id);
                break;
        }

        return resultado;
    }

    public String newProduct(HttpSession session, HttpServletRequest req, ProductDAO dao) throws Exception {
        try {
            Product product = new Product();

            product.setName((String) methodObj.verifyNullEmpty(req, "txtName", "Nome Incorreto", false));
            product.setType((String) methodObj.verifyNullEmpty(req, "txtType", "Tipo Incorreto", false));

            dao.add(product);
            return new RedirectLogic().redirect(
                    req,
                    "Product",
                    "view"
            );

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String editProduct(HttpSession session, HttpServletRequest req, ProductDAO dao) throws Exception {
        try {
            Product product = new Product();

            try {
                product.setId(Long.parseLong((String) methodObj.verifyNullEmpty(req, "txtId", "Nome do Erro", false)));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Nome do Erro");
            }
            product.setName((String) methodObj.verifyNullEmpty(req, "txtName", "Nome Incorreto", false));
            product.setType((String) methodObj.verifyNullEmpty(req, "txtType", "Tipo Incorreto", false));

            dao.edit(product);
            return new RedirectLogic().redirect(
                    req,
                    "Product",
                    "view"
            );

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String deleteProduct(HttpSession session, HttpServletRequest req, ProductDAO dao, Long id) throws Exception {
        dao.delete(id);
        listProduct = dao.getList();
        req.setAttribute("listproduct", listProduct);
        req.setAttribute("listproductSize", listProduct.size());
        return "/WEB-INF/jsp/product/product.jsp";
    }

    public String viewEdit(HttpSession session, HttpServletRequest req, ProductDAO dao, Long id) throws Exception {
        Product productEdit = dao.search(id);
        boolean edit = true;
        req.setAttribute("productEdit", productEdit);
        req.setAttribute("edit", edit);
        return "/WEB-INF/jsp/product/product.jsp";
    }
}
