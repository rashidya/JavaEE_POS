package business.custom.Impl;

import business.custom.PlaceOrderBO;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;
import dto.OrderDetailsDTO;
import entity.Customer;
import entity.Item;
import entity.Order;
import entity.OrderDetail;
import repository.DAOFactory;
import repository.custom.CustomerDAO;
import repository.custom.ItemDAO;
import repository.custom.OrderDAO;
import repository.custom.OrderDetailDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class PlaceOrderBOImpl implements PlaceOrderBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);

    @Override
    public CustomerDTO getCustomer(String cusId, Connection connection) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(cusId, connection);
        CustomerDTO customerDto = new CustomerDTO(
                customer.getId(), customer.getName(), customer.getAddress(), customer.getContact_No()
        );
        return customerDto;
    }

    @Override
    public ItemDTO getItem(String id, Connection connection) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(id, connection);
        String price= String.valueOf(item.getUnitPrice());
        String qty= String.valueOf(item.getQty());
        ItemDTO itemDTO = new ItemDTO(
                item.getId(), item.getItem(), price, qty
        );
        return itemDTO;
    }

    @Override
    public OrderDTO getOrder(String id, Connection connection) throws SQLException, ClassNotFoundException {
        Order order =orderDAO.search(id, connection);

        ArrayList<OrderDetail> orderItems=orderDetailDAO.getOrderItems(id,connection);
        ArrayList<OrderDetailsDTO> itemList=new ArrayList<>();

        for (OrderDetail orderItem : orderItems) {
            itemList.add(new OrderDetailsDTO(orderItem.getOrderId(),orderItem.getItemCode(),orderItem.getCusQty()));
        }

        OrderDTO orderDto = new OrderDTO(
                order.getOrderId(), String.valueOf(order.getOrderDate()), order.getCustomerId(),itemList, String.valueOf(order.getTotal())
        );
        return orderDto;

    }

    @Override
    public boolean placeOrder(OrderDTO dto, Connection connection) {
        Connection con= null;

        try {
            con = connection;
            con.setAutoCommit(false);

            Order order = new Order(dto.getOrderId(), LocalDate.parse(dto.getOrderDate()), dto.getCustomerId(),Double.parseDouble(dto.getTotal()));

            boolean orderAdded = orderDAO.add(order,con);

            if(orderAdded){
                ArrayList<OrderDetailsDTO> orderItems = dto.getOrderItems();

                AddOrderItems:
                for (OrderDetailsDTO orderItem : orderItems) {
                    OrderDetail orderDetail = new OrderDetail(orderItem.getOrderId(), orderItem.getItemCode(), orderItem.getCusQty());
                    if(orderDetailDAO.add(orderDetail,con)) {
                        boolean updateStock = itemDAO.updateStock(con,orderItem.getItemCode(), orderItem.getCusQty(), "remove");
                        if (updateStock) {
                            continue AddOrderItems;
                        }else {
                            con.rollback();
                            return false;
                        }
                    }else{
                        con.rollback();
                        return false;
                    }
                }
                con.commit();
                return true;
            }else{
                con.rollback();
                return false;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }

}
