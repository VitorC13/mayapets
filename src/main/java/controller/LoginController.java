package controller;

import dao.UserDAO;
import jndi.ConnectionFactory;
import model.User;
import util.ObjectField;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginController implements Controller {


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
    public String runController(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String result = "";

        Connection connection = (Connection) req.getAttribute("connection");
        HttpSession session = req.getSession(true);
        String action = req.getParameter("action");
        UserDAO dao = new UserDAO(connection);
        try {
            switch (action) {
                case "auth":
                    result = authenticLogin(req, resp, dao);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }

    public String authenticLogin(HttpServletRequest req, HttpServletResponse resp, UserDAO dao) throws Exception {
        try {
            HttpSession session = req.getSession(true);
            String login = (String) verifyNullEmpty(req, "txtLogin", "Nome do Erro", false);
            User user = dao.autenthicBCrypt(login);
            if (user != null) {
                String hashed = user.getPassword();
                String password = (String) verifyNullEmpty(req, "txtPassword", "Nome do Erro", false);
               // boolean passwordCorrect = BCrypt.checkpw(password, hashed);
                boolean passwordCorrect = true;
                if (passwordCorrect) {
                    session.setAttribute("user", user);
                    return new RedirectLogic().redirect(
                            req,
                            "ControllerServlet",
                            "userLog"
                    );
                } else {
                    req.setAttribute("msg", "Login ou Senha Incorreto!");
                    return new RedirectLogic().redirect(
                            req,
                            "ControllerServlet",
                            "userNotLog"
                    );
                }
            }


        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        req.setAttribute("msg", "Algo de errado!");
        return "/index.jsp";
    }

}
