package com.hr.springboot.examples.demo.scheduling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class CustomScheduler {

    @Scheduled(fixedRate = 5000)
    public void printTime1() {
        System.out.println(String.format("Current Time : %s", Calendar.getInstance().getTime()));
    }

    @Scheduled(cron = "0/15 * * * * *")
    public void printTime2() {
        System.out.println(String.format("[15 sec] Current Time : %s", Calendar.getInstance().getTime()));
    }
}
