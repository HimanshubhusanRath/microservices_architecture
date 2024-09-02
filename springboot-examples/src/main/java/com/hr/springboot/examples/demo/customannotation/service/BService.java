package com.hr.springboot.examples.demo.customannotation.service;

import com.hr.springboot.examples.demo.customannotation.context.TenantContext;
import com.hr.springboot.examples.demo.customannotation.custom.UseSlave;
import org.springframework.stereotype.Component;

@Component
public class BService {
    public String getData() {
        return TenantContext.getCurrentDataSource()+"- Data";
    }
}
