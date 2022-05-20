package entity;

public class OrderDetail {
    private String orderId;
    private String itemCode;
    private int cusQty;

    public OrderDetail() {
    }

    public OrderDetail(String orderId, String itemCode, int cusQty) {
        this.setOrderId(orderId);
        this.setItemCode(itemCode);
        this.setCusQty(cusQty);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getCusQty() {
        return cusQty;
    }

    public void setCusQty(int cusQty) {
        this.cusQty = cusQty;
    }
}
