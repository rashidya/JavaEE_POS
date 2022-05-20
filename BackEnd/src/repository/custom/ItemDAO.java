package repository.custom;

import entity.Item;
import repository.CrudDAO;

import java.sql.Connection;
import java.sql.SQLException;

public interface ItemDAO extends CrudDAO<Item,String> {
    String generateItemID(Connection connection) throws SQLException, ClassNotFoundException;

    boolean updateStock(Connection connection,String itemCode, int cusQty, String remove) throws SQLException, ClassNotFoundException;
}
