package com.example.practice_5_mirea.domain;

public class InputTextExecutor {

    public static String getProductName(String text) {
        StringBuilder nameStringBuilder = new StringBuilder(text.substring(text.indexOf("name: ") + 6, text.indexOf("amount: ") - 1));

        while (nameStringBuilder.indexOf(";") != -1) nameStringBuilder.deleteCharAt(nameStringBuilder.indexOf(";"));
        while (nameStringBuilder.indexOf(" ") != -1) nameStringBuilder.deleteCharAt(nameStringBuilder.indexOf(" "));

        return nameStringBuilder.toString();
    }

    public static String getProductAmount(String text) {
        StringBuilder amountStringBuilder = new StringBuilder(text.substring(text.indexOf("amount: ") + 8));

        while (amountStringBuilder.indexOf(";") != -1) amountStringBuilder.deleteCharAt(amountStringBuilder.indexOf(";"));
        while (amountStringBuilder.indexOf(" ") != -1) amountStringBuilder.deleteCharAt(amountStringBuilder.indexOf(" "));

        return amountStringBuilder.toString();
    }
}
