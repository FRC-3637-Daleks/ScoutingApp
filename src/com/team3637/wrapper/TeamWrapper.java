package com.team3637.wrapper;

import com.team3637.model.Team;

import java.util.List;

public class TeamWrapper {
    private List<Team> teams;

    public TeamWrapper(List<Team> teams) {
        this.teams = teams;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
