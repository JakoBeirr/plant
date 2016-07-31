package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.exception.PrintException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;

import java.awt.print.*;
import java.io.IOException;

public class PrinterServiceImpl implements PrinterService {

    public void printDocument(String name, byte[] invoiceDocument) throws PrintException {
        try {
            PDDocument document = PDDocument.load(invoiceDocument);
            print(name, document);
        } catch (IOException | PrinterException e) {
            throw new PrintException(e.getMessage());
        }
    }

    private void print(String name, PDDocument document) throws IOException, PrinterException {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName(name + ".pdf");
        job.setPageable(new PDFPageable(document));

        Paper paper = new Paper();
        paper.setSize(612, 828);
        paper.setImageableArea(0.0, 0.0, 612, 828);
        PageFormat pageFormat = new PageFormat();
        pageFormat.setPaper(paper);

        Book book = new Book();
        book.append(new PDFPrintable(document, Scaling.SHRINK_TO_FIT), pageFormat, document.getNumberOfPages());
        job.setPageable(book);

        job.print();

        document.close();
    }
}