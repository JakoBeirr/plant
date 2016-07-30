package be.boomkwekerij.plant.exception;

public class ReportException extends Exception {

    public ReportException(String message) {
        super(message);
    }

    public ReportException(Throwable cause) {
        super(cause);
    }
}
