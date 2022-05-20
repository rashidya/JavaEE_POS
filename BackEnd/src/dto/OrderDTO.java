package dto;

import java.util.ArrayList;

public class OrderDTO {
    private String orderId;
    private String orderDate;
    private String customerId;
    private ArrayList<OrderDetailsDTO> orderItems;
    private String total;

    public OrderDTO() {
    }

    public OrderDTO(String orderId, String orderDate, String customerId, ArrayList<OrderDetailsDTO> orderItems, String total) {
        this.setOrderId(orderId);
        this.setOrderDate(orderDate);
        this.setCustomerId(customerId);
        this.setOrderItems(orderItems);
        this.setTotal(total);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public ArrayList<OrderDetailsDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<OrderDetailsDTO> orderItems) {
        this.orderItems = orderItems;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
