package controller;

import dao.CustomerDAO;
import jndi.ConnectionFactory;
import model.Customer;
import model.User;
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


public class CustomerController implements Controller {

    private ObjectMethod methodObj = new ObjectMethod();
    private List<Customer> listcustomer = new ArrayList<Customer>();

    @Override
    public String runController(HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        Connection connection = (Connection) req.getAttribute("connection");
        HttpSession session = req.getSession(true);
        String action = (String) req.getParameter("action");
        CustomerDAO dao = new CustomerDAO(connection);
        listcustomer = dao.getList();
        req.setAttribute("listcustomer", listcustomer);
        req.setAttribute("listcustomerSize", listcustomer.size());
        Long id = null;

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
                resultado = newCustomer(session, req, connection, dao);
                break;
            /*case "edit":
                resultado = editFndUser(session, req, connection, dao);
                break;*/
            case "view":
                resultado = "/jsp/system/customer.jsp";
                break;
            case "delete":
                resultado = deleteCustomer(session, req, connection, dao, id);
                break;
        }

        return resultado;
    }

    public String newCustomer(HttpSession session, HttpServletRequest req, Connection connection, CustomerDAO dao) throws Exception {
        try {
            Customer customer = new Customer();
            User usuarioLogado = (User) session.getAttribute("user");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            customer.setName((String) methodObj.verifyNullEmpty(req, "txtName", "Nome da Empresa", false));

            customer.setCpfCnpj((String) methodObj.verifyNullEmpty(req, "txtCpf", "CNPJ", true));

            customer.setRazaoSocial((String) methodObj.verifyNullEmpty(req, "txtRazao", "Razao", true));

            customer.setAddress((String) methodObj.verifyNullEmpty(req, "txtAddress", "Endereço", false));

            customer.setCountry((String) methodObj.verifyNullEmpty(req, "txtCountry", "Pai", false));

            customer.setState((String) methodObj.verifyNullEmpty(req, "txtState", "Estado", true));

            customer.setCity((String) methodObj.verifyNullEmpty(req, "txtCity", "Cidade", true));
            dao.add(customer);

            return new RedirectLogic().redirect(
                    req,
                    "User",
                    "view"
            );


        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /*public String editFndUser(HttpSession session, HttpServletRequest req, Connection connection, UserDAO dao) throws Exception {
        try {
            User fnduser = new User();
            User usuarioLogado = (User) session.getAttribute("usuarioLogado");
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
                    "view"
            );

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }*/

    public String deleteCustomer(HttpSession session, HttpServletRequest req, Connection connection, CustomerDAO dao, Long id) throws Exception {
        dao.delete(id);
        listcustomer = dao.getList();
        req.setAttribute("listcustomer", listcustomer);
        req.setAttribute("listcustomerSize", listcustomer.size());
        return "/jsp/system/customer.jsp";
    }
}