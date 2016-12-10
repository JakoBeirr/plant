package be.boomkwekerij.plant.mapper;

import be.boomkwekerij.plant.model.dto.BtwDTO;
import be.boomkwekerij.plant.model.dto.CustomerDTO;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.model.dto.InvoiceLineDTO;
import be.boomkwekerij.plant.model.report.BtwReportObject;
import be.boomkwekerij.plant.model.report.InvoiceLineReportObject;
import be.boomkwekerij.plant.model.report.MultiplePagedInvoiceReportObject;
import be.boomkwekerij.plant.model.report.OnePagedInvoiceReportObject;
import be.boomkwekerij.plant.model.repository.Invoice;
import be.boomkwekerij.plant.model.repository.InvoiceLine;
import be.boomkwekerij.plant.service.CustomerService;
import be.boomkwekerij.plant.service.CustomerServiceImpl;
import be.boomkwekerij.plant.util.*;
import org.joda.time.DateTime;

import java.util.*;

public class InvoiceMapper {

    private CustomerService customerService = new CustomerServiceImpl();

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

    private double countTotalPrice(double subTotal, double btwAmount) {
        return subTotal + btwAmount;
    }

    public OnePagedInvoiceReportObject mapDTOToOnePagedReportObject(InvoiceDTO invoiceDTO) {
        OnePagedInvoiceReportObject onePagedInvoiceReportObject = new OnePagedInvoiceReportObject();
        onePagedInvoiceReportObject.setInvoiceDate(DateUtils.formatDate(invoiceDTO.getDate(), DateFormatPattern.DATE_FORMAT));
        onePagedInvoiceReportObject.setInvoiceNumber(invoiceDTO.getInvoiceNumber());
        List<InvoiceLineDTO> invoiceLines = invoiceDTO.getInvoiceLines();
        sortInvoiceLinesByDate(invoiceLines);
        for (InvoiceLineDTO invoiceLineDTO : invoiceLines) {
            InvoiceLineReportObject invoiceLineReportObject = invoiceLineMapper.mapDTOToReportObject(invoiceLineDTO);
            onePagedInvoiceReportObject.getInvoiceLines().add(invoiceLineReportObject);
        }
        removeUnnecessaryDates(onePagedInvoiceReportObject.getInvoiceLines());
        onePagedInvoiceReportObject.setHasOrderNumbers(checkIfInvoiceHasOrderNumbers(invoiceLines));
        onePagedInvoiceReportObject.setSubTotal(NumberUtils.formatDouble(invoiceDTO.getSubTotal(), 2));
        List<BtwDTO> btwList = invoiceDTO.getBtw();
        sortBtwByPercentage(btwList);
        onePagedInvoiceReportObject.setBtw(mapToBtwReportObject(btwList));
        onePagedInvoiceReportObject.setTotalPrice(NumberUtils.formatDouble(invoiceDTO.getTotalPrice(), 2));
        return onePagedInvoiceReportObject;
    }

    public List<MultiplePagedInvoiceReportObject> mapDTOToMultiplePagedReportObject(InvoiceDTO invoiceDTO, int amountOfPages) {
        List<MultiplePagedInvoiceReportObject> multiplePagedInvoiceReportObjects = new ArrayList<>();
        for (int i = 1; i <= amountOfPages; i++) {
            MultiplePagedInvoiceReportObject multiplePagedInvoiceReportObject = new MultiplePagedInvoiceReportObject();
            multiplePagedInvoiceReportObject.setInvoiceDate(DateUtils.formatDate(invoiceDTO.getDate(), DateFormatPattern.DATE_FORMAT));
            multiplePagedInvoiceReportObject.setInvoiceNumber(invoiceDTO.getInvoiceNumber());
            List<InvoiceLineDTO> invoiceLines = getInvoiceLinesToMap(invoiceDTO, i);
            sortInvoiceLinesByDate(invoiceLines);
            for (InvoiceLineDTO invoiceLineDTO : invoiceLines) {
                InvoiceLineReportObject invoiceLineReportObject = invoiceLineMapper.mapDTOToReportObject(invoiceLineDTO);
                multiplePagedInvoiceReportObject.getInvoiceLines().add(invoiceLineReportObject);
            }
            multiplePagedInvoiceReportObject.setTransportPreviousPage(NumberUtils.formatDouble(getTotalPricePage(invoiceDTO, i-1), 2));
            multiplePagedInvoiceReportObject.setTransportCurrentPage(NumberUtils.formatDouble(getTotalPricePage(invoiceDTO, i), 2));
            removeUnnecessaryDates(multiplePagedInvoiceReportObject.getInvoiceLines());
            multiplePagedInvoiceReportObject.setHasOrderNumbers(checkIfInvoiceHasOrderNumbers(invoiceDTO.getInvoiceLines()));
            multiplePagedInvoiceReportObject.setSubTotal(NumberUtils.formatDouble(invoiceDTO.getSubTotal(), 2));
            List<BtwDTO> btwList = invoiceDTO.getBtw();
            sortBtwByPercentage(btwList);
            multiplePagedInvoiceReportObject.setBtw(mapToBtwReportObject(btwList));
            multiplePagedInvoiceReportObject.setTotalPrice(NumberUtils.formatDouble(invoiceDTO.getTotalPrice(), 2));
            multiplePagedInvoiceReportObjects.add(multiplePagedInvoiceReportObject);
        }
        return multiplePagedInvoiceReportObjects;
    }

