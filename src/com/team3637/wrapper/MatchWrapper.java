package com.team3637.wrapper;

import com.team3637.model.Match;

import java.util.List;

public class MatchWrapper {

    List<Match> matches;
    boolean[] deleted;

    public MatchWrapper() {}

    public MatchWrapper(List<Match> matches) {
        this.matches = matches;
    }

    public MatchWrapper(boolean[] deleted) {
        this.deleted = deleted;
    }

    public MatchWrapper(List<Match> matches, boolean[] deleted) {
        this.matches = matches;
        this.deleted = deleted;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public boolean[] getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean[] deleted) {
        this.deleted = deleted;
    }
}
