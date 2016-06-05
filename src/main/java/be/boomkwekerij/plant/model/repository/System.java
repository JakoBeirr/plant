package be.boomkwekerij.plant.model.repository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class System {

    private String lastInvoiceNumber;

    public String getLastInvoiceNumber() {
        return lastInvoiceNumber;
    }

    @XmlElement
    public void setLastInvoiceNumber(String lastInvoiceNumber) {
        this.lastInvoiceNumber = lastInvoiceNumber;
    }
}
