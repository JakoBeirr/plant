package be.boomkwekerij.plant.util;

public enum CountryCode {

    ALBANIA("AL", "Albanië"), ANDORRA("AD", "Andorra"), AUSTRIA("AT", "Austria"), BELARUS("BY", "Wit-Rusland"), BELGIUM("BE", "België"),
    BOSNIA("BA", "Bosnië"), BULGARIA("BG", "Bulgarije"), CROATIA("HR", "Kroatië"), CYPRUS("CY", "Cyprus"), CZECH("CZ", "Tsjechië"),
    DENMARK("DK", "Denemarken"), ESTONIA("EE", "Estland"), FAROE_ISLANDS("FO", "Faroër Eilanden"), FINLAND("FI", "Finland"),
    FRANCE("FR", "Frankrijk"), GERMANY("DE", "Duitsland"), GIBRALTAR("GI", "Gibraltar"), GREECE("GR", "Griekenland"), HUNGARY("HU", "Hongarije"),
    ICELAND("IS", "IJsland"), IRELAND("IE", "Ierland"), ITALY("IT", "Italië"), LATVIA("LV", "Letland"), LIECHTENSTEIN("LI", "Liechtenstein"),
    LITHUANIA("LT", "Litouwen"), LUXEMBOURG("LU", "Luxemburg"), MACEDONIA("MK", "Macedonië"), MALTA("MT", "Malta"), MOLDOVA("MD", "Moldavië"),
    MONACO("MC", "Monaco"), NETHERLANDS("NL", "Nederland"), NORWAY("NO", "Noorwegen"), POLAND("PL", "Polen"), PORTUGAL("PT", "Portugal"),
    ROMANIA("RO", "Roemenië"), RUSSIA("RU", "Rusland"), SAN_MARINO("SM", "San Marino"), SERBIA("RS", "Servië"), SLOVAKIA("SK", "Slovakije"),
    SLOVENIA("SI", "Slovenië"), SPAIN("ES", "Spanje"), SWEDEN("SE", "Zweden"), SWITZERLAND("CH", "Zwitserland"), UKRAINE("UA", "Oekraïne"),
    UNITED_KINGDOM("GB", "Verenigd Koninkrijk"), VATICAN_CITY("VA", "Vaticaanstad"), MONTENEGRO("ME", "Montenegro");

    private String code;
    private String fullname;

    CountryCode(String code, String fullname) {
        this.code = code;
        this.fullname = fullname;
    }

    public static CountryCode fromCountryCode(String countryCode) {
        CountryCode[] countryCodes = CountryCode.values();
        for (CountryCode code : countryCodes) {
            if (code.code.equals(countryCode)) {
                return code;
            }
        }
        return null;
    }

    public String code() {
        return code;
    }

    public String fullname() {
        return fullname;
    }
}
