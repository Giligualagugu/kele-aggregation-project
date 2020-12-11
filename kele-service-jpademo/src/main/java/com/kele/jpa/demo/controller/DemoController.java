package com.kele.jpa.demo.controller;


import com.kele.jpa.demo.repository.GsCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    GsCompanyRepository gsCompanyRepository;


    @GetMapping("/gs/info")
    public Object getGs(@RequestParam("id") Integer id) {
        return gsCompanyRepository.findById(id);
    }
}
