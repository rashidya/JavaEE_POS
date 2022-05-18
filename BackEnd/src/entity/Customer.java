package entity;

public class Customer {
    private String id;
    private String name;
    private String address;
    private String contact_No;

    public Customer() {
    }

    public Customer(String id, String name, String address, String contact_No) {
        this.setId(id);
        this.setName(name);
        this.setAddress(address);
        this.setContact_No(contact_No);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact_No() {
        return contact_No;
    }

    public void setContact_No(String contact_No) {
        this.contact_No = contact_No;
    }
}
