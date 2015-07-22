package com.team3637.service;

import com.team3637.model.Match;

import javax.sql.DataSource;
import java.util.List;

public interface MatchService {
    void setDataSource(DataSource dataSource);
    void create(Match match);
    Match getMatch(Integer id);
    List<Match> getMatches();
    List<Match> getForMatch(Integer teamNum);
    List<Match> getForTeam(Integer matchNum);
    List<Match> getForMatchAndTeam(Integer matchNum, Integer teamNum);
    void update(Match match);
    void delete(Integer id);

    boolean checkForId(Integer id);

    void exportCSV(String outputFile);
}
