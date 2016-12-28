package be.boomkwekerij.plant.util;

public enum Month {

    JANUARY("Januari", 1), FEBRUARY("Februari", 2), MARCH("Maart", 3), APRIL("April", 4), MAY("Mei", 5), JUNE("Juni", 6),
    JULY("Juli", 7), AUGUST("Augustus", 8), SEPTEMBER("September", 9), OCTOBER("Oktober", 10), NOVEMBER("November", 11), DECEMBER("December", 12);

    private String translation;
    private int code;

    Month(String translation, int code) {
        this.translation = translation;
        this.code = code;
    }

    public String translation() {
        return translation;
    }

    public int code() {
        return code;
    }

    public static Month fromTranslation(String translation) {
        for (Month month : values()) {
            if (month.translation().equals(translation)) {
                return month;
            }
        }
        throw new IllegalArgumentException("Onbekende maand");
    }
}
