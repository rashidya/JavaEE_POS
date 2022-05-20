package business.custom.Impl;

import business.custom.ItemBO;
import dto.CustomerDTO;
import dto.ItemDTO;
import entity.Customer;
import entity.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.DAOFactory;

import repository.custom.ItemDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public boolean saveItem(ItemDTO dto, Connection connection) throws SQLException, ClassNotFoundException {
        Item item = new Item(dto.getId(), dto.getItem(), Double.parseDouble(dto.getUnitPrice()), Integer.parseInt(dto.getQty()));
        return itemDAO.add(item, connection);
    }

    @Override
    public ItemDTO searchItem(String itemId, Connection connection) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(itemId, connection);
        String price= String.valueOf(item.getUnitPrice());
        String qty= String.valueOf(item.getQty());
        ItemDTO itemDTO = new ItemDTO(
                item.getId(), item.getItem(), price, qty
        );
        return itemDTO;
    }

    @Override
    public ObservableList<ItemDTO> getAllItems(Connection connection) throws SQLException, ClassNotFoundException {
        ObservableList<ItemDTO> allItems = FXCollections.observableArrayList();
        ArrayList<Item> items = itemDAO.getAll(connection);

        for (Item item : items) {

            String price= String.valueOf(item.getUnitPrice());
            String qty= String.valueOf(item.getQty());
            ItemDTO dto = new ItemDTO(item.getId(),item.getItem(), price, qty);


            allItems.add(dto);

        }

        return allItems;
    }

    @Override
    public String generateItemID(Connection connection) throws SQLException, ClassNotFoundException {
        return itemDAO.generateItemID(connection);
    }

    @Override
    public boolean updateItem(ItemDTO dto, Connection connection) throws SQLException, ClassNotFoundException {
        Item item = new Item(dto.getId(), dto.getItem(), Double.parseDouble(dto.getUnitPrice()), Integer.parseInt(dto.getQty()));

        return itemDAO.update(item, connection);
    }

    @Override
    public boolean deleteItem(String itemId, Connection connection) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(itemId, connection);
    }
}
