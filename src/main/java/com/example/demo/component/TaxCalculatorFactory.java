package com.example.demo.component;

import com.example.demo.service.TaxCalculator;
import com.example.demo.service.impl.IndonesiaTaxCalculator;
import com.example.demo.service.impl.VietnamTaxCalculator;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

@Component
public class TaxCalculatorFactory {
    public TaxCalculator createTaxCalculator(String country, Double income, Double outcome, JsonNode employee){
        if (country.equalsIgnoreCase("indonesia")){
            return new IndonesiaTaxCalculator(income,outcome,employee);
        }else if (country.equalsIgnoreCase("vietnam")){
            return new VietnamTaxCalculator(income,outcome,employee);
        }
        throw new IllegalArgumentException("Unsupported country: "+ country);
    }
}
