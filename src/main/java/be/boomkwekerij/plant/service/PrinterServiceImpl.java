package be.boomkwekerij.plant.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import java.io.IOException;

public class PrinterServiceImpl implements PrinterService {

    public void printDocument(byte[] invoiceDocument) throws IOException, PrinterException {
        PDDocument document = PDDocument.load(invoiceDocument);
        print(document);
    }

    private void print(PDDocument document) throws IOException, PrinterException
    {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(document));
        job.print();
    }
}