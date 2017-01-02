package be.boomkwekerij.plant.mapper;

import be.boomkwekerij.plant.model.dto.BtwDTO;
import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.model.dto.InvoiceLineDTO;
import be.boomkwekerij.plant.model.repository.Invoice;
import be.boomkwekerij.plant.model.repository.InvoiceLine;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoiceMapper {

    private InvoiceLineMapper invoiceLineMapper = new InvoiceLineMapper();

    public Invoice mapDTOToDAO(InvoiceDTO invoiceDTO) {
        Invoice invoice = new Invoice();
        invoice.setId(invoiceDTO.getId());
        invoice.setCustomerId(invoiceDTO.getCustomer().getId());
        invoice.setInvoiceNumber(invoiceDTO.getInvoiceNumber());
        DateTime date = invoiceDTO.getDate();
        if (date == null) {
            date = new DateTime();
        }
        invoice.setDate(date.toDate());
        invoice.setInvoiceLines(getInvoiceLines(invoiceDTO));
        invoice.setPayed(invoiceDTO.isPayed());
        DateTime payDate = invoiceDTO.getPayDate();
        if (payDate != null) {
            invoice.setPayDate(payDate.toDate());
        }
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

    public InvoiceDTO mapDAOToDTO(Invoice invoice, CustomerDTO customer) {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setId(invoice.getId());
        invoiceDTO.setCustomer(customer);
        invoiceDTO.setInvoiceNumber(invoice.getInvoiceNumber());
        invoiceDTO.setDate(new DateTime(invoice.getDate()));
        List<InvoiceLineDTO> invoiceLines = getInvoiceLines(invoice);
        invoiceDTO.setInvoiceLines(invoiceLines);

        double subTotal = countSubTotal(invoiceLines);
        invoiceDTO.setSubTotal(subTotal);

        Map<Double, Double> btwMap = determineBtw(invoiceLines);
        List<BtwDTO> btwList = mapToList(btwMap);
        invoiceDTO.setBtw(btwList);

        double btwAmount = countBtwAmount(btwMap);
        invoiceDTO.setTotalPrice(countTotalPrice(subTotal, btwAmount));

        invoiceDTO.setPayed(invoice.isPayed());
        invoiceDTO.setPayDate(new DateTime(invoice.getPayDate()));
        return invoiceDTO;
    }

    private List<BtwDTO> mapToList(Map<Double, Double> btwMap) {
        List<BtwDTO> btwList = new ArrayList<>();
        for (Double btwPercentage : btwMap.keySet()) {
            BtwDTO btwDTO = new BtwDTO();
            btwDTO.setBtwPercentage(btwPercentage);
            btwDTO.setBtwAmount(btwMap.get(btwPercentage));
            btwList.add(btwDTO);
        }
        return btwList;
    }

    private double countBtwAmount(Map<Double, Double> btwList) {
        double btwAmount = 0;
        for (Double btw : btwList.values()) {
            btwAmount += btw;
        }
        return btwAmount;
    }

    private Map<Double, Double> determineBtw(List<InvoiceLineDTO> invoiceLines) {
        Map<Double, Double> btwList = new HashMap<>();
        for (InvoiceLineDTO invoiceLine : invoiceLines) {
            double invoiceLineBtwPercentage = invoiceLine.getBtw();
            double invoiceLineBtwAmount = invoiceLine.getBtwAmount();

            Double existingBtwAmount = btwList.get(invoiceLineBtwPercentage);
            if (existingBtwAmount == null) {
                existingBtwAmount = (double) 0;
            }

            double newBtwAmount = existingBtwAmount + invoiceLineBtwAmount;
            btwList.put(invoiceLineBtwPercentage, newBtwAmount);
        }
        return btwList;
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

    private double countTotalPrice(double subTotal, double btwAmount) {
        return subTotal + btwAmount;
    }
}
