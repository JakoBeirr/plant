package be.boomkwekerij.plant.view.mapper;

import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.dto.FustDTO;
import be.boomkwekerij.plant.view.model.FustViewModel;

public class FustViewMapper {

    public FustViewModel mapDTOToViewModel(FustDTO fustDTO) {
        FustViewModel fustViewModel = new FustViewModel();
        fustViewModel.setId(fustDTO.getId());
        CustomerDTO customer = fustDTO.getCustomer();
        if (customer != null) {
            fustViewModel.setCustomerName(customer.getName1());
        }
        fustViewModel.setLageKisten(fustDTO.getLageKisten());
        fustViewModel.setHogeKisten(fustDTO.getHogeKisten());
        fustViewModel.setPalletBodem(fustDTO.getPalletBodem());
        fustViewModel.setBoxPallet(fustDTO.getBoxPallet());
        fustViewModel.setHalveBox(fustDTO.getHalveBox());
        fustViewModel.setFerroPalletKlein(fustDTO.getFerroPalletKlein());
        fustViewModel.setFerroPalletGroot(fustDTO.getFerroPalletGroot());
        fustViewModel.setKarrenEnBorden(fustDTO.getKarrenEnBorden());
        fustViewModel.setDiverse(fustDTO.getDiverse());
        return fustViewModel;
    }
}
