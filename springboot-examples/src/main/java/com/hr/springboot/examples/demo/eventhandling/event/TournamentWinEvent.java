package com.hr.springboot.examples.demo.eventhandling.event;

import org.springframework.context.ApplicationEvent;

public class TournamentWinEvent extends ApplicationEvent {

    private String teamName;

    public TournamentWinEvent(final Object source, final String teamName) {
        super(source);
        this.teamName = teamName;
    }

    public String getTeamName() {
        return this.teamName;
    }
}
