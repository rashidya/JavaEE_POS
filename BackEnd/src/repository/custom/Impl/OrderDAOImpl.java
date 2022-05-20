package repository.custom.Impl;

import entity.Customer;
import entity.Order;
import repository.CrudUtil;
import repository.custom.OrderDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public boolean add(Order order, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"INSERT INTO `Order` VALUES(?,?,?,?)",order.getOrderId(),order.getOrderDate(),order.getCustomerId(),order.getTotal());

    }

    @Override
    public boolean update(Order order, Connection connection) throws SQLException, ClassNotFoundException {
      /*  return CrudUtil.executeUpdate(connection,"UPDATE `Order` SET orderDate=? total=? WHERE orderId=?",order.getOrderDate(),order.getTotal(),order.getOrderId());
*/
        return false;
    }

    @Override
    public boolean delete(String id, Connection connection) throws SQLException, ClassNotFoundException {
       /* return CrudUtil.executeUpdate(connection,"DELETE FROM `Order` WHERE orderId=?",id);*/
        return false;
    }

    @Override
    public ArrayList<Order> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Order search(String id, Connection connection) throws SQLException, ClassNotFoundException {

        return null;
    }
}
