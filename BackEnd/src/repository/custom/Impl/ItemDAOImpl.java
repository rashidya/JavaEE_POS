package repository.custom.Impl;

import entity.Item;
import repository.CrudUtil;
import repository.custom.ItemDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean add(Item item, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"INSERT INTO Item VALUES(?,?,?,?)",item.getId(),item.getItem(),item.getUnitPrice(),item.getQty());

    }

    @Override
    public boolean update(Item item, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"UPDATE Item SET item=?,unitPrice=?,qty=? WHERE id=?",item.getItem(),item.getUnitPrice(),item.getQty(),item.getId());

    }

    @Override
    public boolean delete(String id, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"DELETE FROM Item WHERE id=?",id);

    }

    @Override
    public ArrayList<Item> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Item> items=new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery(connection, "SELECT * FROM Item");
        while (rst.next()){
            items.add(new Item(rst.getString(1),rst.getString(2),rst.getDouble(3),rst.getInt(4)));
        }
        return items;
    }

    @Override
    public Item search(String id, Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery(connection,"SELECT * FROM Item WHERE id =?", id);
        if (rst.next()){
            return new Item(rst.getString(1),rst.getString(2),rst.getDouble(3),rst.getInt(4));

        }
        return null;
    }

    @Override
    public String generateItemID(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery(connection,"SELECT id From Item ORDER BY id DESC LIMIT 1");
        if (resultSet.next()){
            int tempId=Integer.parseInt(resultSet.getString(1).split("-")[1]);
            tempId++;
            return  (tempId<10)?"I-00"+tempId:(tempId<100)?"I-0"+tempId:"I-"+tempId;
        }else {
            return "I-001";
        }
    }

    @Override
    public boolean updateStock(Connection con, String itemCode, int cusQty, String addOrRemove) throws SQLException, ClassNotFoundException {
        int newQtyOnHand=0;
        for (Item temp:getAll(con)) {
            if(temp.getId().equals(itemCode)){
                newQtyOnHand=(addOrRemove.equals("remove"))?temp.getQty()-cusQty:temp.getQty()+cusQty;
            }
        }
        return CrudUtil.executeUpdate(con,"UPDATE Item SET qty=? WHERE id=?",newQtyOnHand,itemCode);
    }
}