    private void sortInvoiceLinesByDate(List<InvoiceLineDTO> invoiceLines) {
        if (invoiceLines.size() > 0) {
            Collections.sort(invoiceLines, new Comparator<InvoiceLineDTO>() {
                public int compare(InvoiceLineDTO invoiceLineDTO1, InvoiceLineDTO invoiceLineDTO2) {
                    return invoiceLineDTO1.getDate().compareTo(invoiceLineDTO2.getDate());
                }
            });
        }
    }

    private List<InvoiceLineDTO> getInvoiceLinesToMap(InvoiceDTO invoiceDTO, int pageCount) {
        int fromIndex = (pageCount - 1) * Initializer.MAX_INVOICELINES;
        int expectedToIndex = pageCount * Initializer.MAX_INVOICELINES;
        int maximumToIndex = invoiceDTO.getInvoiceLines().size();
        int toIndex = expectedToIndex > maximumToIndex ? maximumToIndex : expectedToIndex;
        return invoiceDTO.getInvoiceLines().subList(fromIndex, toIndex);
    }

    private double getTotalPricePage(InvoiceDTO invoiceDTO, int pageCount) {
        if (pageCount == 0) {
            return 0.0;
        } else {
            double totalPrice = 0.0;
            for (int i = pageCount; i > 0; i--) {
                List<InvoiceLineDTO> invoiceLinesOfPage = getInvoiceLinesToMap(invoiceDTO, i);
                totalPrice += countSubTotal(invoiceLinesOfPage);
            }
            return totalPrice;
        }
    }

    private Boolean checkIfInvoiceHasOrderNumbers(List<InvoiceLineDTO> invoiceLines) {
        for (InvoiceLineDTO invoiceLineDTO : invoiceLines) {
            if (!invoiceLineDTO.getOrderNumber().trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void removeUnnecessaryDates(List<InvoiceLineReportObject> invoiceLines) {
        String dateToCompare = "";
        for (InvoiceLineReportObject invoiceLine : invoiceLines) {
            if (invoiceLine.getInvoiceLineDate().equals(dateToCompare)) {
                invoiceLine.setInvoiceLineDate("");
            } else {
                dateToCompare = invoiceLine.getInvoiceLineDate();
            }
        }
    }

    private List<BtwReportObject> mapToBtwReportObject(List<BtwDTO> btw) {
        List<BtwReportObject> btwList = new ArrayList<>();

        if (btw.size() == 1 && btw.get(0).getBtwPercentage() == 0.0) {
            BtwReportObject btwReportObject = new BtwReportObject();
            btwReportObject.setBtwPercentage("verlegd");
            btwReportObject.setBtwAmount("");
            btwList.add(btwReportObject);
        } else {
            for (BtwDTO btwDTO : btw) {
                if (btwDTO.getBtwPercentage() != 0.0) {
                    BtwReportObject btwReportObject = new BtwReportObject();
                    btwReportObject.setBtwPercentage(Double.toString(btwDTO.getBtwPercentage()) + "%");
                    btwReportObject.setBtwAmount(NumberUtils.formatDouble(btwDTO.getBtwAmount(), 2) + " EUR");
                    btwList.add(btwReportObject);
                }
            }
        }
        return btwList;
    }

    private void sortBtwByPercentage(List<BtwDTO> btwList) {
        if (btwList.size() > 0) {
            Collections.sort(btwList, new Comparator<BtwDTO>() {
                public int compare(BtwDTO btwDTO1, BtwDTO btwDTO2) {
                    return (int) btwDTO1.getBtwPercentage() - (int) btwDTO2.getBtwPercentage();
                }
            });
        }
    }
}
