package repository.custom.Impl;

import entity.Customer;
import repository.CrudUtil;
import repository.custom.CustomerDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean add(Customer customer, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"INSERT INTO Customer VALUES(?,?,?,?)",customer.getId(),customer.getName(),customer.getAddress(),customer.getContact_No());
       /* PreparedStatement pstm = connection.prepareStatement("INSERT INTO Customer VALUES(?,?,?,?,?,?,?)");
        pstm.setObject(1,c.getCustomerId());
        pstm.setObject(2,c.getCustomerName());
        pstm.setObject(3,c.getGender());
        pstm.setObject(4,c.getContact());
        pstm.setObject(5,c.getNic());
        pstm.setObject(6,c.getAddress());
        pstm.setObject(7,c.getEmail());

        if (pstm.executeUpdate()>0) {
            return true;
        }
        return false;*/
    }

    @Override
    public boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Customer search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }
}
