package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Customer;

public class CustomerDAO implements IDao<Customer> {
    private final Connection connection;

    public CustomerDAO(Connection connection) {
        this.connection = connection;
    }


/////////##ADD/////////////////

    public void add(Customer customer) throws SQLException {
        int contador = 0;
        String sql = "INSERT INTO fnd_customer("
                + "name, "
                + "cpf_cnpj, "
                + "razao_social, "
                + "address, "
                + "country, "
                + "state, "
                + "city "
                + ") VALUES ("
                + "?, "
                + "?, "
                + "?, "
                + "?, "
                + "?, "
                + "?, "
                + "?);";
        ResultSet rs = null;
        try {
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {

                stmt.setString(++contador, customer.getName());
                if (customer.getCpfCnpj() == null) {
                    stmt.setNull(++contador, java.sql.Types.VARCHAR);
                } else {
                    stmt.setString(++contador, customer.getCpfCnpj());
                }
                if (customer.getRazaoSocial() == null) {
                    stmt.setNull(++contador, java.sql.Types.VARCHAR);
                } else {
                    stmt.setString(++contador, customer.getRazaoSocial());
                }
                stmt.setString(++contador, customer.getAddress());
                stmt.setString(++contador, customer.getCountry());

                if (customer.getState() == null) {
                    stmt.setNull(++contador, java.sql.Types.VARCHAR);
                } else {
                    stmt.setString(++contador, customer.getState());
                }
                if (customer.getCity() == null) {
                    stmt.setNull(++contador, java.sql.Types.VARCHAR);
                } else {
                    stmt.setString(++contador, customer.getCity());
                }
                stmt.executeUpdate();
                this.connection.commit();
            }
        } catch (SQLException e) {
            System.out.println("=== Exception: " + e.toString() + " ===");
            throw new RuntimeException(e);
        }
    }


/////////##EDIT/////////////////

    public void edit(Customer fndcustomer) throws SQLException {
        int contador = 0;
        String sql = "UPDATE fnd_customer SET "
                + "name = ? ,"
                + "cpf_cnpj = ? ,"
                + "address = ? ,"
                + "country = ? ,"
                + "state = ? ,"
                + "city = ? ,"
                + "date_created = ? ,"
                + "date_updated = ? ,"
                + "active = ? ,"
                + "is_deleted= ? "
                + " WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(++contador, fndcustomer.getName());
            stmt.setString(++contador, fndcustomer.getAddress());
            stmt.setString(++contador, fndcustomer.getCountry());
            stmt.setTimestamp(++contador, fndcustomer.getDateCreated());
            stmt.setTimestamp(++contador, fndcustomer.getDateUpdated());
            stmt.setBoolean(++contador, fndcustomer.getActive());
            stmt.setBoolean(++contador, fndcustomer.getDeleted());
            stmt.setLong(++contador, fndcustomer.getId());
            if (fndcustomer.getCpfCnpj() == null) {
                stmt.setNull(++contador, java.sql.Types.VARCHAR);
            } else {
                stmt.setString(++contador, fndcustomer.getCpfCnpj());
            }
            if (fndcustomer.getState() == null) {
                stmt.setNull(++contador, java.sql.Types.VARCHAR);
            } else {
                stmt.setString(++contador, fndcustomer.getState());
            }
            if (fndcustomer.getCity() == null) {
                stmt.setNull(++contador, java.sql.Types.VARCHAR);
            } else {
                stmt.setString(++contador, fndcustomer.getCity());
            }
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("=== ALLINDROP: Exception: " + e.toString() + " ===");
            throw new RuntimeException(e);
        }
    }

/////////##LIST/////////////////

    public List<Customer> getList() {
        try {
            List<Customer> listcustomer = new ArrayList<Customer>();
            try (PreparedStatement stmt = this.connection.prepareStatement("SELECT "
                    + "id, "
                    + "name, "
                    + "cpf_cnpj, "
                    + "razao_social, "
                    + "address, "
                    + "country, "
                    + "state, "
                    + "city, "
                    + "date_created, "
                    + "date_updated, "
                    + "active, "
                    + "is_deleted "
                    + "   FROM fnd_customer where active = true and is_deleted = false;")) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Customer customer = new Customer();
                    customer.setId(rs.getLong("id"));
                    customer.setName(rs.getString("name"));
                    customer.setCpfCnpj(rs.getString("cpf_cnpj"));
                    customer.setRazaoSocial(rs.getString("razao_social"));
                    customer.setAddress(rs.getString("address"));
                    customer.setCountry(rs.getString("country"));
                    customer.setState(rs.getString("state"));
                    customer.setCity(rs.getString("city"));
                    customer.setDateCreated(rs.getTimestamp("date_created"));
                    customer.setDateUpdated(rs.getTimestamp("date_updated"));
                    customer.setActive(rs.getBoolean("active"));
                    customer.setDeleted(rs.getBoolean("is_deleted"));
                    listcustomer.add(customer);
                }
                rs.close();
                stmt.close();
                return listcustomer;
            }
        } catch (SQLException e) {
            System.out.println("=== ALLINDROP: Exception: " + e.toString() + " ===");
        }
        return null;
    }

/////////##DELETE/////////////////

    public void delete(Long id) {
        String sql = "UPDATE fnd_customer SET active=false, date_updated=now(), is_deleted=true WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("===Exception: " + e.toString() + " ===");
            throw new RuntimeException(e);
        }
    }

/////////##SEARCH/////////////////

    public Customer search(Long id) {
        try {
            Customer fndcustomer = new Customer();
            try (PreparedStatement stmt = this.connection.prepareStatement("SELECT "
                    + "id, "
                    + "name, "
                    + "cpf_cnpj, "
                    + "address, "
                    + "country, "
                    + "state, "
                    + "city, "
                    + "date_created, "
                    + "date_updated, "
                    + "active, "
                    + "is_deleted "
                    + "   FROM fnd_customer where id = ?;")) {
                stmt.setLong(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    fndcustomer.setId(rs.getLong("id"));
                    fndcustomer.setName(rs.getString("name"));
                    fndcustomer.setCpfCnpj(rs.getString("cpf_cnpj"));
                    fndcustomer.setAddress(rs.getString("address"));
                    fndcustomer.setCountry(rs.getString("country"));
                    fndcustomer.setState(rs.getString("state"));
                    fndcustomer.setCity(rs.getString("city"));
                    fndcustomer.setDateCreated(rs.getTimestamp("date_created"));
                    fndcustomer.setDateUpdated(rs.getTimestamp("date_updated"));
                    fndcustomer.setActive(rs.getBoolean("active"));
                    fndcustomer.setDeleted(rs.getBoolean("is_deleted"));
                }
                rs.close();
                stmt.close();
            }
            return fndcustomer;
        } catch (SQLException e) {
            System.out.println("=== ALLINDROP: Exception: " + e.toString() + " ===");
        }
        return null;
    }
}