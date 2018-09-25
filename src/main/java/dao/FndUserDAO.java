package dao;

import model.FndUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class FndUserDAO implements IDao<FndUser>{
    private final Connection connection;

    public FndUserDAO(Connection connection) {
        this.connection = connection;
    }


/////////##ADD/////////////////

    public Long add(FndUser fnduser) throws SQLException {
        int contador = 0;
        String sql = "INSERT INTO public.fnd_user("
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
                + "?, "
                + "?, "
                + "?, "
                + "?);";
        ResultSet rs = null;
        try {
            try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setString(++contador, fnduser.getName());
                stmt.setString(++contador, fnduser.getPassword());
                stmt.setString(++contador, fnduser.getLogin());
                stmt.setLong(++contador, fnduser.getIdCustomer());
                stmt.setTimestamp(++contador, fnduser.getDateCreated());
                stmt.setTimestamp(++contador, fnduser.getDateUpdate());
                stmt.setBoolean(++contador, fnduser.isActive());
                stmt.setBoolean(++contador, fnduser.isDeleted());
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
            System.out.println("=== ALLINDROP: Exception: " + e.toString() + " ===");
            throw new RuntimeException(e);
        } finally {
            rs.close();
        }
    }


/////////##EDIT/////////////////

    public void edit(FndUser fnduser) throws SQLException {
        int contador = 0;
        String sql = "UPDATE public.fnd_user SET "
                + "name = ? ,"
                + "password = ? ,"
                + "login = ? ,"
                + "cpf = ? ,"
                + "email = ? ,"
                + "address = ? ,"
                + "id_customer = ? ,"
                + "date_created = ? ,"
                + "date_update = ? ,"
                + "active = ? ,"
                + "is_deleted= ? "
                + " WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(++contador, fnduser.getName());
            stmt.setString(++contador, fnduser.getPassword());
            stmt.setString(++contador, fnduser.getLogin());
            stmt.setLong(++contador, fnduser.getIdCustomer());
            stmt.setTimestamp(++contador, fnduser.getDateCreated());
            stmt.setTimestamp(++contador, fnduser.getDateUpdate());
            stmt.setBoolean(++contador, fnduser.isActive());
            stmt.setBoolean(++contador, fnduser.isDeleted());
            stmt.setLong(++contador, fnduser.getId());
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
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("=== ALLINDROP: Exception: " + e.toString() + " ===");
            throw new RuntimeException(e);
        }
    }

/////////##LIST/////////////////

    public List<FndUser> getList() {
        try {
            List<FndUser> listfnduser = new ArrayList<FndUser>();
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
                    + " coalesce(date_updated, date_created) AS date_updated"
                    + "   FROM public.fnd_user;")) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    FndUser fnduser = new FndUser();
                    fnduser.setId(rs.getLong("id"));
                    fnduser.setName(rs.getString("name"));
                    fnduser.setPassword(rs.getString("password"));
                    fnduser.setLogin(rs.getString("login"));
                    fnduser.setCpf(rs.getString("cpf"));
                    fnduser.setEmail(rs.getString("email"));
                    fnduser.setAddress(rs.getString("address"));
                    fnduser.setIdCustomer(rs.getLong("id_customer"));
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
            System.out.println("=== ALLINDROP: Exception: " + e.toString() + " ===");
        }
        return null;
    }

/////////##DELETE/////////////////

    public void delete(Long id) {
        String sql = "UPDATE public.fnd_user SET active=false, date_updated=now(), is_deleted=true WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("=== ALLINDROP: Exception: " + e.toString() + " ===");
            throw new RuntimeException(e);
        }
    }

/////////##SEARCH/////////////////

    public FndUser search(Long id) {
        try {
            FndUser fnduser = new FndUser();
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
                    + " coalesce(date_updated, date_created) AS date_updated"
                    + "   FROM public.fnd_user where id = ?;")) {
                stmt.setLong(1, id);
                ResultSet rs = stmt.executeQuery();
                fndUserObject(fnduser, rs);
                rs.close();
                stmt.close();
            }
            return fnduser;
        } catch (SQLException e) {
            System.out.println("=== ALLINDROP: Exception: " + e.toString() + " ===");
        }
        return null;
    }

    /////////##SEARCH/////////////////

    public FndUser autenthicBCrypt(String login) {
        try {
            FndUser fnduser = new FndUser();
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
                    + " coalesce(date_updated, date_created) AS date_updated"
                    + "   FROM public.fnd_user where login = ?;")) {
                stmt.setString(1, login);
                ResultSet rs = stmt.executeQuery();
                fndUserObject(fnduser, rs);
                rs.close();
                stmt.close();
            }
            return fnduser;
        } catch (SQLException e) {
            System.out.println("=== ALLINDROP: Exception: " + e.toString() + " ===");
        }
        return null;
    }

    private void fndUserObject(FndUser fnduser, ResultSet rs) throws SQLException {
        if (rs.next()) {
            fnduser.setId(rs.getLong("id"));
            fnduser.setName(rs.getString("name"));
            fnduser.setPassword(rs.getString("password"));
            fnduser.setLogin(rs.getString("login"));
            fnduser.setCpf(rs.getString("cpf"));
            fnduser.setEmail(rs.getString("email"));
            fnduser.setAddress(rs.getString("address"));
            fnduser.setIdCustomer(rs.getLong("id_customer"));
            fnduser.setDateCreated(rs.getTimestamp("date_created"));
            fnduser.setDateUpdate(rs.getTimestamp("date_update"));
            fnduser.setActive(rs.getBoolean("active"));
            fnduser.setDeleted(rs.getBoolean("is_deleted"));
        }
    }
}