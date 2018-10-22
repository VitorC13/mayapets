package controller;

import dao.UserDAO;
import jndi.ConnectionFactory;
import model.User;
import util.BCrypt;
import util.ObjectField;
import util.ObjectMethod;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class UserController implements Controller {

    private ObjectMethod methodObj = new ObjectMethod();
    private List<User> listUser = new ArrayList<User>();

    @Override
    public String runController(HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        Connection connection = (Connection) req.getAttribute("connection");
        HttpSession session = req.getSession(true);
        String action = (String) req.getParameter("action");
        UserDAO dao = new UserDAO(connection);
        listUser = dao.getList();
        req.setAttribute("listuser", listUser);
        req.setAttribute("listuserSize", listUser.size());
        Long id = null;

        if (action == null) {
            action = "view";
        } else {
            String[] divAction = action.split("_");
            if (divAction.length > 1) {
                action = divAction[0];
                id = Long.valueOf(divAction[1]);
            }else{
                action = divAction[0];
            }
        }

        String resultado = "";
        switch (action) {
            case "new":
                resultado = newUser(session, req, connection, dao);
                break;
            case "edit":
                resultado = editUser(session, req, connection, dao);
                break;
            case "view":
                resultado = "/jsp/fnd/user.jsp";
                break;
            case "delete":
                resultado = deleteUser(session, req, connection, dao, id);
                break;
        }

        return resultado;
    }

    public String newUser(HttpSession session, HttpServletRequest req, Connection connection, UserDAO dao) throws Exception {
        try {
            User user = new User();
            User usuarioLogado = (User) session.getAttribute("user");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            user.setName((String) methodObj.verifyNullEmpty(req, "txtName", "Nome Incorreto", false));

            user.setPassword(BCrypt.hashpw(req.getParameter("txtpassword"), BCrypt.gensalt(12)));

            user.setLogin((String) methodObj.verifyNullEmpty(req, "txtLogin", "Login Incorreto", false));

            user.setCpf((String) methodObj.verifyNullEmpty(req, "txtCpf", "CPF Incorreto", true));

            user.setEmail((String) methodObj.verifyNullEmpty(req, "txtEmail", "Email Incorreto", true));

            user.setAddress((String) methodObj.verifyNullEmpty(req, "txtAddres", "Endereco incorreto", true));

            try {
                user.setIdCustomer(Long.parseLong((String) methodObj.verifyNullEmpty(req, "txtCustomer", "Empresa errada", false)));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Nome do Erro");
            }
            dao.add(user);


            return new RedirectLogic().redirect(
                    req,
                    "User",
                    "view"
            );


        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String editUser(HttpSession session, HttpServletRequest req, Connection connection, UserDAO dao) throws Exception {
        try {
            User user = new User();
            User usuarioLogado = (User) session.getAttribute("usuarioLogado");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            try {
                user.setId(Long.parseLong((String) methodObj.verifyNullEmpty(req, "txtid", "Nome do Erro", false)));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Nome do Erro");
            }

            user.setName((String) methodObj.verifyNullEmpty(req, "txtname", "Nome do Erro", false));

            user.setPassword((String) methodObj.verifyNullEmpty(req, "txtpassword", "Nome do Erro", false));

            user.setLogin((String) methodObj.verifyNullEmpty(req, "txtlogin", "Nome do Erro", false));

            user.setCpf((String) methodObj.verifyNullEmpty(req, "txtcpf", "Nome do Erro", true));

            user.setEmail((String) methodObj.verifyNullEmpty(req, "txtemail", "Nome do Erro", true));

            user.setAddress((String) methodObj.verifyNullEmpty(req, "txtaddress", "Nome do Erro", true));

            try {
                user.setIdCustomer(Long.parseLong((String) methodObj.verifyNullEmpty(req, "txtid_customer", "Nome do Erro", false)));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Nome do Erro");
            }
            dao.edit(user);
            return new RedirectLogic().redirect(
                    req,
                    "FndUserLogic",
                    "view"
            );

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String deleteUser(HttpSession session, HttpServletRequest req, Connection connection, UserDAO dao, Long id) throws Exception {
        dao.delete(id);
        listUser = dao.getList();
        req.setAttribute("listuser", listUser);
        req.setAttribute("listuserSize", listUser.size());
        return "/jsp/fnd/user.jsp";
    }
}