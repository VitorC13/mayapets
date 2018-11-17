package controller;


import model.User;
import net.vidageek.mirror.dsl.Mirror;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ControllerServlet",
        urlPatterns = {"/maya", "/Login", "/Home", "/User",
                "/Customer", "/Product", "/Collection", "/Price", "/Stock", "/Barcode", "/"})
public class ControllerServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);

        try {

            HttpSession session = req.getSession(true);
            User userLogago = (User) session.getAttribute("user");
            String action = req.getServletPath();
            String className = action.split("/")[1];
            /// MIRROR JAVA REFLECTION
            if (userLogago != null || className.equals("Login")) {
                Class c = Class.forName("controller." + className + "Controller");
                String pagWebRedirect = (String) new Mirror().on(c.newInstance()).invoke().method("runController").withArgs(req, resp);
                req.getRequestDispatcher(pagWebRedirect).forward(req, resp);
            } else {
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            }

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
