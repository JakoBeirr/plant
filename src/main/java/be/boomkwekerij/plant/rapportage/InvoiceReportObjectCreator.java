package be.boomkwekerij.plant.rapportage;

import be.boomkwekerij.plant.model.dto.BtwDTO;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.model.dto.InvoiceLineDTO;
import be.boomkwekerij.plant.model.report.BtwReportObject;
import be.boomkwekerij.plant.model.report.InvoiceLineReportObject;
import be.boomkwekerij.plant.model.report.MultiplePagedInvoiceReportObject;
import be.boomkwekerij.plant.model.report.OnePagedInvoiceReportObject;
import be.boomkwekerij.plant.util.DateFormatPattern;
import be.boomkwekerij.plant.util.DateUtils;
import be.boomkwekerij.plant.util.Initializer;
import be.boomkwekerij.plant.util.NumberUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InvoiceReportObjectCreator {

    private InvoiceLineReportObjectCreator invoiceLineReportObjectCreator = new InvoiceLineReportObjectCreator();

    public OnePagedInvoiceReportObject createOnePageReportObject(InvoiceDTO invoiceDTO) {
        OnePagedInvoiceReportObject onePagedInvoiceReportObject = new OnePagedInvoiceReportObject();
        onePagedInvoiceReportObject.setInvoiceDate(DateUtils.formatDate(invoiceDTO.getDate(), DateFormatPattern.DATE_FORMAT));
        onePagedInvoiceReportObject.setInvoiceNumber(invoiceDTO.getInvoiceNumber());
        List<InvoiceLineDTO> invoiceLines = invoiceDTO.getInvoiceLines();
        sortInvoiceLinesByDate(invoiceLines);
        for (InvoiceLineDTO invoiceLineDTO : invoiceLines) {
            onePagedInvoiceReportObject.getInvoiceLines().add(invoiceLineReportObjectCreator.create(invoiceLineDTO));

            if (StringUtils.isNotBlank(invoiceLineDTO.getRemark())) {
                onePagedInvoiceReportObject.getInvoiceLines().add(invoiceLineReportObjectCreator.createRemarkRow(invoiceLineDTO));
            }
        }
        removeUnnecessaryDates(onePagedInvoiceReportObject.getInvoiceLines());
        onePagedInvoiceReportObject.setHasOrderNumbers(checkIfInvoiceHasOrderNumbers(invoiceLines));
        onePagedInvoiceReportObject.setSubTotal(NumberUtils.roundDouble(invoiceDTO.getSubTotal(), 2) + " EUR");
        List<BtwDTO> btwList = invoiceDTO.getBtw();
        sortBtwByPercentage(btwList);
        onePagedInvoiceReportObject.setBtw(mapToBtwReportObject(btwList));
        onePagedInvoiceReportObject.setTotalPrice(NumberUtils.roundDouble(invoiceDTO.getTotalPrice(), 2) + " EUR");
        return onePagedInvoiceReportObject;
    }

    public List<MultiplePagedInvoiceReportObject> createMultiplePagedReportObject(InvoiceDTO invoiceDTO, int amountOfPages) {
        List<MultiplePagedInvoiceReportObject> multiplePagedInvoiceReportObjects = new ArrayList<>();
        for (int i = 1; i <= amountOfPages; i++) {
            MultiplePagedInvoiceReportObject multiplePagedInvoiceReportObject = new MultiplePagedInvoiceReportObject();
            multiplePagedInvoiceReportObject.setInvoiceDate(DateUtils.formatDate(invoiceDTO.getDate(), DateFormatPattern.DATE_FORMAT));
            multiplePagedInvoiceReportObject.setInvoiceNumber(invoiceDTO.getInvoiceNumber());
            List<InvoiceLineDTO> invoiceLines = getInvoiceLinesToMap(invoiceDTO, i);
            sortInvoiceLinesByDate(invoiceLines);
            for (InvoiceLineDTO invoiceLineDTO : invoiceLines) {
                multiplePagedInvoiceReportObject.getInvoiceLines().add(invoiceLineReportObjectCreator.create(invoiceLineDTO));

                if (StringUtils.isNotBlank(invoiceLineDTO.getRemark())) {
                    multiplePagedInvoiceReportObject.getInvoiceLines().add(invoiceLineReportObjectCreator.createRemarkRow(invoiceLineDTO));
                }
            }
            multiplePagedInvoiceReportObject.setTransportPreviousPage(NumberUtils.roundDouble(getTotalPricePage(invoiceDTO, i-1), 2) + " EUR");
            multiplePagedInvoiceReportObject.setTransportCurrentPage(NumberUtils.roundDouble(getTotalPricePage(invoiceDTO, i), 2) + " EUR");
            removeUnnecessaryDates(multiplePagedInvoiceReportObject.getInvoiceLines());
            multiplePagedInvoiceReportObject.setHasOrderNumbers(checkIfInvoiceHasOrderNumbers(invoiceDTO.getInvoiceLines()));
            multiplePagedInvoiceReportObject.setSubTotal(NumberUtils.roundDouble(invoiceDTO.getSubTotal(), 2) + " EUR");
            List<BtwDTO> btwList = invoiceDTO.getBtw();
            sortBtwByPercentage(btwList);
            multiplePagedInvoiceReportObject.setBtw(mapToBtwReportObject(btwList));
            multiplePagedInvoiceReportObject.setTotalPrice(NumberUtils.roundDouble(invoiceDTO.getTotalPrice(), 2) + " EUR");
            multiplePagedInvoiceReportObjects.add(multiplePagedInvoiceReportObject);
        }
        return multiplePagedInvoiceReportObjects;
    }

    private void sortInvoiceLinesByDate(List<InvoiceLineDTO> invoiceLines) {
        if (invoiceLines.size() > 0) {
            Collections.sort(invoiceLines, (invoiceLineDTO1, invoiceLineDTO2) -> invoiceLineDTO1.getDate().compareTo(invoiceLineDTO2.getDate()));
        }
    }

    private List<InvoiceLineDTO> getInvoiceLinesToMap(InvoiceDTO invoiceDTO, int pageCount) {
        List<InvoiceLineDTO> subList = new ArrayList<>();

        int page = 0;
        int invoiceLineCount = 0;
        for (InvoiceLineDTO invoiceLineDTO : invoiceDTO.getInvoiceLines()) {
            int invoiceLineSize = StringUtils.isBlank(invoiceLineDTO.getRemark()) ? 1 : 2;

            if ((invoiceLineCount + invoiceLineSize) <= Initializer.MAX_INVOICELINES) {
                subList.add(invoiceLineDTO);
                invoiceLineCount += invoiceLineSize;
            } else {
                page++;
                if (page < pageCount) {
                    subList.clear();

                    subList.add(invoiceLineDTO);
                    invoiceLineCount = invoiceLineSize;
                } else {
                    break;
                }
            }
        }

        return subList;
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

    private double countSubTotal(List<InvoiceLineDTO> invoiceLines) {
        double subTotal = 0;

        for (InvoiceLineDTO invoiceLine : invoiceLines) {
            subTotal += invoiceLine.getTotalPrice();
        }

        return subTotal;
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
                    btwReportObject.setBtwAmount(NumberUtils.roundDouble(btwDTO.getBtwAmount(), 2) + " EUR");
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
