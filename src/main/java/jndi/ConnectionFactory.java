/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jndi;

import org.mariadb.jdbc.MySQLDataSource;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionFactory {

    //Método Construtor da Classe//
    public ConnectionFactory() {

    }

    //Método de Conexão//
    public static java.sql.Connection getConnection() throws NamingException, Exception {

        return ConnectionUtils.getConnection();
    }
}
