package jndi;

import org.mariadb.jdbc.MySQLDataSource;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtils {

    public static Connection getConnection() throws NamingException, Exception {

        // Create a Connection to database.
        Connection connection = null;

        /*InitialContext cxt = null;

        cxt  = new InitialContext();

        if (cxt == null) {
            throw new Exception("Uh oh -- no context!");
        }

        DataSource ds = null;

        ds = (DataSource) cxt.lookup("java:comp/env/jdbc/mariadb");
        connection  = ds.getConnection();

        if (ds == null) {
            throw new Exception("Data source not found!");

        }*/


        URI jdbUri = new URI(System.getenv("JAWSDB_URL"));

        String username = jdbUri.getUserInfo().split(":")[0];
        String password = jdbUri.getUserInfo().split(":")[1];
        String port = String.valueOf(jdbUri.getPort());
        String jdbUrl = "jdbc:mysql://" + jdbUri.getHost() + ":" + port + jdbUri.getPath();

        return DriverManager.getConnection(jdbUrl, username, password);
    }

    public static void closeQuietly(Connection connection) {
        try {
            connection.close();
        } catch (Exception e) {
        }
    }

    public static void rollbackQuietly(Connection connection) {
        try {
            connection.rollback();
        } catch (Exception e) {
        }
    }

}
