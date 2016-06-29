package be.boomkwekerij.plant.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberUtils {

    public static String formatDouble(Double value, int amountOfDecimals) {
        return formatter(amountOfDecimals).format(value);
    }

    private static NumberFormat formatter(int amountOfDecimals) {
        NumberFormat numberFormat = new DecimalFormat();
        numberFormat.setMaximumFractionDigits(amountOfDecimals);
        numberFormat.setMinimumFractionDigits(amountOfDecimals);
        return numberFormat;
    }
}
