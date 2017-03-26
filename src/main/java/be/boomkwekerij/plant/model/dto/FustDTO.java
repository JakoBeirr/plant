package be.boomkwekerij.plant.model.dto;

public class FustDTO {

    private String id;
    private CustomerDTO customer;
    private int lageKisten;
    private int hogeKisten;
    private int palletBodem;
    private int boxPallet;
    private int halveBox;
    private int ferroPalletKlein;
    private int ferroPalletGroot;
    private int karrenEnBorden;
    private int diverse;

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

    public int getLageKisten() {
        return lageKisten;
    }

    public void setLageKisten(int lageKisten) {
        this.lageKisten = lageKisten;
    }

    public int getHogeKisten() {
        return hogeKisten;
    }

    public void setHogeKisten(int hogeKisten) {
        this.hogeKisten = hogeKisten;
    }

    public int getPalletBodem() {
        return palletBodem;
    }

    public void setPalletBodem(int palletBodem) {
        this.palletBodem = palletBodem;
    }

    public int getBoxPallet() {
        return boxPallet;
    }

    public void setBoxPallet(int boxPallet) {
        this.boxPallet = boxPallet;
    }

    public int getHalveBox() {
        return halveBox;
    }

    public void setHalveBox(int halveBox) {
        this.halveBox = halveBox;
    }

    public int getFerroPalletKlein() {
        return ferroPalletKlein;
    }

    public void setFerroPalletKlein(int ferroPalletKlein) {
        this.ferroPalletKlein = ferroPalletKlein;
    }

    public int getFerroPalletGroot() {
        return ferroPalletGroot;
    }

    public void setFerroPalletGroot(int ferroPalletGroot) {
        this.ferroPalletGroot = ferroPalletGroot;
    }

    public int getKarrenEnBorden() {
        return karrenEnBorden;
    }

    public void setKarrenEnBorden(int karrenEnBorden) {
        this.karrenEnBorden = karrenEnBorden;
    }

    public int getDiverse() {
        return diverse;
    }

    public void setDiverse(int diverse) {
        this.diverse = diverse;
    }
}