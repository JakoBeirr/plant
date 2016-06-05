package be.boomkwekerij.plant.model.repository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Customer {

    private String id;
    private String name1;
    private String name2;
    private String address1;
    private String address2;
    private String postalCode;
    private String residence;
    private String country;
    private String telephone;
    private String gsm;
    private String fax;
    private String btwNumber;
    private String emailAddress;

    public String getId() {
        return id;
    }

    @XmlElement
    public void setId(String id) {
        this.id = id;
    }

    public String getName1() {
        return name1;
    }

    @XmlElement
    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    @XmlElement
    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getAddress1() {
        return address1;
    }

    @XmlElement
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    @XmlElement
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    @XmlElement
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getResidence() {
        return residence;
    }

    @XmlElement
    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getCountry() {
        return country;
    }

    @XmlElement
    public void setCountry(String country) {
        this.country = country;
    }

    public String getTelephone() {
        return telephone;
    }

    @XmlElement
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getGsm() {
        return gsm;
    }

    @XmlElement
    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    public String getFax() {
        return fax;
    }

    @XmlElement
    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getBtwNumber() {
        return btwNumber;
    }

    @XmlElement
    public void setBtwNumber(String btwNumber) {
        this.btwNumber = btwNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    @XmlElement
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
