package dao;

import model.Price;
import model.Stock;
import model.TypeStock;
import util.ObjectMethod;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockDAO implements IDao<Stock> {
    private final Connection connection;


    public StockDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Stock stock) throws SQLException {
        int contador = 0;
        String sql = "INSERT INTO stock("
                + "id_customer, "
                + "id_price, "
                + "amount, "
                + "type, "
                + "date_created, "
                + "date_updated, "
                + "active, "
                + "is_deleted"
                + ") VALUES ("
                + "?, "
                + "?, "
                + "?, "
                + "?, "
                + "now(), "
                + "now(), "
                + "true, "
                + "false);";
        ResultSet rs = null;
        try {
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(++contador, stock.getCustomer().getId());
                stmt.setLong(++contador, stock.getPrice().getId());
                stmt.setDouble(++contador, stock.getAmount());
                stmt.setString(++contador, String.valueOf(stock.getType()));
                stmt.executeUpdate();
                this.connection.commit();
            }
        } catch (SQLException e) {
            System.out.println("=== Exception: " + e.toString() + " ===");
            throw new RuntimeException(e);
        }
    }

    public Long addLong(Stock stock) throws SQLException {
        int contador = 0;
        String sql = "INSERT INTO stock("
                + "id_customer, "
                + "id_price, "
                + "amount, "
                + "type, "
                + "date_created, "
                + "date_updated, "
                + "active, "
                + "is_deleted"
                + ") VALUES ("
                + "?, "
                + "?, "
                + "?, "
                + "?, "
                + "now(), "
                + "now(), "
                + "true, "
                + "false);";
        ResultSet rs = null;
        try {
            try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setLong(++contador, stock.getCustomer().getId());
                stmt.setLong(++contador, stock.getPrice().getId());
                stmt.setDouble(++contador, stock.getAmount());
                stmt.setString(++contador, String.valueOf(stock.getType()));
                stmt.executeUpdate();
                rs = stmt.getGeneratedKeys();
                this.connection.commit();
                if (rs.next()) {
                    return rs.getLong("id");
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("=== Exception: " + e.toString() + " ===");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void edit(Stock stock) throws SQLException {
        int contador = 0;
        String sql = "UPDATE stock SET "
                + "id_customer = ? ,"
                + "id_price = ? ,"
                + "amount = ? ,"
                + "type = ? ,"
                + "date_updated = now()"
                + " WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(++contador, stock.getCustomer().getId());
            stmt.setLong(++contador, stock.getPrice().getId());
            stmt.setDouble(++contador, stock.getAmount());
            stmt.setString(++contador, String.valueOf(stock.getType()));
            stmt.setLong(++contador, stock.getId());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("===Exception: " + e.toString() + " ===");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Stock> getList() throws SQLException, ClassNotFoundException {

        try {
            List<Stock> listStock = new ArrayList<Stock>();
            try (PreparedStatement stmt = this.connection.prepareStatement("SELECT "
                    + "s.id, "
                    + "s.id_customer, "
                    + "s.id_price, "
                    + "(SELECT COUNT(id) FROM barcode WHERE id_stock = s.id and barcode.active = TRUE) as amount, "
                    + "s.type, "
                    + "s.date_created, "
                    + "s.active, "
                    + "s.is_deleted "
                    + "   FROM stock s WHERE s.active = true AND s.is_deleted = FALSE;")) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Stock stock = new Stock();
                    stock.setId(rs.getLong("id"));
                    stock.setCustomer(new CustomerDAO(connection).search(rs.getLong("id_customer")));
                    stock.setPrice(new PriceDAO(connection).search(rs.getLong("id_price")));
                    stock.setAmount(rs.getDouble("amount"));
                    stock.setType(TypeStock.valueOf(rs.getString("type")));
                    stock.setDateCreated(rs.getTimestamp("date_created"));
                    stock.setActive(rs.getBoolean("active"));
                    stock.setDeleted(rs.getBoolean("is_deleted"));
                    listStock.add(stock);
                }
                rs.close();
                stmt.close();
                return listStock;
            }
        } catch (SQLException e) {
            System.out.println("===Exception: " + e.toString() + " ===");
        }
        return null;
    }

    @Override
    public Stock search(Long id) throws SQLException {
        try {
            Stock stock = new Stock();
            try (PreparedStatement stmt = this.connection.prepareStatement("SELECT "
                    + "s.id, "
                    + "s.id_customer, "
                    + "s.id_price, "
                    + "(SELECT COUNT(id) FROM barcode WHERE id_stock = s.id and barcode.active = TRUE) as amount, "
                    + "s.type, "
                    + "s.date_created, "
                    + "s.active, "
                    + "s.is_deleted "
                    + "   FROM stock s WHERE s.id = ?;")) {
                stmt.setLong(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    stock.setId(rs.getLong("id"));
                    stock.setCustomer(new CustomerDAO(connection).search(rs.getLong("id_customer")));
                    stock.setPrice(new PriceDAO(connection).search(rs.getLong("id_price")));
                    stock.setAmount(rs.getDouble("amount"));
                    stock.setType(TypeStock.valueOf(rs.getString("type")));
                    stock.setDateCreated(rs.getTimestamp("date_created"));
                    stock.setActive(rs.getBoolean("active"));
                    stock.setDeleted(rs.getBoolean("is_deleted"));
                }
                rs.close();
            }
            return stock;
        } catch (SQLException e) {
            System.out.println("===Exception: " + e.toString() + " ===");
        }
        return null;
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "UPDATE stock SET active=false, date_updated=now(), is_deleted=true WHERE id = ?;";
        ObjectMethod.deleteMethod(id, sql, connection);
    }
}
