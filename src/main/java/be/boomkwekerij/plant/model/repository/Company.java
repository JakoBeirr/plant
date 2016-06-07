package be.boomkwekerij.plant.model.repository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Company {

    private String name;
    private String telephone;
    private String fax;
    private String gsm;
    private String accountNumberBelgium;
    private String ibanBelgium;
    private String bicBelgium;
    private String accountNumberNetherlands;
    private String ibanNetherlands;
    private String bicNetherlands;
    private String btwNumber;

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    @XmlElement
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    @XmlElement
    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getGsm() {
        return gsm;
    }

    @XmlElement
    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    public String getAccountNumberBelgium() {
        return accountNumberBelgium;
    }

    @XmlElement
    public void setAccountNumberBelgium(String accountNumberBelgium) {
        this.accountNumberBelgium = accountNumberBelgium;
    }

    public String getIbanBelgium() {
        return ibanBelgium;
    }

    @XmlElement
    public void setIbanBelgium(String ibanBelgium) {
        this.ibanBelgium = ibanBelgium;
    }

    public String getBicBelgium() {
        return bicBelgium;
    }

    @XmlElement
    public void setBicBelgium(String bicBelgium) {
        this.bicBelgium = bicBelgium;
    }

    public String getAccountNumberNetherlands() {
        return accountNumberNetherlands;
    }

    @XmlElement
    public void setAccountNumberNetherlands(String accountNumberNetherlands) {
        this.accountNumberNetherlands = accountNumberNetherlands;
    }

    public String getIbanNetherlands() {
        return ibanNetherlands;
    }

    @XmlElement
    public void setIbanNetherlands(String ibanNetherlands) {
        this.ibanNetherlands = ibanNetherlands;
    }

    public String getBicNetherlands() {
        return bicNetherlands;
    }

    @XmlElement
    public void setBicNetherlands(String bicNetherlands) {
        this.bicNetherlands = bicNetherlands;
    }

    public String getBtwNumber() {
        return btwNumber;
    }

    @XmlElement
    public void setBtwNumber(String btwNumber) {
        this.btwNumber = btwNumber;
    }
}
