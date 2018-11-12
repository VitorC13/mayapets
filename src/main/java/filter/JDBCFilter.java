package filter;

import jndi.ConnectionFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.util.Base64;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter(urlPatterns = {"/*"})
public class JDBCFilter implements Filter {

    public JDBCFilter() {
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        // 
        String servletPath = req.getServletPath();

        // Only open Connection for special request.
        // (For example: servlet, jsp, ..)
        // 
        // Avoid open Connection for the common request
        // (For example image, css, javascript,... )
        // @WebFilter(urlPatterns = {"*.jsp", "/do"})
        if (servletPath.contains("*.jsp") || servletPath.contains("/")) {
            Connection conn = null;
            try {
                // Create a Connection.
                conn = ConnectionFactory.openConnection();
                // Set auto commit = false
                conn.setAutoCommit(false);

                // Store connection in attribute of request.
                MyUtils.storeConnection(request, conn);

                // Go to next element (filter or target) in chain
                chain.doFilter(request, response);

                // Call commit() to commit transaction.
                conn.commit();
            } catch (Exception e) {
                ConnectionFactory.closeConnection();

                final StringWriter sw = new StringWriter();
                final PrintWriter pw = new PrintWriter(sw, true);
                e.printStackTrace(pw);
                
                
                /*
                StringBuilder stackTrace = new StringBuilder();
                stackTrace.append(e.getStackTrace()[0].toString());
                stackTrace.append(e.getStackTrace()[1].toString());
                stackTrace.append(e.getStackTrace()[2].toString());
                 */
                
                byte[] erouteHash = Base64.getEncoder().encode(/*stackTrace.toString().getBytes()*/sw.getBuffer().toString().getBytes());
                byte[] emessageHash = Base64.getEncoder().encode("An internal error has occurred".getBytes());

                /*IMPLEMENTAR EXCEPTION*/
                
               System.out.println("=== JDBCFilter Exception: " + e.toString() + " ===");
                
            } finally {
                ConnectionFactory.closeConnection();
            }
        } // For common request.
        else {
            // Go to next element (filter or target) in chain.
            chain.doFilter(request, response);
        }

    }

}
