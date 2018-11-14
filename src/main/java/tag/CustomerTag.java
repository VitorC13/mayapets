package tag;

import dao.CustomerDAO;
import jndi.ConnectionFactory;
import model.Customer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class CustomerTag extends SimpleTagSupport {

    private String var1, id, label, maxlength, required, readonly, valuex;
    private boolean selected;

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setVar1(String var1) {
        this.var1 = var1;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setMaxlength(String maxlength) {
        this.maxlength = maxlength;
    }


    public void setRequired(String required) {
        this.required = required;
    }

    public void setReadonly(String readonly) {
        this.readonly = readonly;
    }

    public void setValuex(String valuex) {
        this.valuex = valuex;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();

        Locale locale = new Locale("pt", "BR");
        ResourceBundle bundle = ResourceBundle.getBundle("text.properties", locale);

        Connection connection = null;
        try {
            connection = ConnectionFactory.getConnection();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            CustomerDAO dao = new CustomerDAO(connection);
            List<Customer> list = dao.getList();




        } catch (Exception e) {
            System.out.println("=== ALLINDROP: Exception: " + e.toString() + " ===");
            throw new RuntimeException(e);
        }
    }
}
