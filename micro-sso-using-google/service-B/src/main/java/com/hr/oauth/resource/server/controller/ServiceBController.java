package com.hr.oauth.resource.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/serv-b")
public class ServiceBController {

    @GetMapping
    public String serviceResponse() {
        return "Response from service-B";
    }


}
