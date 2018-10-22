package controller;


import net.vidageek.mirror.dsl.Mirror;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ControllerServlet",
        urlPatterns = {"/maya", "/Login", "/User", "/Customer"})
public class ControllerServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);

        String controller = req.getParameter("controller");
        String actions = req.getParameter("actions");

        try {

            if (controller != null) {
                if (controller.equals("ControllerServlet") && actions.equals("userLog")) {
                    req.getRequestDispatcher("/jsp/fnd/home.jsp").forward(req, resp);
                } else if (controller.equals("ControllerServlet") && actions.equals("userNotLog")) {
                    req.setAttribute("msg", "Senha Incorreta");
                    req.getRequestDispatcher("index.jsp").forward(req, resp);
                }
            }
            /// MIRROR JAVA REFLECTION
            String action = req.getServletPath();
            String className = action.split("/")[1];
            String classe = StringUtils.capitalize(className);
            Class c = Class.forName("controller." + classe + "Controller");
            String pagWebRedirect = (String) new Mirror().on(c.newInstance()).invoke().method("runController").withArgs(req, resp);
            req.getRequestDispatcher(pagWebRedirect).forward(req, resp);

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
