package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.exception.PrintException;
import be.boomkwekerij.plant.model.dto.BestandDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.Orientation;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;

import java.awt.print.*;
import java.io.IOException;

public class PrinterServiceImpl implements PrinterService {

    public void printDocument_Portrait(BestandDTO bestand) throws PrintException {
        try {
            PDDocument pdDocument = PDDocument.load(bestand.getFile());
            printPortrait(bestand.getName(), pdDocument);
        } catch (IOException | PrinterException e) {
            throw new PrintException(e.getMessage());
        }
    }

    public void printDocument_LandScape(BestandDTO bestand) throws PrintException {
        try {
            PDDocument pdDocument = PDDocument.load(bestand.getFile());
            printLandScape(bestand.getName(), pdDocument);
        } catch (IOException | PrinterException e) {
            throw new PrintException(e.getMessage());
        }
    }

    private void printPortrait(String name, PDDocument document) throws IOException, PrinterException {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName(name);
        job.setPageable(new PDFPageable(document));

        Paper paper = new Paper();
        paper.setSize(612, 828);
        paper.setImageableArea(0.0, 0.0, 612, 828);
        PageFormat pageFormat = new PageFormat();
        pageFormat.setOrientation(PageFormat.PORTRAIT);
        pageFormat.setPaper(paper);

        Book book = new Book();
        book.append(new PDFPrintable(document, Scaling.SHRINK_TO_FIT), pageFormat, document.getNumberOfPages());
        job.setPageable(book);

        job.print();

        document.close();
    }

    private void printLandScape(String name, PDDocument document) throws IOException, PrinterException {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName(name);
        job.setPageable(new PDFPageable(document));

        Paper paper = new Paper();
        paper.setSize(612, 828);
        paper.setImageableArea(0.0, 0.0, 612, 828);
        PageFormat pageFormat = new PageFormat();
        pageFormat.setOrientation(PageFormat.LANDSCAPE);
        pageFormat.setPaper(paper);

        Book book = new Book();
        book.append(new PDFPrintable(document, Scaling.SHRINK_TO_FIT), pageFormat, document.getNumberOfPages());
        job.setPageable(book);

        job.print();

        document.close();
    }
}