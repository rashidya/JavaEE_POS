package repository.custom;

import entity.OrderDetail;
import repository.CrudDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailDAO extends CrudDAO<OrderDetail,String> {
    ArrayList<OrderDetail> getOrderItems(String id, Connection connection) throws SQLException, ClassNotFoundException;
}
