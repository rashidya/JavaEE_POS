package repository.custom;

import entity.Customer;
import repository.CrudDAO;
import repository.SuperDAO;

import java.sql.Connection;
import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer,String> {

    String generateID(Connection connection) throws SQLException, ClassNotFoundException;
}
