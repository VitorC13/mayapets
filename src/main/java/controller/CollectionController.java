package controller;

import dao.CollectionDAO;
import model.Collection;
import util.ObjectMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CollectionController implements Controller {

    private ObjectMethod methodObj = new ObjectMethod();
    private List<Collection> listCollection = new ArrayList<Collection>();
    private Long id = null;

    @Override
    public String runController(HttpServletRequest req, HttpServletResponse res) throws Exception {
        Connection connection = (Connection) req.getAttribute("connection");
        HttpSession session = req.getSession(true);
        String action = (String) req.getParameter("action");
        CollectionDAO dao = new CollectionDAO(connection);
        listCollection = dao.getList();
        req.setAttribute("listcollection", listCollection);
        req.setAttribute("listcollectionSize", listCollection.size());

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
                resultado = newCollection(session, req, dao);
                break;
            case "edit":
                resultado = editCollection(session, req, dao);
                break;
            case "view":
                resultado = "/jsp/product/collection.jsp";
                break;
            case "viewedit":
                resultado = viewEdit(session, req, dao, id);
                break;
            case "delete":
                resultado = deleteCollection(session, req, dao, id);
                break;
        }

        return resultado;
    }

    public String newCollection(HttpSession session, HttpServletRequest req, CollectionDAO dao) throws Exception {
        try {
            Collection collection = new Collection();

            collection.setName((String) methodObj.verifyNullEmpty(req, "txtName", "Nome Incorreto", false));

            dao.add(collection);
            return new RedirectLogic().redirect(
                    req,
                    "Collection",
                    "view"
            );

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String editCollection(HttpSession session, HttpServletRequest req, CollectionDAO dao) throws Exception {
        try {
            Collection collection = new Collection();

            try {
                collection.setId(Long.parseLong((String) methodObj.verifyNullEmpty(req, "txtId", "Nome do Erro", false)));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Nome do Erro");
            }
            collection.setName((String) methodObj.verifyNullEmpty(req, "txtName", "Nome Incorreto", false));

            dao.edit(collection);
            return new RedirectLogic().redirect(
                    req,
                    "Collection",
                    "view"
            );

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String deleteCollection(HttpSession session, HttpServletRequest req, CollectionDAO dao, Long id) throws Exception {
        dao.delete(id);
        listCollection = dao.getList();
        req.setAttribute("listcollection", listCollection);
        req.setAttribute("listcollectionSize", listCollection.size());
        return "/jsp/product/collection.jsp";
    }

    public String viewEdit(HttpSession session, HttpServletRequest req, CollectionDAO dao, Long id) throws Exception {
        Collection collectionEdit = dao.search(id);
        boolean edit = true;
        req.setAttribute("collectionEdit", collectionEdit);
        req.setAttribute("edit", edit);
        return "/jsp/product/collection.jsp";
    }
}
