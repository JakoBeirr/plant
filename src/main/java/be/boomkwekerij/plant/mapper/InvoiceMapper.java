package be.boomkwekerij.plant.mapper;

import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.model.dto.InvoiceLineDTO;
import be.boomkwekerij.plant.model.report.InvoiceLineReportObject;
import be.boomkwekerij.plant.model.report.InvoiceReportObject;
import be.boomkwekerij.plant.model.repository.Invoice;
import be.boomkwekerij.plant.model.repository.InvoiceLine;
import be.boomkwekerij.plant.service.CustomerService;
import be.boomkwekerij.plant.service.CustomerServiceImpl;
import be.boomkwekerij.plant.util.DateFormatPattern;
import be.boomkwekerij.plant.util.DateUtils;
import be.boomkwekerij.plant.util.NumberUtils;
import be.boomkwekerij.plant.util.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class InvoiceMapper {

    private CustomerService customerService = new CustomerServiceImpl();

    private InvoiceLineMapper invoiceLineMapper = new InvoiceLineMapper();

    public Invoice mapDTOToDAO(InvoiceDTO invoiceDTO) {
        Invoice invoice = new Invoice();
        invoice.setId(invoiceDTO.getId());
        invoice.setCustomerId(invoiceDTO.getCustomer().getId());
        invoice.setInvoiceNumber(invoiceDTO.getInvoiceNumber());
        invoice.setDate(invoiceDTO.getDate());
        invoice.setInvoiceLines(getInvoiceLines(invoiceDTO));
        invoice.setBtw(invoiceDTO.getBtw());
        return invoice;
    }

    private List<InvoiceLine> getInvoiceLines(InvoiceDTO invoiceDTO) {
        List<InvoiceLine> invoiceLines = new ArrayList<InvoiceLine>();

        for (InvoiceLineDTO invoiceLineDTO : invoiceDTO.getInvoiceLines()) {
            InvoiceLine invoiceLine = invoiceLineMapper.mapDTOToDAO(invoiceLineDTO);
            invoiceLines.add(invoiceLine);
        }

        return invoiceLines;
    }

    public InvoiceDTO mapDAOToDTO(Invoice invoice) {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setId(invoice.getId());
        invoiceDTO.setCustomer(getCustomer(invoice.getCustomerId()));
        invoiceDTO.setInvoiceNumber(invoice.getInvoiceNumber());
        invoiceDTO.setDate(invoice.getDate());
        List<InvoiceLineDTO> invoiceLines = getInvoiceLines(invoice);
        invoiceDTO.setInvoiceLines(invoiceLines);
        double subTotal = countSubTotal(invoiceLines);
        invoiceDTO.setSubTotal(subTotal);
        invoiceDTO.setBtw(invoice.getBtw());
        double btwAmount = countBtwAmount(subTotal, invoice.getBtw());
        invoiceDTO.setBtwAmount(btwAmount);
        invoiceDTO.setTotalPrice(countTotalPrice(subTotal, btwAmount));
        return invoiceDTO;
    }

    private CustomerDTO getCustomer(String id) {
        SearchResult<CustomerDTO> customerSearchResult = customerService.getCustomer(id);

        if (customerSearchResult.isSuccess()) {
            return customerSearchResult.getFirst();
        }
        return null;
    }

    private List<InvoiceLineDTO> getInvoiceLines(Invoice invoice) {
        List<InvoiceLineDTO> invoiceLinesDTOs = new ArrayList<InvoiceLineDTO>();

        for (InvoiceLine invoiceLine : invoice.getInvoiceLines()) {
            InvoiceLineDTO invoiceLineDTO = invoiceLineMapper.mapDAOToDTO(invoiceLine);
            invoiceLinesDTOs.add(invoiceLineDTO);
        }

        return invoiceLinesDTOs;
    }

    private double countSubTotal(List<InvoiceLineDTO> invoiceLines) {
        double subTotal = 0;

        for (InvoiceLineDTO invoiceLine : invoiceLines) {
            subTotal += invoiceLine.getTotalPrice();
        }

        return subTotal;
    }

    private double countBtwAmount(double subTotal, double btw) {
        return subTotal * btw;
    }

    private double countTotalPrice(double subTotal, double btwAmount) {
        return subTotal + btwAmount;
    }

    public InvoiceReportObject mapDTOToReportObject(InvoiceDTO invoiceDTO) {
        InvoiceReportObject invoiceReportObject = new InvoiceReportObject();
        invoiceReportObject.setInvoiceDate(DateUtils.formatDate(invoiceDTO.getDate(), DateFormatPattern.DATE_FORMAT));
        invoiceReportObject.setInvoiceNumber(invoiceDTO.getInvoiceNumber());
        for (InvoiceLineDTO invoiceLineDTO : invoiceDTO.getInvoiceLines()) {
            InvoiceLineReportObject invoiceLineReportObject = invoiceLineMapper.mapDTOToReportObject(invoiceLineDTO);
            invoiceReportObject.getInvoiceLines().add(invoiceLineReportObject);
        }
        invoiceReportObject.setSubTotal(NumberUtils.formatDouble(invoiceDTO.getSubTotal(), 2));
        invoiceReportObject.setBtw(NumberUtils.formatDouble((invoiceDTO.getBtw()*100), 2));
        invoiceReportObject.setBtwAmount(NumberUtils.formatDouble(invoiceDTO.getBtwAmount(), 2));
        invoiceReportObject.setTotalPrice(NumberUtils.formatDouble(invoiceDTO.getTotalPrice(), 2));
        return invoiceReportObject;
    }
}
