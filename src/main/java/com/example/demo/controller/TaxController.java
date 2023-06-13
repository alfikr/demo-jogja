package com.example.demo.controller;

import com.example.demo.service.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.demo.models.SimpleResponse;
import com.fasterxml.jackson.databind.JsonNode;


@RestController
public class TaxController {

    @Autowired
    private TaxService taxService;
    
    @PostMapping("/hitungpajak")
    public SimpleResponse hitungPajak(@RequestBody JsonNode param){
        return taxService.hitungPajak(param);
    }
}
