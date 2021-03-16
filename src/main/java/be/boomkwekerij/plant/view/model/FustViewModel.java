package be.boomkwekerij.plant.view.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class FustViewModel {

    private String customerId;
    private SimpleStringProperty customerName = new SimpleStringProperty("");
    private SimpleIntegerProperty lageKisten = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty hogeKisten = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty palletBodem = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty boxPallet = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty halveBox = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty ferroPalletKlein = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty ferroPalletGroot = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty karren = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty borden = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty diverse = new SimpleIntegerProperty(0);

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public SimpleStringProperty customerNameProperty() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public int getLageKisten() {
        return lageKisten.get();
    }

    public SimpleIntegerProperty lageKistenProperty() {
        return lageKisten;
    }

    public void setLageKisten(int lageKisten) {
        this.lageKisten.set(lageKisten);
    }

    public int getHogeKisten() {
        return hogeKisten.get();
    }

    public SimpleIntegerProperty hogeKistenProperty() {
        return hogeKisten;
    }

    public void setHogeKisten(int hogeKisten) {
        this.hogeKisten.set(hogeKisten);
    }

    public int getPalletBodem() {
        return palletBodem.get();
    }

    public SimpleIntegerProperty palletBodemProperty() {
        return palletBodem;
    }

    public void setPalletBodem(int palletBodem) {
        this.palletBodem.set(palletBodem);
    }

    public int getBoxPallet() {
        return boxPallet.get();
    }

    public SimpleIntegerProperty boxPalletProperty() {
        return boxPallet;
    }

    public void setBoxPallet(int boxPallet) {
        this.boxPallet.set(boxPallet);
    }

    public int getHalveBox() {
        return halveBox.get();
    }

    public SimpleIntegerProperty halveBoxProperty() {
        return halveBox;
    }

    public void setHalveBox(int halveBox) {
        this.halveBox.set(halveBox);
    }

    public int getFerroPalletKlein() {
        return ferroPalletKlein.get();
    }

    public SimpleIntegerProperty ferroPalletKleinProperty() {
        return ferroPalletKlein;
    }

    public void setFerroPalletKlein(int ferroPalletKlein) {
        this.ferroPalletKlein.set(ferroPalletKlein);
    }

    public int getFerroPalletGroot() {
        return ferroPalletGroot.get();
    }

    public SimpleIntegerProperty ferroPalletGrootProperty() {
        return ferroPalletGroot;
    }

    public void setFerroPalletGroot(int ferroPalletGroot) {
        this.ferroPalletGroot.set(ferroPalletGroot);
    }

    public int getKarren() {
        return karren.get();
    }

    public SimpleIntegerProperty karrenProperty() {
        return karren;
    }

    public void setKarren(int karren) {
        this.karren.set(karren);
    }

    public int getBorden() {
        return borden.get();
    }

    public SimpleIntegerProperty bordenProperty() {
        return borden;
    }

    public void setBorden(int borden) {
        this.borden.set(borden);
    }

    public int getDiverse() {
        return diverse.get();
    }

    public SimpleIntegerProperty diverseProperty() {
        return diverse;
    }

    public void setDiverse(int diverse) {
        this.diverse.set(diverse);
    }
}
