package it.gen.formats;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColumnExtractor {


    public static final Pattern pattern = Pattern.compile("([a-z]*)([A-Z].*?)");
    public static final Pattern patterng2 = Pattern.compile("(^[A-Z][a-z0-9]*.*?)");
    public static final String UNDERSCORE_SEP = "_";


    public static String extractMatches(String value) {
        Matcher matcher = pattern.matcher(value);
        String startColName="";
        try {
            if (matcher.matches()) {
                if (!matcher.group(1).trim().isEmpty()) {
                    startColName = matcher.group(1).toUpperCase();
                }
            }
        } catch (IllegalStateException e){
        }
        try {
            startColName += extrapolation(matcher.group(2));
        } catch (IllegalStateException e) {
           throw e;
        }
        return startColName;
    }

    private static String extrapolation(String strSubstring){
        Matcher matcher2 = patterng2.matcher(strSubstring);
        String result="";
        if (matcher2.find()){
            result =  matcher2.group(1);
            int endMatch = matcher2.end(1);
            if (endMatch < strSubstring.length()-1) {
                result += extrapolation(strSubstring.substring(endMatch));
            }
        }
        return UNDERSCORE_SEP + result.toUpperCase();
    }


    public static final Pattern numberPattern = Pattern.compile("(?:NUMBER\\()(<=|>=)?(\\d)+(?:,)?(<=|>=)?(\\d)?(?:\\))");
//
//    public static final Map extractAndGenerateTransitiveNumberParts(String input, String output, Map wheretoputMap){
//        Matcher matcher = numberPattern.matcher(input);
//        int min = 1;
//        int max = 22;
//        int mindec=1;
//        int maxdec=9;
//        if (matcher.matches()){
//            Boolean thenint=null;
//            Boolean thendec=null;
//            thenint = getGtThenSign(matcher.group(1));
//            Integer intpart = Integer.valueOf(matcher.group(2));
//            thendec =  getGtThenSign(matcher.group(3));
//            Integer decpart = Integer.valueOf(matcher.group(4));
//            if (thenint!=null && thendec!= null){
//                IntStream rangeInt;
//                IntStream rangeDec;
//                if (thenint!=null) {
//                    rangeInt = thenint? IntStream.range(intpart, max): IntStream.range(1, intpart);
//                }
//                if (thendec!=null) {
//                    rangeDec = thendec ? IntStream.range(decpart, maxdec): IntStream.range(1, decpart);
//                }
//            }
//            else {
//                wheretoputMap.put(input, output);
//
//            }
//        }
//        return wheretoputMap;
//    }
//
//    private static Boolean getGtThenSign(String then) {
//        Boolean thenint = null;
//        if (then != null){
//             thenint = then.equals(">") ? true : false;
//        }
//        return thenint;
//    }



}
