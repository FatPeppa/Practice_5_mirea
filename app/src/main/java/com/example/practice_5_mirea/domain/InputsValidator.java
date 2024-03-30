package com.example.practice_5_mirea.domain;

public class InputsValidator {
    public static boolean checkGoodName(String goodName) {
        return goodName.length() > 0 && !isNumeric(goodName);
    }

    public static boolean checkGoodAmount(String goodAmount) {
        return goodAmount.length() > 0 && isNumeric(goodAmount);
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
