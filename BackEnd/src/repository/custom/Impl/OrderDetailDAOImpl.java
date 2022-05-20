package repository.custom.Impl;

import entity.OrderDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.CrudUtil;
import repository.custom.OrderDetailDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public boolean add(OrderDetail orderDetail, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"INSERT INTO OrderDetail VALUES(?,?,?)",orderDetail.getOrderId(),orderDetail.getItemCode(),orderDetail.getCusQty());

    }

    @Override
    public boolean update(OrderDetail orderDetail, Connection connection) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s, Connection connection) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<OrderDetail> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public OrderDetail search(String s, Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<OrderDetail> getOrderItems(String id, Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> orderItems = new ArrayList<>();
        ResultSet resultSet = CrudUtil.executeQuery(connection,"SELECT  * From `OrderDetail` WHERE OrderId =?", id);
        while (resultSet.next()){
            OrderDetail temp =new OrderDetail(resultSet.getString(1),resultSet.getString(2),resultSet.getInt(3));
            orderItems.add(temp);
        }
        return orderItems;
    }
}
