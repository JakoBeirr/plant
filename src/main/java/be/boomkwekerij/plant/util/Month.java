package be.boomkwekerij.plant.util;

public enum Month {

    JANUARY("Januari"), FEBRUARY("Februari"), MARCH("Maart"), APRIL("April"), MAY("Mei"), JUNE("Juni"),
    JULY("Juli"), AUGUST("Augustus"), SEPTEMBER("September"), OCTOBER("Oktober"), NOVEMBER("November"), DECEMBER("December");

    private String translation;

    Month(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }

    public Month getMonthFromTranslation(String translation) {
        for (Month month : values()) {
            if (month.getTranslation().equals(translation)) {
                return month;
            }
        }
        return null;
    }
}
