package com.hr.springboot.examples.demo.eventhandling.event.listeners;

import com.hr.springboot.examples.demo.eventhandling.event.TournamentWinEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PrizeDistributionListener {

    @EventListener
    public void distributePrize(final TournamentWinEvent event){
        System.out.println(String.format("Awarding the team '%s' with the prize money of 1 Cr",event.getTeamName()));
    }

    @EventListener
    @Async
    public void felicitate(final TournamentWinEvent event) throws Exception{
        Thread.sleep(5000);
        System.out.println(String.format("Felicitating the team '%s' upon arrival at the Airport ",event.getTeamName()));
    }

}
