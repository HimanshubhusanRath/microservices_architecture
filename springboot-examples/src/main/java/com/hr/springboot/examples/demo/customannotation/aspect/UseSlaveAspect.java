package com.hr.springboot.examples.demo.customannotation.aspect;


import com.hr.springboot.examples.demo.customannotation.context.TenantContext;
import com.hr.springboot.examples.demo.customannotation.custom.UseSlave;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class UseSlaveAspect {

    @Before("@within(useSlave)")
    public void useDataSource(final UseSlave useSlave) {
        TenantContext.setCurrentDataSource("Slave:"+useSlave.version());
    }
}
