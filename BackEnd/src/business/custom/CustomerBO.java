package business.custom;

import business.SuperBO;
import dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface CustomerBO extends SuperBO {
    public boolean addCustomer(CustomerDTO dto, Connection connection) throws SQLException, ClassNotFoundException;
}
