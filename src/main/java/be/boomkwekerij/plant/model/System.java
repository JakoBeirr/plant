package be.boomkwekerij.plant.model;

public class System {

    private long lastCustomerId;
    private long lastInvoiceId;
    private long lastPlantId;
    private String lastInvoiceNumber;

    public long getLastCustomerId() {
        return lastCustomerId;
    }

    public void setLastCustomerId(long lastCustomerId) {
        this.lastCustomerId = lastCustomerId;
    }

    public long getLastInvoiceId() {
        return lastInvoiceId;
    }

    public void setLastInvoiceId(long lastInvoiceId) {
        this.lastInvoiceId = lastInvoiceId;
    }

    public long getLastPlantId() {
        return lastPlantId;
    }

    public void setLastPlantId(long lastPlantId) {
        this.lastPlantId = lastPlantId;
    }

    public String getLastInvoiceNumber() {
        return lastInvoiceNumber;
    }

    public void setLastInvoiceNumber(String lastInvoiceNumber) {
        this.lastInvoiceNumber = lastInvoiceNumber;
    }
}
