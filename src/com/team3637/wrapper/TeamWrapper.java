package com.team3637.wrapper;

import com.team3637.model.Team;
import java.util.List;

public class TeamWrapper {
    private List<Team> teams;
    boolean[] deleted;

    public TeamWrapper() {}

    public TeamWrapper(List<Team> teams, boolean[] deleted) {
        this.teams = teams;
        this.deleted = deleted;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public boolean[] getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean[] deleted) {
        this.deleted = deleted;
    }
}
