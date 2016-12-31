package be.boomkwekerij.plant.model.report;

public class InvoicesInvoiceReportObject {

    private String invoiceNumber;
    private String customer;
    private String invoiceDate;
    private String totalAmountExclusive;
    private String totalAmountInclusive;
    private String payDate;

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getTotalAmountExclusive() {
        return totalAmountExclusive;
    }

    public void setTotalAmountExclusive(String totalAmountExclusive) {
        this.totalAmountExclusive = totalAmountExclusive;
    }

    public String getTotalAmountInclusive() {
        return totalAmountInclusive;
    }

    public void setTotalAmountInclusive(String totalAmountInclusive) {
        this.totalAmountInclusive = totalAmountInclusive;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }
}
