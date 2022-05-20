package business.custom.Impl;

import business.custom.PlaceOrderBO;
import dto.CustomerDTO;
import dto.ItemDTO;
import entity.Customer;
import entity.Item;
import repository.DAOFactory;
import repository.custom.CustomerDAO;
import repository.custom.ItemDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderBOImpl implements PlaceOrderBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public CustomerDTO getCustomer(String cusId, Connection connection) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(cusId, connection);
        CustomerDTO customerDto = new CustomerDTO(
                customer.getId(), customer.getName(), customer.getAddress(), customer.getContact_No()
        );
        return customerDto;
    }

    @Override
    public ItemDTO getItem(String id, Connection connection) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(id, connection);
        String price= String.valueOf(item.getUnitPrice());
        String qty= String.valueOf(item.getQty());
        ItemDTO itemDTO = new ItemDTO(
                item.getId(), item.getItem(), price, qty
        );
        return itemDTO;
    }
}
