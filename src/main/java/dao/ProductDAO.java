package dao;

import model.Product;
import util.ObjectMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements IDao<Product> {
    private final Connection connection;

    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Product product) throws SQLException {
        int contador = 0;
        String sql = "INSERT INTO product("
                + "name, "
                + "type, "
                + "date_created, "
                + "date_updated, "
                + "active, "
                + "is_deleted"
                + ") VALUES ("
                + "?, "
                + "?, "
                + "now(), "
                + "now(), "
                + "true, "
                + "false);";
        ResultSet rs = null;
        try {
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(++contador, product.getName());
                stmt.setString(++contador, product.getType());
                stmt.executeUpdate();
                this.connection.commit();
            }
        } catch (SQLException e) {
            System.out.println("=== Exception: " + e.toString() + " ===");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void edit(Product product) throws SQLException {
        int contador = 0;
        String sql = "UPDATE product SET "
                + "name = ? ,"
                + "type = ? ,"
                + "date_updated = now()"
                + " WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(++contador, product.getName());
            stmt.setString(++contador, product.getType());
            stmt.setLong(++contador, product.getId());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("=== ALLINDROP: Exception: " + e.toString() + " ===");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getList() throws SQLException, ClassNotFoundException {
        try {
            List<Product> listProduct = new ArrayList<Product>();
            try (PreparedStatement stmt = this.connection.prepareStatement("SELECT "
                    + "id, "
                    + "name, "
                    + "type, "
                    + "date_created, "
                    + "active, "
                    + "is_deleted "
                    + "   FROM product WHERE active = true AND is_deleted = FALSE;")) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getLong("id"));
                    product.setName(rs.getString("name"));
                    product.setType(rs.getString("type"));
                    product.setDateCreated(rs.getTimestamp("date_created"));
                    product.setActive(rs.getBoolean("active"));
                    product.setDeleted(rs.getBoolean("is_deleted"));
                    listProduct.add(product);
                }
                rs.close();
                stmt.close();
                return listProduct;
            }
        } catch (SQLException e) {
            System.out.println("===Exception: " + e.toString() + " ===");
        }
        return null;
    }

    @Override
    public Product search(Long id) throws SQLException {
        try {
            Product product = new Product();
            try (PreparedStatement stmt = this.connection.prepareStatement("SELECT "
                    + "id, "
                    + "name, "
                    + "type, "
                    + "date_created, "
                    + "active, "
                    + "is_deleted "
                    + "   FROM product WHERE id = ?;")) {
                stmt.setLong(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    product.setId(rs.getLong("id"));
                    product.setName(rs.getString("name"));
                    product.setType(rs.getString("type"));
                    product.setDateCreated(rs.getTimestamp("date_created"));
                    product.setActive(rs.getBoolean("active"));
                    product.setDeleted(rs.getBoolean("is_deleted"));
                }
                rs.close();
            }
            return product;
        } catch (SQLException e) {
            System.out.println("===Exception: " + e.toString() + " ===");
        }
        return null;
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "UPDATE product SET active=false, date_updated=now(), is_deleted=true WHERE id = ?;";
        ObjectMethod.deleteMethod(id, sql, connection);
    }
}
