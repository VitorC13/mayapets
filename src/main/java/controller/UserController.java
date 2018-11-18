package controller;

import dao.CustomerDAO;
import dao.UserDAO;
import model.User;
import util.BCrypt;
import util.ObjectMethod;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class UserController implements Controller {

    private ObjectMethod methodObj = new ObjectMethod();
    private List<User> listUser = new ArrayList<User>();
    private Long id = null;

    @Override
    public String runController(HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        Connection connection = (Connection) req.getAttribute("connection");
        HttpSession session = req.getSession(true);
        String action = (String) req.getParameter("action");
        UserDAO dao = new UserDAO(connection);
        CustomerDAO daoCustomer = new CustomerDAO(connection);
        listUser = dao.getList();
        req.setAttribute("listuser", listUser);
        req.setAttribute("listuserSize", listUser.size());

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
                resultado = newUser(session, req, dao, daoCustomer);
                break;
            case "edit":
                resultado = editUser(session, req, dao, daoCustomer);
                break;
            case "view":
                resultado = "/WEB-INF/jsp/fnd/user.jsp";
                break;
            case "viewedit":
                resultado = viewEdit(session, req, dao, id);
                break;
            case "delete":
                resultado = deleteUser(session, req, dao, id);
                break;
            case "resetPassword":
                resultado = resetUserPassword(session, req, dao, id);
                break;
            case "checkLogin":
                resultado = checkLogin(session, req, dao);
                break;
        }

        return resultado;
    }

    public String newUser(HttpSession session, HttpServletRequest req, UserDAO dao, CustomerDAO daoCustomer) throws Exception {
        try {
            User user = new User();

            user.setName((String) methodObj.verifyNullEmpty(req, "txtName", "Nome Incorreto", false));

            user.setPassword(BCrypt.hashpw(req.getParameter("txtPassword"), BCrypt.gensalt(15)));

            user.setLogin((String) methodObj.verifyNullEmpty(req, "txtLogin", "Login Incorreto", false));

            String cpf = req.getParameter("txtCpf");
            String formatCpf = cpf.replace(".", "");
            formatCpf = formatCpf.replace("-", "");
            user.setCpf(formatCpf);

            user.setEmail((String) methodObj.verifyNullEmpty(req, "txtEmail", "Email Incorreto", true));

            user.setAddress((String) methodObj.verifyNullEmpty(req, "txtAddress", "Endereco incorreto", true));

            try {
                Long idCustomer = Long.parseLong((String) methodObj.verifyNullEmpty(req, "txtCustomer", "Empresa errada", false));
                user.setCustomer(daoCustomer.search(idCustomer));
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

    public String editUser(HttpSession session, HttpServletRequest req, UserDAO dao, CustomerDAO daoCustomer) throws Exception {
        try {
            User user = new User();

            try {
                user.setId(Long.parseLong((String) methodObj.verifyNullEmpty(req, "txtId", "Nome do Erro", false)));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Nome do Erro");
            }

            user.setName((String) methodObj.verifyNullEmpty(req, "txtName", "Nome Incorreto", false));

            user.setPassword(BCrypt.hashpw(req.getParameter("txtPassword"), BCrypt.gensalt(15)));

            user.setLogin((String) methodObj.verifyNullEmpty(req, "txtLogin", "Login Incorreto", false));

            String cpf = req.getParameter("txtCpf");
            String formatCpf = cpf.replace(".", "");
            formatCpf = formatCpf.replace("-", "");
            user.setCpf(formatCpf);

            user.setEmail((String) methodObj.verifyNullEmpty(req, "txtEmail", "Email Incorreto", true));

            user.setAddress((String) methodObj.verifyNullEmpty(req, "txtAddress", "Endereco incorreto", true));

            try {
                Long idCustomer = Long.parseLong((String) methodObj.verifyNullEmpty(req, "txtCustomer", "Empresa errada", false));
                user.setCustomer(daoCustomer.search(idCustomer));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Nome do Erro");
            }

            dao.edit(user);
            return new RedirectLogic().redirect(
                    req,
                    "User",
                    "view"
            );

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String deleteUser(HttpSession session, HttpServletRequest req, UserDAO dao, Long id) throws Exception {
        dao.delete(id);
        listUser = dao.getList();
        req.setAttribute("listuser", listUser);
        req.setAttribute("listuserSize", listUser.size());
        return "/WEB-INF/jsp/fnd/user.jsp";
    }

    public String resetUserPassword(HttpSession session, HttpServletRequest req, UserDAO dao, Long id) throws Exception {
        dao.resetPass(id);
        return "/WEB-INF/jsp/fnd/user.jsp";
    }

    public String viewEdit(HttpSession session, HttpServletRequest req, UserDAO dao, Long id) throws Exception {
        User userEdit = dao.search(id);
        boolean edit = true;
        req.setAttribute("userEdit", userEdit);
        req.setAttribute("edit", edit);
        return "/WEB-INF/jsp/fnd/user.jsp";
    }

    public String checkLogin(HttpSession session, HttpServletRequest req, UserDAO dao) throws Exception {
        boolean exist = false;
        String login = req.getParameter("loginTry");
        System.out.println(login);
        exist = dao.searchLogin(login);
        System.out.println(exist);
        req.setAttribute("exist", exist);
        return "/WEB-INF/jsp/fnd/ajax/check_login.jsp";
    }
}