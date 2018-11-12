package dao;

import model.Barcode;
import model.Stock;
import util.ObjectMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BarcodeDAO implements IDao<Barcode> {
    private final Connection connection;

    public BarcodeDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Barcode barcode) throws SQLException {
        int contador = 0;
        String sql = "INSERT INTO barcode("
                + "id_stock, "
                + "barcode, "
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
                stmt.setLong(++contador, barcode.getStock().getId());
                stmt.setString(++contador, barcode.getBarcode());
                stmt.executeUpdate();
                this.connection.commit();
            }
        } catch (SQLException e) {
            System.out.println("=== Exception: " + e.toString() + " ===");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void edit(Barcode barcode) throws SQLException {
        int contador = 0;
        String sql = "UPDATE barcode SET "
                + "id_stock = ? ,"
                + "barcode = ? ,"
                + "date_updated = now()"
                + " WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(++contador, barcode.getStock().getId());
            stmt.setString(++contador, barcode.getBarcode());
            stmt.setLong(++contador, barcode.getId());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("=== ALLINDROP: Exception: " + e.toString() + " ===");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Barcode> getList() throws SQLException, ClassNotFoundException {
        try {
            List<Barcode> listBarcode = new ArrayList<Barcode>();
            try (PreparedStatement stmt = this.connection.prepareStatement("SELECT "
                    + "id, "
                    + "id_stock, "
                    + "barcode, "
                    + "date_created, "
                    + "active, "
                    + "is_deleted "
                    + "   FROM barcode WHERE active = true AND is_deleted = FALSE order by id_stock;")) {
                ResultSet rs = stmt.executeQuery();
                listObjectBarcode(listBarcode, rs);
                rs.close();
                stmt.close();
                return listBarcode;
            }
        } catch (SQLException e) {
            System.out.println("===Exception: " + e.toString() + " ===");
        }
        return null;
    }

    @Override
    public Barcode search(Long id) throws SQLException {
        try {
            Barcode barcode = new Barcode();
            try (PreparedStatement stmt = this.connection.prepareStatement("SELECT "
                    + "id, "
                    + "id_stock, "
                    + "barcode, "
                    + "date_created, "
                    + "active, "
                    + "is_deleted "
                    + "   FROM barcode WHERE id = ?;")) {
                stmt.setLong(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    barcode.setId(rs.getLong("id"));
                    barcode.setStock(new StockDAO(connection).search(rs.getLong("id_stock")));
                    barcode.setBarcode(rs.getString("barcode"));
                    barcode.setDateCreated(rs.getTimestamp("date_created"));
                    barcode.setActive(rs.getBoolean("active"));
                    barcode.setDeleted(rs.getBoolean("is_deleted"));
                }
                rs.close();
            }
            return barcode;
        } catch (SQLException e) {
            System.out.println("===Exception: " + e.toString() + " ===");
        }
        return null;
    }

    public List<Barcode> getListByStock(Stock stock) throws SQLException, ClassNotFoundException {
        List<Barcode> listBarcode = new ArrayList<Barcode>();
        String sql = "SELECT "
                + "id, "
                + "id_stock, "
                + "barcode, "
                + "date_created, "
                + "active, "
                + "is_deleted "
                + "   FROM barcode WHERE active = true AND is_deleted = FALSE and id_stock = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, stock.getId());
            ResultSet rs = stmt.executeQuery();
            listObjectBarcode(listBarcode, rs);
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("===Exception: " + e.toString() + " ===");
        }
        return listBarcode;
    }

    public List<Long> getListIdStockBarcode() throws SQLException, ClassNotFoundException {
        List<Long> listIdStock = new ArrayList<Long>();
        String sql = "SELECT DISTINCT id_stock FROM barcode WHERE barcode <> '' ";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Long id = null;
                id = rs.getLong("id_stock");
                listIdStock.add(id);
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("===Exception: " + e.toString() + " ===");
        }
        return listIdStock;
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "UPDATE barcode SET active=false, date_updated=now(), is_deleted=true WHERE id = ?;";
        ObjectMethod.deleteMethod(id, sql, connection);
    }

    public void deleteRow(Long idStock) throws SQLException {
        String sql = "DELETE FROM barcode WHERE id_stock = ?;";
        ObjectMethod.deleteMethod(idStock, sql, connection);
    }

    private void listObjectBarcode(List<Barcode> listBarcode, ResultSet rs) throws SQLException {
        while (rs.next()) {
            Barcode barcode = new Barcode();
            barcode.setId(rs.getLong("id"));
            barcode.setStock(new StockDAO(connection).search(rs.getLong("id_stock")));
            barcode.setBarcode(rs.getString("barcode"));
            barcode.setDateCreated(rs.getTimestamp("date_created"));
            barcode.setActive(rs.getBoolean("active"));
            barcode.setDeleted(rs.getBoolean("is_deleted"));
            listBarcode.add(barcode);
        }
    }
}
