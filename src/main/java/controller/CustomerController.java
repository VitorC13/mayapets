package controller;

import dao.CustomerDAO;
import dao.UserDAO;
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
    private Long id = null;

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
            case "edit":
                resultado = editCustomer(session, req, connection, dao);
                break;
            case "viewedit":
                resultado = viewEdit(session, req, dao, id);
                break;
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

            customer.setName((String) methodObj.verifyNullEmpty(req, "txtName", "Nome da Empresa", false));

            String cpf = req.getParameter("txtCpf");
            String formatCpf = cpf.replace(".","");
            formatCpf = formatCpf.replace("-","");
            formatCpf = formatCpf.replace("/","");
            customer.setCpfCnpj(formatCpf);

            customer.setRazaoSocial((String) methodObj.verifyNullEmpty(req, "txtRazao", "Razao", true));

            customer.setAddress((String) methodObj.verifyNullEmpty(req, "txtAddress", "Endereço", false));

            customer.setCountry((String) methodObj.verifyNullEmpty(req, "txtCountry", "Pai", false));

            customer.setState((String) methodObj.verifyNullEmpty(req, "txtState", "Estado", true));

            customer.setCity((String) methodObj.verifyNullEmpty(req, "txtCity", "Cidade", true));
            dao.add(customer);

            return new RedirectLogic().redirect(
                    req,
                    "Customer",
                    "view"
            );


        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String editCustomer(HttpSession session, HttpServletRequest req, Connection connection, CustomerDAO dao) throws Exception {
        try {
            Customer customer = new Customer();

            try {
                customer.setId(Long.parseLong((String) methodObj.verifyNullEmpty(req, "txtId", "Nome do Erro", false)));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Nome do Erro");
            }

            customer.setName((String) methodObj.verifyNullEmpty(req, "txtName", "Nome da Empresa", false));

            String cpf = req.getParameter("txtCpf");
            String formatCpf = cpf.replace(".","");
            formatCpf = formatCpf.replace("-","");
            formatCpf = formatCpf.replace("/","");
            customer.setCpfCnpj(formatCpf);

            customer.setRazaoSocial((String) methodObj.verifyNullEmpty(req, "txtRazao", "Razao", true));

            customer.setAddress((String) methodObj.verifyNullEmpty(req, "txtAddress", "Endereço", false));

            customer.setCountry((String) methodObj.verifyNullEmpty(req, "txtCountry", "Pai", false));

            customer.setState((String) methodObj.verifyNullEmpty(req, "txtState", "Estado", true));

            customer.setCity((String) methodObj.verifyNullEmpty(req, "txtCity", "Cidade", true));

            dao.edit(customer);
            return new RedirectLogic().redirect(
                    req,
                    "Customer",
                    "view"
            );

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String deleteCustomer(HttpSession session, HttpServletRequest req, Connection connection, CustomerDAO dao, Long id) throws Exception {
        dao.delete(id);
        listcustomer = dao.getList();
        req.setAttribute("listcustomer", listcustomer);
        req.setAttribute("listcustomerSize", listcustomer.size());
        return "/jsp/system/customer.jsp";
    }

    public String viewEdit(HttpSession session, HttpServletRequest req, CustomerDAO dao, Long id) throws Exception {
        Customer customerEdit = dao.search(id);
        boolean edit = true;
        req.setAttribute("customerEdit", customerEdit);
        req.setAttribute("edit", edit);
        return "/jsp/system/customer.jsp";
    }
}