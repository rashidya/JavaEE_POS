package entity;

public class Item {
    private String id;
    private String item;
    private double unitPrice;
    private int qty;

    public Item() {
    }

    public Item(String id, String item, double unitPrice, int qty) {
        this.setId(id);
        this.setItem(item);
        this.setUnitPrice(unitPrice);
        this.setQty(qty);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
