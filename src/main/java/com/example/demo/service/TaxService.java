package com.example.demo.service;

import com.example.demo.component.TaxCalculatorFactory;
import com.example.demo.models.IndonesiaTaxEnum;
import com.example.demo.models.SimpleResponse;
import com.example.demo.models.VietnamTaxEnum;
import com.example.demo.utils.CurrencyUtil;
import com.example.demo.utils.GenericConstants;
import com.example.demo.utils.JsonValidator;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.*;

@Service
@Slf4j
public class TaxService {

    private final TaxCalculatorFactory taxCalculatorFactory;

    public TaxService(TaxCalculatorFactory taxCalculatorFactory) {
        this.taxCalculatorFactory = taxCalculatorFactory;
    }

    public SimpleResponse hitungPajak(JsonNode param) {
        JsonNode employee = param.get("employee");
        Set<ConstraintViolation> violationSet = JsonValidator.validateParam(employee, "marital status", "country");
        if (!violationSet.isEmpty()) {
            return new SimpleResponse(GenericConstants.FAILED, GenericConstants.FAILED_CODE, violationSet.iterator().next().getMessage());
        }
        JsonNode komponengaji = param.get("komponengaji");
        Double income = 0D;
        Double outcome = 0D;
        for (JsonNode n : komponengaji) {
            if (n.get("type").asText().equalsIgnoreCase("earning")) {
                income += n.get("amount").asDouble();
            } else if (n.get("type").asText().equalsIgnoreCase("deduction")) {
                outcome += n.get("amount").asDouble();
            }
        }
        Map<String, Object> result = new HashMap<>();
        String country = employee.get("country").asText();
        TaxCalculator taxCalculator = taxCalculatorFactory.createTaxCalculator(country,income,outcome,employee);
        result.put("monthlyTax", taxCalculator.getMonthlyTax());
        result.put("yearlyIncomeNetto", taxCalculator.getYearlyNetIncome());
        result.put("yearlyTax", taxCalculator.getYearlyTax());

        return new SimpleResponse(GenericConstants.SUCCESS, GenericConstants.SUCCESS_CODE, result);
    }

}