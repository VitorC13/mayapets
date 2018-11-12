package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;

public class HomeController implements Controller  {



    @Override
    public String runController(HttpServletRequest req, HttpServletResponse res) throws Exception {
        Connection connection = (Connection) req.getAttribute("connection");
        HttpSession session = req.getSession(true);
        String action = (String) req.getParameter("actions");

        if (action == null) {
            action = "view";
        }

        String resultado = "";
        switch (action) {
            case "userLog":
                resultado = "/jsp/fnd/home.jsp";
                break;
            case "userNotLog":
                req.setAttribute("msg", "Senha Incorreta");
                resultado = "index.jsp";
                break;
        }

        return resultado;
    }
}
