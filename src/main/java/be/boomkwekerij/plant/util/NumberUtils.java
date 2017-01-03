package be.boomkwekerij.plant.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberUtils {

    public static String roundDouble(Double value, int amountOfDecimals) {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(amountOfDecimals, RoundingMode.HALF_UP);
        return formatter(amountOfDecimals).format(bigDecimal.doubleValue());
    }

    private static NumberFormat formatter(int amountOfDecimals) {
        NumberFormat numberFormat = new DecimalFormat();
        numberFormat.setMaximumFractionDigits(amountOfDecimals);
        numberFormat.setMinimumFractionDigits(amountOfDecimals);
        return numberFormat;
    }
}
