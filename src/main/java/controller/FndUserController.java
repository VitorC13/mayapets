package controller;

import dao.FndUserDAO;
import jndi.ConnectionFactory;
import model.FndUser;
import util.ObjectField;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class FndUserController {

    protected void finalize() throws Throwable {
        System.out.println("Finalize is CALLED ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
        try {
            new ConnectionFactory();
            ConnectionFactory.openConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.finalize();
    }

    public boolean verifyBoolean(HttpServletRequest req, String field) {
        return ObjectField.nullBoolean(req, field);
    }

    public Object verifyNullEmpty(HttpServletRequest req, String field, String exception, boolean isNull) throws Exception {
        return ObjectField.getObject(req, field, exception, isNull);
    }

    public String executa(HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        Connection connection = (Connection) req.getAttribute("connection");
        HttpSession session = req.getSession(true);
        String action = (String) req.getParameter("action");
        FndUserDAO dao = new FndUserDAO(connection);
        List<FndUser> listfnduser = dao.getList();
        req.setAttribute("fnduserrequest", listfnduser);
        req.setAttribute("fnduserSize", listfnduser.size());
        String resultado = "";
        switch (action) {
            case "new":
                resultado = newFndUser(session, req, connection, dao);
                break;
            case "edit":
                resultado = editFndUser(session, req, connection, dao);
                break;
            case "view":
                resultado = viewFndUser(session, req, connection);
                break;
            case "view-edit":
                resultado = "/WEB-INF/jsp/finance/edit_traveladvances.jsp";
                break;
        }

        return resultado;
    }

    public String newFndUser(HttpSession session, HttpServletRequest req, Connection connection, FndUserDAO dao) throws Exception {
        try {
            FndUser fnduser = new FndUser();
            FndUser usuarioLogado = (FndUser) session.getAttribute("usuarioLogado");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            fnduser.setId(usuarioLogado.getId());

            fnduser.setName((String) verifyNullEmpty(req, "txtname", "Nome do Erro", false));

            fnduser.setPassword((String) verifyNullEmpty(req, "txtpassword", "Nome do Erro", false));

            fnduser.setLogin((String) verifyNullEmpty(req, "txtlogin", "Nome do Erro", false));

            fnduser.setCpf((String) verifyNullEmpty(req, "txtcpf", "Nome do Erro", true));

            fnduser.setEmail((String) verifyNullEmpty(req, "txtemail", "Nome do Erro", true));

            fnduser.setAddress((String) verifyNullEmpty(req, "txtaddress", "Nome do Erro", true));

            try {
                fnduser.setIdCustomer(Long.parseLong((String) verifyNullEmpty(req, "txtid_customer", "Nome do Erro", false)));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Nome do Erro");
            }
            Long daoNumberRef = dao.add(fnduser);


            return new RedirectLogic().redirect(
                    req,
                    "FndUserController",
                    "fnd",
                    "18",
                    "action=view&numberRef=" + daoNumberRef //Actions gerais
            );


        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String editFndUser(HttpSession session, HttpServletRequest req, Connection connection, FndUserDAO dao) throws Exception {
        try {
            FndUser fnduser = new FndUser();
            FndUser usuarioLogado = (FndUser) session.getAttribute("usuarioLogado");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            try {
                fnduser.setId(Long.parseLong((String) verifyNullEmpty(req, "txtid", "Nome do Erro", false)));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Nome do Erro");
            }

            fnduser.setName((String) verifyNullEmpty(req, "txtname", "Nome do Erro", false));

            fnduser.setPassword((String) verifyNullEmpty(req, "txtpassword", "Nome do Erro", false));

            fnduser.setLogin((String) verifyNullEmpty(req, "txtlogin", "Nome do Erro", false));

            fnduser.setCpf((String) verifyNullEmpty(req, "txtcpf", "Nome do Erro", true));

            fnduser.setEmail((String) verifyNullEmpty(req, "txtemail", "Nome do Erro", true));

            fnduser.setAddress((String) verifyNullEmpty(req, "txtaddress", "Nome do Erro", true));

            try {
                fnduser.setIdCustomer(Long.parseLong((String) verifyNullEmpty(req, "txtid_customer", "Nome do Erro", false)));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Nome do Erro");
            }
            dao.edit(fnduser);
            return new RedirectLogic().redirect(
                    req,
                    "FndUserLogic",
                    "fnd",
                    "18",
                    "action=view&numberRef=" //Actions gerais
            );

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String viewFndUser(HttpSession session, HttpServletRequest req, Connection connection) throws Exception {

        FndUserDAO dao2 = new FndUserDAO(connection);
        FndUser fnduser = dao2.search(Long.parseLong(req.getParameter("numberRef")));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        session.setAttribute("fnduser", fnduser);

        return "/WEB-INF/jsp/finance/view_fnduser.jsp?id=" + req.getParameter("numberRef");

    }

}