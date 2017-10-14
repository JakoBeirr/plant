package be.boomkwekerij.plant.model.dto;

public class FustOverviewDTO {

    private CustomerDTO customer;
    private int lageKisten = 0;
    private int hogeKisten = 0;
    private int palletBodem = 0;
    private int boxPallet = 0;
    private int halveBox = 0;
    private int ferroPalletKlein = 0;
    private int ferroPalletGroot = 0;
    private int karrenEnBorden = 0;
    private int diverse = 0;

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public int getLageKisten() {
        return lageKisten;
    }

    public void addLageKisten(int lageKisten) {
        this.lageKisten += lageKisten;
    }

    public int getHogeKisten() {
        return hogeKisten;
    }

    public void addHogeKisten(int hogeKisten) {
        this.hogeKisten += hogeKisten;
    }

    public int getPalletBodem() {
        return palletBodem;
    }

    public void addPalletBodem(int palletBodem) {
        this.palletBodem += palletBodem;
    }

    public int getBoxPallet() {
        return boxPallet;
    }

    public void addBoxPallet(int boxPallet) {
        this.boxPallet += boxPallet;
    }

    public int getHalveBox() {
        return halveBox;
    }

    public void addHalveBox(int halveBox) {
        this.halveBox += halveBox;
    }

    public int getFerroPalletKlein() {
        return ferroPalletKlein;
    }

    public void addFerroPalletKlein(int ferroPalletKlein) {
        this.ferroPalletKlein += ferroPalletKlein;
    }

    public int getFerroPalletGroot() {
        return ferroPalletGroot;
    }

    public void addFerroPalletGroot(int ferroPalletGroot) {
        this.ferroPalletGroot += ferroPalletGroot;
    }

    public int getKarrenEnBorden() {
        return karrenEnBorden;
    }

    public void addKarrenEnBorden(int karrenEnBorden) {
        this.karrenEnBorden += karrenEnBorden;
    }

    public int getDiverse() {
        return diverse;
    }

    public void addDiverse(int diverse) {
        this.diverse += diverse;
    }
}
