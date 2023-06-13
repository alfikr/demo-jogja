package com.example.demo.service;

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

    public SimpleResponse hitungPajak(JsonNode param){
        JsonNode employee = param.get("employee");
        Set<ConstraintViolation> violationSet=JsonValidator.validateParam(employee,"marital status","country");
        if (!violationSet.isEmpty()){
            return new SimpleResponse(GenericConstants.FAILED,GenericConstants.FAILED_CODE,violationSet.iterator().next().getMessage());
        }
        JsonNode komponengaji = param.get("komponengaji");
        Double income =0D;
        Double outcome=0D;
        for(JsonNode n :komponengaji){
            if (n.get("type").asText().equalsIgnoreCase("earning")){
                income+=n.get("amount").asDouble();
            }else if (n.get("type").asText().equalsIgnoreCase("deduction")){
                outcome+=n.get("amount").asDouble();
            }
        }
        Map<String,Object> result = new HashMap<>();
        Double netto=0D;
        Double tax=0D;
        Locale locale = employee.get("country").asText().equalsIgnoreCase("indonesia")?new Locale("id","ID"):new Locale("vi", "VN");
        Currency currency = employee.get("country").asText().equalsIgnoreCase("indonesia")?Currency.getInstance("IDR"):Currency.getInstance("VND");
        if (employee.get("country").asText().equalsIgnoreCase("indonesia")){
            if (employee.get("marital status").asText().equalsIgnoreCase("maried") && employee.get("childs").asInt()>0){
                netto= (income*12)-IndonesiaTaxEnum.MARRIED_HAVE_CHILD.getValue();
            }else if (employee.get("marital status").asText().equalsIgnoreCase("maried") && employee.get("childs").asInt()==0){
                netto= (income*12)-IndonesiaTaxEnum.MARRIED.getValue();
            }else {
                netto= (income*12)-IndonesiaTaxEnum.SINGLE.getValue();
            }
            if (netto<50000000){
                tax=  (netto*(5D/100D));
            }
            if (netto>=50000000D && netto<250000000D){
                tax=  ((netto-50000000)*(10D/100D));

            }
            if (netto>=250000000D) {
                tax= ((50000000*(5D/100D))+(netto-50000000)*(10D/100D));
            }
            result.put("monthlyTax",CurrencyUtil.formatCurrency((Math.ceil((tax.longValue()/12)/1000D)*1000),currency,locale));
        }else {
            netto= ((income-outcome)*12)-(employee.get("marital status").asText().equalsIgnoreCase("maried")
                    ?VietnamTaxEnum.MARRIED.getValue():VietnamTaxEnum.SINGLE.getValue());
            if (netto<50000000D){
                tax=  (netto-(netto*(2.5D/100D)));
            }else {
                tax= ((50000000*(2.5D/100D))+((netto-50000000)*7.5D/100D));
            }
            result.put("monthlyTax",CurrencyUtil.formatCurrency((Math.ceil((tax.longValue()/12)/1000D)*1000),currency,locale));
        }
        result.put("yearlyIncomeNetto",CurrencyUtil.formatCurrency(netto,currency,locale));
        result.put("yearlyTax",CurrencyUtil.formatCurrency(tax,currency,locale));


        return new SimpleResponse(GenericConstants.SUCCESS,GenericConstants.SUCCESS_CODE,result);
    }
}