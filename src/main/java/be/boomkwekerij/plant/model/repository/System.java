package be.boomkwekerij.plant.model.repository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class System {

    private long lastCompanyId;
    private long lastCustomerId;
    private long lastInvoiceId;
    private long lastPlantId;
    private String lastInvoiceNumber;

    public long getLastCompanyId() {
        return lastCompanyId;
    }

    @XmlElement
    public void setLastCompanyId(long lastCompanyId) {
        this.lastCompanyId = lastCompanyId;
    }

    public long getLastCustomerId() {
        return lastCustomerId;
    }

    @XmlElement
    public void setLastCustomerId(long lastCustomerId) {
        this.lastCustomerId = lastCustomerId;
    }

    public long getLastInvoiceId() {
        return lastInvoiceId;
    }

    @XmlElement
    public void setLastInvoiceId(long lastInvoiceId) {
        this.lastInvoiceId = lastInvoiceId;
    }

    public long getLastPlantId() {
        return lastPlantId;
    }

    @XmlElement
    public void setLastPlantId(long lastPlantId) {
        this.lastPlantId = lastPlantId;
    }

    public String getLastInvoiceNumber() {
        return lastInvoiceNumber;
    }

    @XmlElement
    public void setLastInvoiceNumber(String lastInvoiceNumber) {
        this.lastInvoiceNumber = lastInvoiceNumber;
    }
}
