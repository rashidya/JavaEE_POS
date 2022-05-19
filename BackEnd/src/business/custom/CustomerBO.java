package business.custom;

import business.SuperBO;
import dto.CustomerDTO;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.SQLException;

public interface CustomerBO extends SuperBO {
    boolean addCustomer(CustomerDTO dto, Connection connection) throws SQLException, ClassNotFoundException;

    ObservableList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException, ClassNotFoundException;

    CustomerDTO searchCustomer(String cutomerId, Connection connection) throws SQLException, ClassNotFoundException;

    String generateID(Connection connection) throws SQLException, ClassNotFoundException;

    boolean updateCustomer(CustomerDTO customerDTO, Connection connection) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(String cusId, Connection connection) throws SQLException, ClassNotFoundException;
}
