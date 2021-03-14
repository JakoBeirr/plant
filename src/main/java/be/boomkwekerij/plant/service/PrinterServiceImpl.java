package be.boomkwekerij.plant.service;

import be.boomkwekerij.plant.exception.PrintException;
import be.boomkwekerij.plant.model.dto.BestandDTO;
import be.boomkwekerij.plant.util.InitializerSingleton;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.Orientation;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;

import java.awt.print.*;
import java.io.File;
import java.io.IOException;

public class PrinterServiceImpl implements PrinterService {

    public void printDocumentInPortraitMode(BestandDTO bestand) throws PrintException {
        try {
            writeFileToFileSystem(bestand);

            PDDocument pdDocument = PDDocument.load(bestand.getFile());
            printPortrait(bestand.getName(), pdDocument);
        } catch (IOException | PrinterException e) {
            throw new PrintException(e.getMessage());
        }
    }

    public void printDocumentInLandScapeMode(BestandDTO bestand) throws PrintException {
        try {
            writeFileToFileSystem(bestand);

            PDDocument pdDocument = PDDocument.load(bestand.getFile());
            printLandScape(bestand.getName(), pdDocument);
        } catch (IOException | PrinterException e) {
            throw new PrintException(e.getMessage());
        }
    }

    private void writeFileToFileSystem(BestandDTO report) throws IOException {
        String reportName = report.getName().replaceAll("[\\\\/:*?\"<>|]", "_");
        File file = new File(InitializerSingleton.getInitializer().getDataDirectory() + "/files/" + reportName);
        if (file.exists()) {
            file.delete();
        }
        FileUtils.writeByteArrayToFile(file, report.getFile());
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