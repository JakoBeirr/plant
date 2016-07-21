package be.boomkwekerij.plant.view.model;

import javafx.beans.property.SimpleStringProperty;

public class CustomerViewModel {

    private final SimpleStringProperty id = new SimpleStringProperty("");
    private final SimpleStringProperty name1 = new SimpleStringProperty("");
    private final SimpleStringProperty name2 = new SimpleStringProperty("");
    private final SimpleStringProperty address1 = new SimpleStringProperty("");
    private final SimpleStringProperty address2 = new SimpleStringProperty("");
    private final SimpleStringProperty postalCode = new SimpleStringProperty("");
    private final SimpleStringProperty residence = new SimpleStringProperty("");
    private final SimpleStringProperty country = new SimpleStringProperty("");
    private final SimpleStringProperty telephone = new SimpleStringProperty("");
    private final SimpleStringProperty gsm = new SimpleStringProperty("");
    private final SimpleStringProperty fax = new SimpleStringProperty("");
    private final SimpleStringProperty btwNumber = new SimpleStringProperty("");
    private final SimpleStringProperty emailAddress = new SimpleStringProperty("");

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getName1() {
        return name1.get();
    }

    public void setName1(String name1) {
        this.name1.set(name1);
    }

    public String getName2() {
        return name2.get();
    }

    public void setName2(String name2) {
        this.name2.set(name2);
    }

    public String getAddress1() {
        return address1.get();
    }

    public void setAddress1(String address1) {
        this.address1.set(address1);
    }

    public String getAddress2() {
        return address2.get();
    }

    public void setAddress2(String address2) {
        this.address2.set(address2);
    }

    public String getPostalCode() {
        return postalCode.get();
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }

    public String getResidence() {
        return residence.get();
    }

    public void setResidence(String residence) {
        this.residence.set(residence);
    }

    public String getCountry() {
        return country.get();
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public String getTelephone() {
        return telephone.get();
    }

    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }

    public String getGsm() {
        return gsm.get();
    }

    public void setGsm(String gsm) {
        this.gsm.set(gsm);
    }

    public String getFax() {
        return fax.get();
    }

    public void setFax(String fax) {
        this.fax.set(fax);
    }

    public String getBtwNumber() {
        return btwNumber.get();
    }

    public void setBtwNumber(String btwNumber) {
        this.btwNumber.set(btwNumber);
    }

    public String getEmailAddress() {
        return emailAddress.get();
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress.set(emailAddress);
    }
}
