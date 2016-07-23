package be.boomkwekerij.plant.model.repository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class System {

    private String nextInvoiceNumber;

    public String getNextInvoiceNumber() {
        return nextInvoiceNumber;
    }

    @XmlElement
    public void setNextInvoiceNumber(String nextInvoiceNumber) {
        this.nextInvoiceNumber = nextInvoiceNumber;
    }
}
