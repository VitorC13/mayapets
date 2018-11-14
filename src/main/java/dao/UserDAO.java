package dao;

import model.User;
import util.BCrypt;
import util.ObjectMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDAO implements IDao<User> {
    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }


/////////##ADD/////////////////

    public void add(User fnduser) throws SQLException {
        int contador = 0;
        String sql = "INSERT INTO fnd_user("
                + "name, "
                + "password, "
                + "login, "
                + "cpf, "
                + "email, "
                + "address, "
                + "id_customer, "
                + "date_created, "
                + "date_update, "
                + "active, "
                + "is_deleted"
                + ") VALUES ("
                + "?, "
                + "?, "
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

                stmt.setString(++contador, fnduser.getName());
                stmt.setString(++contador, fnduser.getPassword());
                stmt.setString(++contador, fnduser.getLogin());
                if (fnduser.getCpf() == null) {
                    stmt.setNull(++contador, java.sql.Types.VARCHAR);
                } else {
                    stmt.setString(++contador, fnduser.getCpf());
                }
                if (fnduser.getEmail() == null) {
                    stmt.setNull(++contador, java.sql.Types.VARCHAR);
                } else {
                    stmt.setString(++contador, fnduser.getEmail());
                }
                if (fnduser.getAddress() == null) {
                    stmt.setNull(++contador, java.sql.Types.VARCHAR);
                } else {
                    stmt.setString(++contador, fnduser.getAddress());
                }
                stmt.setLong(++contador, fnduser.getCustomer().getId());
                stmt.executeUpdate();
                this.connection.commit();
            }
        } catch (SQLException e) {
            System.out.println("=== Exception: " + e.toString() + " ===");
            throw new RuntimeException(e);
        }
    }


/////////##EDIT/////////////////

    public void edit(User user) throws SQLException {
        int contador = 0;
        String sql = "UPDATE fnd_user SET "
                + "name = ? ,"
                + "password = ? ,"
                + "login = ? ,"
                + "cpf = ? ,"
                + "email = ? ,"
                + "address = ? ,"
                + "id_customer = ? ,"
                + "date_update = now()"
                + " WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(++contador, user.getName());
            stmt.setString(++contador, user.getPassword());
            stmt.setString(++contador, user.getLogin());
            if (user.getCpf() == null) {
                stmt.setNull(++contador, java.sql.Types.VARCHAR);
            } else {
                stmt.setString(++contador, user.getCpf());
            }
            if (user.getEmail() == null) {
                stmt.setNull(++contador, java.sql.Types.VARCHAR);
            } else {
                stmt.setString(++contador, user.getEmail());
            }
            if (user.getAddress() == null) {
                stmt.setNull(++contador, java.sql.Types.VARCHAR);
            } else {
                stmt.setString(++contador, user.getAddress());
            }
            stmt.setLong(++contador, user.getCustomer().getId());
            stmt.setLong(++contador, user.getId());

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("===: Exception: " + e.toString() + " ===");
            throw new RuntimeException(e);
        }
    }

/////////##LIST/////////////////

    public List<User> getList() {
        try {
            List<User> listfnduser = new ArrayList<User>();
            try (PreparedStatement stmt = this.connection.prepareStatement("SELECT "
                    + "id, "
                    + "name, "
                    + "password, "
                    + "login, "
                    + "cpf, "
                    + "email, "
                    + "address, "
                    + "id_customer, "
                    + "date_created, "
                    + "date_update, "
                    + "active, "
                    + "is_deleted "
                    + "   FROM fnd_user WHERE active = true AND is_deleted = FALSE;")) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    User fnduser = new User();
                    fnduser.setId(rs.getLong("id"));
                    fnduser.setName(rs.getString("name"));
                    fnduser.setPassword(rs.getString("password"));
                    fnduser.setLogin(rs.getString("login"));
                    fnduser.setCpf(rs.getString("cpf"));
                    fnduser.setEmail(rs.getString("email"));
                    fnduser.setAddress(rs.getString("address"));
                    fnduser.setCustomer(new CustomerDAO(connection).search(rs.getLong("id_customer")));
                    fnduser.setDateCreated(rs.getTimestamp("date_created"));
                    fnduser.setDateUpdate(rs.getTimestamp("date_update"));
                    fnduser.setActive(rs.getBoolean("active"));
                    fnduser.setDeleted(rs.getBoolean("is_deleted"));
                    listfnduser.add(fnduser);
                }
                rs.close();
                stmt.close();
                return listfnduser;
            }
        } catch (SQLException e) {
            System.out.println("===Exception: " + e.toString() + " ===");
        }
        return null;
    }

