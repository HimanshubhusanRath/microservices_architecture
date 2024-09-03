package com.hr.springboot.examples.demo.eventhandling.controller;

import com.hr.springboot.examples.demo.eventhandling.event.TournamentWinEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event-handling")
public class EventHandlingController {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @GetMapping("/winner/{team}")
    public String declareWinner(@PathVariable("team") final String teamName) {
        applicationEventPublisher.publishEvent(new TournamentWinEvent(this,teamName));
        return "Done";
    }
}
