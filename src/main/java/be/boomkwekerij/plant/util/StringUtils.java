package be.boomkwekerij.plant.util;

public class StringUtils {

    public static int countStringSimilarity(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) {
            longer = s2; shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) { return 100; }
        double similarity = (longerLength - org.apache.commons.lang.StringUtils.getLevenshteinDistance(longer, shorter)) / (double) longerLength;
        return new Double(similarity * 100).intValue();
    }
}
