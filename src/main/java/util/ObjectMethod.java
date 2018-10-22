package util;

import jndi.ConnectionFactory;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class ObjectMethod {

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

}
