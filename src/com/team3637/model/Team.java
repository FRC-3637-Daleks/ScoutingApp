package com.team3637.model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private int id;
    private int team;
    private float avgscore;
    private int matches;
    private List<String> tags;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public float getAvgscore() {
        return avgscore;
    }

    public void setAvgscore(float avgscore) {
        this.avgscore = avgscore;
    }

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Team() {
        tags = new ArrayList<>();
    }
}
