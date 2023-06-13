package com.example.demo.service.impl;

import com.example.demo.models.VietnamTaxEnum;
import com.example.demo.service.TaxCalculator;
import com.example.demo.utils.CurrencyUtil;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Currency;
import java.util.Locale;

public class VietnamTaxCalculator implements TaxCalculator {
    Double netto;
    Double tax;
    final Double income;
    final Double outcome;
    final JsonNode employee;
    public VietnamTaxCalculator(Double income,Double outcome,JsonNode employee){
        this.income=income;
        this.outcome=outcome;
        this.employee=employee;
        calculateNetIncome();
        calculateTax();
    }
    private void calculateNetIncome() {
        netto= ((income - outcome) * 12) - (employee.get("marital status").asText().equalsIgnoreCase("maried")
                ? VietnamTaxEnum.MARRIED.getValue() : VietnamTaxEnum.SINGLE.getValue());
    }

    private void calculateTax() {
        if (netto < 50000000D) {
            tax = (netto - (netto * (2.5D / 100D)));
        } else {
            tax = ((50000000 * (2.5D / 100D)) + ((netto - 50000000) * 7.5D / 100D));
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
        return new Locale("vi", "VN");
    }

    @Override
    public Currency getCurrency() {
        return Currency.getInstance("VND");
    }
}
