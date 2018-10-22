package dao;

import java.sql.SQLException;
import java.util.List;

public interface IDao<T> {

    public void add(T t) throws SQLException;

    public void edit(T t) throws SQLException;

    public List<T> getList() throws SQLException, ClassNotFoundException;

    public T search(Long id) throws SQLException;

    public void delete(Long id) throws SQLException;

}