/////////##DELETE/////////////////

    public void delete(Long id) {
        String sql = "UPDATE fnd_user SET active=false, date_update=now(), is_deleted=true WHERE id = ?;";
        ObjectMethod.deleteMethod(id, sql, connection);
    }

    public void resetPass(Long id) {
        String sql = "UPDATE fnd_user SET password = ? WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, BCrypt.hashpw("123456", BCrypt.gensalt(15)));
            stmt.setLong(2, id);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("=== Exception: " + e.toString() + " ===");
            throw new RuntimeException(e);
        }
    }

/////////##SEARCH/////////////////

    public User search(Long id) {
        try {
            User user = new User();
            try (PreparedStatement stmt = this.connection.prepareStatement("SELECT "
                    + "id, "
                    + "name, "
                    + "password, "
                    + "login, "
                    + "cpf, "
                    + "email, "
                    + "address, "
                    + "id_customer, "
                    + "date_created, "
                    + "date_update, "
                    + "active, "
                    + "is_deleted "
                    + "   FROM fnd_user where id = ?;")) {
                stmt.setLong(1, id);
                ResultSet rs = stmt.executeQuery();
                userObject(user, rs);
                rs.close();
                stmt.close();
            }
            return user;
        } catch (SQLException e) {
            System.out.println("===Exception: " + e.toString() + " ===");
        }
        return null;
    }

    public Boolean searchLogin(String login) {
        try {
            Boolean exist = false;
            try (PreparedStatement stmt = this.connection.prepareStatement("SELECT login FROM fnd_user where login = ?;")) {
                stmt.setString(1, login);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    exist = true;
                }
                rs.close();
                stmt.close();
            }
            return exist;
        } catch (SQLException e) {
            System.out.println("===Exception: " + e.toString() + " ===");
        }
        return null;
    }

    /////////##SEARCH/////////////////

    public User autenthicBCrypt(String login) {
        try {
            User user = new User();
            try (PreparedStatement stmt = this.connection.prepareStatement("SELECT "
                    + "id, "
                    + "name, "
                    + "password, "
                    + "login, "
                    + "cpf, "
                    + "email, "
                    + "address, "
                    + "id_customer, "
                    + "date_created, "
                    + "date_update, "
                    + "active, "
                    + "is_deleted, "
                    + " coalesce(date_created) AS date_updated"
                    + "   FROM fnd_user where login = ?;")) {
                stmt.setString(1, login);
                ResultSet rs = stmt.executeQuery();
                userObject(user, rs);
                rs.close();
            }
            return user;
        } catch (SQLException e) {
            System.out.println("===: Exception: " + e.toString() + " ===");
        }
        return null;
    }

    private void userObject(User fnduser, ResultSet rs) throws SQLException {
        if (rs.next()) {
            fnduser.setId(rs.getLong("id"));
            fnduser.setName(rs.getString("name"));
            fnduser.setPassword(rs.getString("password"));
            fnduser.setLogin(rs.getString("login"));
            fnduser.setCpf(rs.getString("cpf"));
            fnduser.setEmail(rs.getString("email"));
            fnduser.setAddress(rs.getString("address"));
            fnduser.setCustomer(new CustomerDAO(connection).search(rs.getLong("id_customer")));
            fnduser.setDateCreated(rs.getTimestamp("date_created"));
            fnduser.setDateUpdate(rs.getTimestamp("date_update"));
            fnduser.setActive(rs.getBoolean("active"));
            fnduser.setDeleted(rs.getBoolean("is_deleted"));
        }
    }
}