package be.boomkwekerij.plant.view.mapper;

import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.dto.FustOverviewDTO;
import be.boomkwekerij.plant.view.model.FustViewModel;

public class FustViewMapper {

    public FustViewModel mapDTOToViewModel(FustOverviewDTO fust) {
        FustViewModel fustViewModel = new FustViewModel();
        CustomerDTO customer = fust.getCustomer();
        if (customer != null) {
            fustViewModel.setCustomerId(customer.getId());
            fustViewModel.setCustomerName(customer.getName1());
        }
        fustViewModel.setLageKisten(fust.getLageKisten());
        fustViewModel.setHogeKisten(fust.getHogeKisten());
        fustViewModel.setPalletBodem(fust.getPalletBodem());
        fustViewModel.setBoxPallet(fust.getBoxPallet());
        fustViewModel.setHalveBox(fust.getHalveBox());
        fustViewModel.setFerroPalletKlein(fust.getFerroPalletKlein());
        fustViewModel.setFerroPalletGroot(fust.getFerroPalletGroot());
        fustViewModel.setKarren(fust.getKarren());
        fustViewModel.setBorden(fust.getBorden());
        fustViewModel.setDiverse(fust.getDiverse());
        return fustViewModel;
    }
}
