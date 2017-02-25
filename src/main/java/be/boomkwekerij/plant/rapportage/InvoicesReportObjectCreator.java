package be.boomkwekerij.plant.rapportage;

import be.boomkwekerij.plant.model.dto.CompanyDTO;
import be.boomkwekerij.plant.model.dto.InvoiceDTO;
import be.boomkwekerij.plant.model.report.InvoicesInvoiceReportObject;
import be.boomkwekerij.plant.model.report.InvoicesReportObject;
import be.boomkwekerij.plant.util.DateFormatPattern;
import be.boomkwekerij.plant.util.DateUtils;
import be.boomkwekerij.plant.util.NumberUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InvoicesReportObjectCreator {

    public InvoicesReportObject create(List<InvoiceDTO> invoices, CompanyDTO companyDTO, DateTime reportDate, String period, String reportTitle) {
        InvoicesReportObject invoicesReportObject = new InvoicesReportObject();
        invoicesReportObject.setCompanyName(companyDTO.getName1() + " " + companyDTO.getName2());
        invoicesReportObject.setReportDate(DateUtils.formatDate(reportDate, DateFormatPattern.DATE_FORMAT));
        invoicesReportObject.setPeriod(period);
        invoicesReportObject.setReportTitle(reportTitle);
        sortInvoicesByDate(invoices);
        List<InvoicesInvoiceReportObject> invoicesInvoiceReportObjects = new ArrayList<>();
        for (InvoiceDTO invoice : invoices) {
            InvoicesInvoiceReportObject invoiceReportObject = new InvoicesInvoiceReportObject();
            invoiceReportObject.setInvoiceNumber(invoice.getInvoiceNumber());
            invoiceReportObject.setCustomer(invoice.getCustomer().getName1());
            invoiceReportObject.setInvoiceDate(DateUtils.formatDate(invoice.getDate(), DateFormatPattern.DATE_FORMAT));
            invoiceReportObject.setTotalAmountExclusive(NumberUtils.roundDouble(invoice.getSubTotal(), 2) + " EUR");
            invoiceReportObject.setTotalAmountInclusive(NumberUtils.roundDouble(invoice.getTotalPrice(), 2) + " EUR");
            if (invoice.isPayed() && invoice.getPayDate() != null) {
                invoiceReportObject.setPayDate(DateUtils.formatDate(invoice.getPayDate(), DateFormatPattern.DATE_FORMAT));
            } else {
                invoiceReportObject.setPayDate(" - ");
            }
            invoicesInvoiceReportObjects.add(invoiceReportObject);
        }
        invoicesReportObject.setInvoices(invoicesInvoiceReportObjects);
        invoicesReportObject.setTotalExclusive(NumberUtils.roundDouble(countTotalExclusive(invoices), 2) + " EUR");
        invoicesReportObject.setTotalInclusive(NumberUtils.roundDouble(countTotalInclusive(invoices), 2) + " EUR");
        return invoicesReportObject;
    }

    private void sortInvoicesByDate(List<InvoiceDTO> invoices) {
        Collections.sort(invoices, new Comparator<InvoiceDTO>() {
            public int compare(InvoiceDTO invoice1, InvoiceDTO invoice2) {
                return Integer.parseInt(invoice1.getInvoiceNumber()) - Integer.parseInt(invoice2.getInvoiceNumber());
            }
        });
    }

    private Double countTotalExclusive(List<InvoiceDTO> invoices) {
        Double totalExclusive = 0.0;
        for (InvoiceDTO invoice : invoices) {
            totalExclusive += invoice.getSubTotal();
        }
        return totalExclusive;
    }

    private Double countTotalInclusive(List<InvoiceDTO> invoices) {
        Double totalInclusive = 0.0;
        for (InvoiceDTO invoice : invoices) {
            totalInclusive += invoice.getTotalPrice();
        }
        return totalInclusive;
    }
}
