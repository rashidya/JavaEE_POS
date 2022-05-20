package business.custom;

import business.SuperBO;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface PlaceOrderBO extends SuperBO {
    CustomerDTO getCustomer(String cutomerId, Connection connection) throws SQLException, ClassNotFoundException;

    ItemDTO getItem(String id, Connection connection) throws SQLException, ClassNotFoundException;

    OrderDTO getOrder(String id, Connection connection) throws SQLException, ClassNotFoundException;

    boolean placeOrder(OrderDTO orderDTO, Connection connection);
}
