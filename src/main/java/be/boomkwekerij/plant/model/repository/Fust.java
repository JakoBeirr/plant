package be.boomkwekerij.plant.model.repository;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class Fust {

    private String id;
    private String customerId;
    private int lageKisten;
    private int hogeKisten;
    private int palletBodem;
    private int boxPallet;
    private int halveBox;
    private int ferroPalletKlein;
    private int ferroPalletGroot;
    private int karrenEnBorden;
    private int diverse;
    private Date datum;

    public String getId() {
        return id;
    }

    @XmlElement
    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    @XmlElement
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getLageKisten() {
        return lageKisten;
    }

    @XmlElement
    public void setLageKisten(int lageKisten) {
        this.lageKisten = lageKisten;
    }

    public int getHogeKisten() {
        return hogeKisten;
    }

    @XmlElement
    public void setHogeKisten(int hogeKisten) {
        this.hogeKisten = hogeKisten;
    }

    public int getPalletBodem() {
        return palletBodem;
    }

    @XmlElement
    public void setPalletBodem(int palletBodem) {
        this.palletBodem = palletBodem;
    }

    public int getBoxPallet() {
        return boxPallet;
    }

    @XmlElement
    public void setBoxPallet(int boxPallet) {
        this.boxPallet = boxPallet;
    }

    public int getHalveBox() {
        return halveBox;
    }

    @XmlElement
    public void setHalveBox(int halveBox) {
        this.halveBox = halveBox;
    }

    public int getFerroPalletKlein() {
        return ferroPalletKlein;
    }

    @XmlElement
    public void setFerroPalletKlein(int ferroPalletKlein) {
        this.ferroPalletKlein = ferroPalletKlein;
    }

    public int getFerroPalletGroot() {
        return ferroPalletGroot;
    }

    @XmlElement
    public void setFerroPalletGroot(int ferroPalletGroot) {
        this.ferroPalletGroot = ferroPalletGroot;
    }

    public int getKarrenEnBorden() {
        return karrenEnBorden;
    }

    @XmlElement
    public void setKarrenEnBorden(int karrenEnBorden) {
        this.karrenEnBorden = karrenEnBorden;
    }

    public int getDiverse() {
        return diverse;
    }

    @XmlElement
    public void setDiverse(int diverse) {
        this.diverse = diverse;
    }

    public Date getDatum() {
        return datum;
    }

    @XmlElement
    public void setDatum(Date datum) {
        this.datum = datum;
    }
}
