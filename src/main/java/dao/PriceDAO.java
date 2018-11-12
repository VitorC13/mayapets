package dao;

import model.Collection;
import model.Price;
import model.Product;
import util.ObjectMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PriceDAO implements IDao<Price> {
    private final Connection connection;


    public PriceDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Price price) throws SQLException {
        int contador = 0;
        String sql = "INSERT INTO price("
                + "price, "
                + "price_varejo, "
                + "price_atacado, "
                + "id_product, "
                + "id_collection, "
                + "date_created, "
                + "date_updated, "
                + "active, "
                + "is_deleted"
                + ") VALUES ("
                + "?, "
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
                stmt.setDouble(++contador, price.getPrice());
                stmt.setDouble(++contador, price.getPriceVar());
                stmt.setDouble(++contador, price.getPriceAtc());
                stmt.setLong(++contador, price.getProduct().getId());
                stmt.setLong(++contador, price.getCollection().getId());
                stmt.executeUpdate();
                this.connection.commit();
            }
        } catch (SQLException e) {
            System.out.println("=== Exception: " + e.toString() + " ===");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void edit(Price price) throws SQLException {
        int contador = 0;
        String sql = "UPDATE price SET "
                + "price = ? ,"
                + "price_varejo = ? ,"
                + "price_atacado = ? ,"
                + "id_product = ? ,"
                + "id_collection = ? ,"
                + "date_updated = now()"
                + " WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(++contador, price.getPrice());
            stmt.setDouble(++contador, price.getPriceVar());
            stmt.setDouble(++contador, price.getPriceAtc());
            stmt.setLong(++contador, price.getProduct().getId());
            stmt.setLong(++contador, price.getCollection().getId());
            stmt.setLong(++contador, price.getId());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("===Exception: " + e.toString() + " ===");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Price> getList() throws SQLException, ClassNotFoundException {

        try {
            List<Price> listPrice = new ArrayList<Price>();
            try (PreparedStatement stmt = this.connection.prepareStatement("SELECT "
                    + "id, "
                    + "price, "
                    + "price_varejo, "
                    + "price_atacado, "
                    + "id_product, "
                    + "id_collection, "
                    + "date_created, "
                    + "active, "
                    + "is_deleted "
                    + "   FROM price WHERE active = true AND is_deleted = FALSE;")) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Price price = new Price();
                    price.setId(rs.getLong("id"));
                    price.setPrice(rs.getDouble("price"));
                    price.setPriceVar(rs.getDouble("price_varejo"));
                    price.setPriceAtc(rs.getDouble("price_atacado"));
                    price.setProduct(new ProductDAO(connection).search(rs.getLong("id_product")));
                    price.setCollection(new CollectionDAO(connection).search(rs.getLong("id_collection")));
                    price.setDateCreated(rs.getTimestamp("date_created"));
                    price.setActive(rs.getBoolean("active"));
                    price.setDeleted(rs.getBoolean("is_deleted"));
                    listPrice.add(price);
                }
                rs.close();
                stmt.close();
                return listPrice;
            }
        } catch (SQLException e) {
            System.out.println("===Exception: " + e.toString() + " ===");
        }
        return null;
    }

    @Override
    public Price search(Long id) throws SQLException {
        try {
            Price price = new Price();
            try (PreparedStatement stmt = this.connection.prepareStatement("SELECT "
                    + "id, "
                    + "price, "
                    + "price_varejo, "
                    + "price_atacado, "
                    + "id_product, "
                    + "id_collection, "
                    + "date_created, "
                    + "active, "
                    + "is_deleted "
                    + "   FROM price WHERE id = ?;")) {
                stmt.setLong(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    price.setId(rs.getLong("id"));
                    price.setPrice(rs.getDouble("price"));
                    price.setPriceVar(rs.getDouble("price_varejo"));
                    price.setPriceAtc(rs.getDouble("price_atacado"));
                    price.setProduct(new ProductDAO(connection).search(rs.getLong("id_product")));
                    price.setCollection(new CollectionDAO(connection).search(rs.getLong("id_collection")));
                    price.setDateCreated(rs.getTimestamp("date_created"));
                    price.setActive(rs.getBoolean("active"));
                    price.setDeleted(rs.getBoolean("is_deleted"));
                }
                rs.close();
            }
            return price;
        } catch (SQLException e) {
            System.out.println("===Exception: " + e.toString() + " ===");
        }
        return null;
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "UPDATE price SET active=false, date_updated=now(), is_deleted=true WHERE id = ?;";
        ObjectMethod.deleteMethod(id, sql, connection);
    }
}
