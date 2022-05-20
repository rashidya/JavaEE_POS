package business.custom;

import business.SuperBO;
import dto.ItemDTO;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.SQLException;

public interface ItemBO  extends SuperBO {
    boolean saveItem(ItemDTO itemDTO, Connection connection) throws SQLException, ClassNotFoundException;

    ItemDTO searchItem(String itemId, Connection connection) throws SQLException, ClassNotFoundException;

    ObservableList<ItemDTO> getAllItems(Connection connection) throws SQLException, ClassNotFoundException;

    String generateItemID(Connection connection) throws SQLException, ClassNotFoundException;

    boolean updateItem(ItemDTO itemDTO, Connection connection) throws SQLException, ClassNotFoundException;

    boolean deleteItem(String itemId, Connection connection) throws SQLException, ClassNotFoundException;
}
