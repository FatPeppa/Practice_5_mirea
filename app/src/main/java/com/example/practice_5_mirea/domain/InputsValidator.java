package com.example.practice_5_mirea.domain;

public class InputsValidator {
    public static boolean checkGoodName(String goodName) {
        return goodName.length() > 0 && !isNumeric(goodName);
    }

    public static boolean checkGoodAmount(String goodAmount) {
        return goodAmount.length() > 0 && isNumeric(goodAmount);
    }

    public static boolean checkSharedProductInfo(String sharedText) {

        if (!sharedText.contains("name: ")
                || !sharedText.contains("amount: ")
                || sharedText.indexOf("name: ") > sharedText.indexOf("amount: ") + 7
        )
            return false;

        try {
            StringBuilder nameStringBuilder = new StringBuilder(sharedText.substring(sharedText.indexOf("name: ") + 6, sharedText.indexOf("amount: ") - 1));
            StringBuilder amountStringBuilder = new StringBuilder(sharedText.substring(sharedText.indexOf("amount: ") + 8));

            while (nameStringBuilder.indexOf(";") != -1) nameStringBuilder.deleteCharAt(nameStringBuilder.indexOf(";"));
            while (nameStringBuilder.indexOf(" ") != -1) nameStringBuilder.deleteCharAt(nameStringBuilder.indexOf(" "));
            while (amountStringBuilder.indexOf(";") != -1) amountStringBuilder.deleteCharAt(amountStringBuilder.indexOf(";"));
            while (amountStringBuilder.indexOf(" ") != -1) amountStringBuilder.deleteCharAt(amountStringBuilder.indexOf(" "));

            return checkGoodName(nameStringBuilder.toString()) && checkGoodAmount(amountStringBuilder.toString());
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
