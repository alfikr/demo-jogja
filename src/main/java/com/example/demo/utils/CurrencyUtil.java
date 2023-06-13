package com.example.demo.utils;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class CurrencyUtil {
    public static String formatCurrency(double amount, Currency currency, Locale locale) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        currencyFormat.setCurrency(currency);
        return currencyFormat.format(amount);
    }
}
