package dao;

import model.Collection;
import model.Product;
import util.ObjectMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CollectionDAO implements IDao<Collection> {
    private final Connection connection;

    public CollectionDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Collection collection) throws SQLException {
        int contador = 0;
        String sql = "INSERT INTO collection("
                + "name, "
                + "date_created, "
                + "date_updated, "
                + "active, "
                + "is_deleted"
                + ") VALUES ("
                + "?, "
                + "now(), "
                + "now(), "
                + "true, "
                + "false);";
        ResultSet rs = null;
        try {
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(++contador, collection.getName());
                stmt.executeUpdate();
                this.connection.commit();
            }
        } catch (SQLException e) {
            System.out.println("=== Exception: " + e.toString() + " ===");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void edit(Collection collection) throws SQLException {
        int contador = 0;
        String sql = "UPDATE collection SET "
                + "name = ? ,"
                + "date_updated = now()"
                + " WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(++contador, collection.getName());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("=== ALLINDROP: Exception: " + e.toString() + " ===");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Collection> getList() throws SQLException, ClassNotFoundException {
        try {
            List<Collection> listCollection = new ArrayList<Collection>();
            try (PreparedStatement stmt = this.connection.prepareStatement("SELECT "
                    + "id, "
                    + "name, "
                    + "date_created, "
                    + "active, "
                    + "is_deleted "
                    + "   FROM collection WHERE active = true AND is_deleted = FALSE;")) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Collection collection = new Collection();
                    collection.setId(rs.getLong("id"));
                    collection.setName(rs.getString("name"));
                    collection.setDateCreated(rs.getTimestamp("date_created"));
                    collection.setActive(rs.getBoolean("active"));
                    collection.setDeleted(rs.getBoolean("is_deleted"));
                    listCollection.add(collection);
                }
                rs.close();
                stmt.close();
                return listCollection;
            }
        } catch (SQLException e) {
            System.out.println("===Exception: " + e.toString() + " ===");
        }
        return null;
    }

    @Override
    public Collection search(Long id) throws SQLException {
        try {
            Collection collection = new Collection();
            try (PreparedStatement stmt = this.connection.prepareStatement("SELECT "
                    + "id, "
                    + "name, "
                    + "date_created, "
                    + "active, "
                    + "is_deleted "
                    + "   FROM collection WHERE id = ?;")) {
                stmt.setLong(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    collection.setId(rs.getLong("id"));
                    collection.setName(rs.getString("name"));
                    collection.setDateCreated(rs.getTimestamp("date_created"));
                    collection.setActive(rs.getBoolean("active"));
                    collection.setDeleted(rs.getBoolean("is_deleted"));
                }
                rs.close();
            }
            return collection;
        } catch (SQLException e) {
            System.out.println("===Exception: " + e.toString() + " ===");
        }
        return null;
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "UPDATE collection SET active=false, date_updated=now(), is_deleted=true WHERE id = ?;";
        ObjectMethod.deleteMethod(id, sql, connection);
    }

}
