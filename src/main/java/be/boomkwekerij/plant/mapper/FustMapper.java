package be.boomkwekerij.plant.mapper;

import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.dto.FustDTO;
import be.boomkwekerij.plant.model.repository.Fust;
import org.joda.time.DateTime;

public class FustMapper {

    public Fust mapDTOToDAO(FustDTO fustDTO) {
        Fust fust = new Fust();
        fust.setId(fustDTO.getId());
        fust.setCustomerId(fustDTO.getCustomer().getId());
        fust.setLageKisten(fustDTO.getLageKisten());
        fust.setHogeKisten(fustDTO.getHogeKisten());
        fust.setPalletBodem(fustDTO.getPalletBodem());
        fust.setBoxPallet(fustDTO.getBoxPallet());
        fust.setHalveBox(fustDTO.getHalveBox());
        fust.setFerroPalletKlein(fustDTO.getFerroPalletKlein());
        fust.setFerroPalletGroot(fustDTO.getFerroPalletGroot());
        fust.setKarrenEnBorden(fustDTO.getKarrenEnBorden());
        fust.setDiverse(fustDTO.getDiverse());
        DateTime date = fustDTO.getDatum();
        if (date == null) {
            date = new DateTime();
        }
        fust.setDatum(date.toDate());
        return fust;
    }

    public FustDTO mapDAOToDTO(Fust fust, CustomerDTO customer) {
        FustDTO fustDTO = new FustDTO();
        fustDTO.setId(fust.getId());
        fustDTO.setCustomer(customer);
        fustDTO.setLageKisten(fust.getLageKisten());
        fustDTO.setHogeKisten(fust.getHogeKisten());
        fustDTO.setPalletBodem(fust.getPalletBodem());
        fustDTO.setBoxPallet(fust.getBoxPallet());
        fustDTO.setHalveBox(fust.getHalveBox());
        fustDTO.setFerroPalletKlein(fust.getFerroPalletKlein());
        fustDTO.setFerroPalletGroot(fust.getFerroPalletGroot());
        fustDTO.setKarrenEnBorden(fust.getKarrenEnBorden());
        fustDTO.setDiverse(fust.getDiverse());
        fustDTO.setDatum(new DateTime(fust.getDatum()));
        return fustDTO;
    }
}
