package dto;

public class ItemDTO {
    private String id;
    private String item;
    private Double unitPrice;
    private int qty;

    public ItemDTO() {
    }

    public ItemDTO(String id, String item, Double unitPrice, int qty) {
        this.id = id;
        this.item = item;
        this.unitPrice = unitPrice;
        this.qty = qty;
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

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
