package util;

import javax.servlet.http.HttpServletRequest;

public class ObjectField {
    public static Object getObject(HttpServletRequest req, String field, String exception, boolean isNull) throws Exception {
        if (req.getParameter(field) != null && !((String) req.getParameter(field)).trim().equals(""))
            return req.getParameter(field);
        else if (!isNull) throw new Exception(exception);
        else return null;
    }

    public static boolean nullBoolean(HttpServletRequest req, String field) {
        if (req.getParameter(field) != null) {
            return true;
        } else {
            return false;
        }
    }
}
