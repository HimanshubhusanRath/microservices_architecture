package com.hr.springboot.examples.demo.customannotation.controller;


import com.hr.springboot.examples.demo.customannotation.service.AService;
import com.hr.springboot.examples.demo.customannotation.service.BService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/custom-annotation")
public class CustomAnnotationController {
    @Autowired
    private AService aService;
    @Autowired
    private BService bService;

    @GetMapping("/{id}")
    public String getData(@PathVariable("id") final String id) {
        if (id.equals("a")) {
            return aService.getData();
        } else {
            return bService.getData();
        }
    }

}
