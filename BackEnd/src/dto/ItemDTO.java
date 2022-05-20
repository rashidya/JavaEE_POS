package dto;

public class ItemDTO {
    private String id;
    private String item;
    private String unitPrice;
    private String qty;

    public ItemDTO() {
    }

    public ItemDTO(String id, String item, String unitPrice, String qty) {
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

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
