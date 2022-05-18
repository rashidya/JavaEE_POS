package repository;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO <T,ID> extends lk.ijse.pos_system.repository.SuperDAO {

    boolean add(T t) throws SQLException, ClassNotFoundException;
    boolean update(T t) throws SQLException, ClassNotFoundException;
    boolean delete(ID id) throws SQLException, ClassNotFoundException;
    ArrayList<T> getAll() throws SQLException, ClassNotFoundException;
    T search(ID id) throws SQLException, ClassNotFoundException;
}
