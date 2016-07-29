package be.boomkwekerij.plant.view.mapper;

import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.util.DateFormatPattern;
import be.boomkwekerij.plant.util.DateUtils;
import be.boomkwekerij.plant.util.NumberUtils;
import be.boomkwekerij.plant.view.model.InvoiceViewModel;

public class InvoiceViewMapper {

    public InvoiceViewModel mapDTOToViewModel(InvoiceDTO invoiceDTO) {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        invoiceViewModel.setId(invoiceDTO.getId());
        invoiceViewModel.setCustomerName(invoiceDTO.getCustomer().getName1());
        invoiceViewModel.setInvoiceNumber(invoiceDTO.getInvoiceNumber());
        invoiceViewModel.setDate(DateUtils.formatDate(invoiceDTO.getDate(), DateFormatPattern.DATE_FORMAT));
        invoiceViewModel.setTotalPrice(NumberUtils.formatDouble(invoiceDTO.getTotalPrice(), 2));
        invoiceViewModel.setPayed(invoiceDTO.isPayed());
        return invoiceViewModel;
    }
}
