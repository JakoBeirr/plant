package be.boomkwekerij.plant.model.dto;

public class FustDTO {

    private String id;
    private CustomerDTO customer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }
}
