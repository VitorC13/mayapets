package controller;

import dao.FndUserDAO;
import jndi.ConnectionFactory;
import model.FndUser;
import util.BCrypt;
import util.ObjectField;

import javax.persistence.EntityNotFoundException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {


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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);

        Connection connection = (Connection) req.getAttribute("connection");
        HttpSession session = req.getSession(true);
        String action = req.getParameter("action");
        FndUserDAO dao = new FndUserDAO(connection);
        try {
            switch (action) {
                case "auth":
                    authenticLogin(req, resp, dao);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void authenticLogin(HttpServletRequest req, HttpServletResponse resp, FndUserDAO dao) throws Exception {
        try {

            FndUser fndUser = dao.autenthicBCrypt((String) verifyNullEmpty(req, "txtLogin", "Nome do Erro", false));
            if (fndUser != null) {
                String hashed = fndUser.getPassword();
                boolean passwordCorrect = BCrypt.checkpw((String) verifyNullEmpty(req, "txtPassword", "Nome do Erro", false), hashed);
                if (passwordCorrect) {
                    RequestDispatcher dispatcher = req.getRequestDispatcher("principal.jsp");
                    req.setAttribute("user", fndUser);
                    dispatcher.forward(req, resp);
                } else {
                    RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
                    req.setAttribute("msg", "Login ou Senha Incorreto!");
                    rd.forward(req, resp);
                }
            }


        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
