package com.example.demo.service;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Currency;
import java.util.Locale;

public interface TaxCalculator {

    public String getMonthlyTax();
    public String getYearlyTax();
    public String getYearlyNetIncome();
    public Locale getLocale();
    public Currency getCurrency();
}
