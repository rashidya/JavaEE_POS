package repository.custom.Impl;

import entity.Customer;
import repository.CrudUtil;
import repository.custom.CustomerDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean add(Customer customer, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"INSERT INTO Customer VALUES(?,?,?,?)",customer.getId(),customer.getName(),customer.getAddress(),customer.getContact_No());

    }

    @Override
    public boolean update(Customer customer, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"UPDATE Customer SET name=?,address=?,contact_No=? WHERE id=?",customer.getName(),customer.getAddress(),customer.getContact_No(),customer.getId());

    }


    @Override
    public boolean delete(String id,Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"DELETE FROM Customer WHERE id=?",id);

    }

    @Override
    public ArrayList<Customer> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers=new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery(connection, "SELECT * FROM Customer");
        while (rst.next()){
            customers.add(new Customer(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4)));
        }
        return customers;
    }

    @Override
    public Customer search(String id,Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery(connection,"SELECT * FROM Customer WHERE id =?", id);
        if (rst.next()){
            return new Customer(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4));

        }
        return null;
    }

    @Override
    public String generateID(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery(connection,"SELECT id From Customer ORDER BY id DESC LIMIT 1");
        if (resultSet.next()){
            int tempId=Integer.parseInt(resultSet.getString(1).split("-")[1]);
            tempId++;
            return  (tempId<10)?"C-00"+tempId:(tempId<100)?"C-0"+tempId:"C-"+tempId;
        }else {
            return "C-001";
        }
    }
}
