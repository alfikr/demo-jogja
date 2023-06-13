package com.example.demo.service.impl;

import com.example.demo.models.IndonesiaTaxEnum;
import com.example.demo.service.TaxCalculator;
import com.example.demo.utils.CurrencyUtil;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Currency;
import java.util.Locale;

public class IndonesiaTaxCalculator implements TaxCalculator {

    Double netto;
    Double tax;
    final Double income;
    final Double outcome;
    final JsonNode employee;
    public IndonesiaTaxCalculator(Double income, Double outcome, JsonNode employee){
        this.income=income;
        this.outcome=outcome;
        this.employee=employee;
        calculateNetIncome();
        calculateTax();
    }
    public void calculateNetIncome() {
        if (employee.get("marital status").asText().equalsIgnoreCase("maried") && employee.get("childs").asInt() > 0) {
            netto = (income * 12) - IndonesiaTaxEnum.MARRIED_HAVE_CHILD.getValue();
        } else if (employee.get("marital status").asText().equalsIgnoreCase("maried") && employee.get("childs").asInt() == 0) {
            netto = (income * 12) - IndonesiaTaxEnum.MARRIED.getValue();
        } else {
            netto = (income * 12) - IndonesiaTaxEnum.SINGLE.getValue();
        }
    }

    public void calculateTax() {
        if (netto < 50000000) {
            tax = (netto * (5D / 100D));
        } else if (netto >= 50000000D && netto < 250000000D) {
            tax = ((netto - 50000000) * (10D / 100D));
        } else {
            tax = ((50000000 * (5D / 100D)) + (netto - 50000000) * (10D / 100D));
        }
    }

    @Override
    public String getMonthlyTax() {
        return CurrencyUtil.formatCurrency((Math.ceil((tax.longValue()/12)/1000D)*1000),getCurrency(),getLocale());
    }

    @Override
    public String getYearlyTax() {
        return CurrencyUtil.formatCurrency(tax, getCurrency(), getLocale());
    }

    @Override
    public String getYearlyNetIncome() {
        return CurrencyUtil.formatCurrency(netto, getCurrency(), getLocale());
    }

    @Override
    public Locale getLocale() {
        return new Locale("id", "ID");
    }

    @Override
    public Currency getCurrency() {
        return Currency.getInstance("IDR");
    }

}
