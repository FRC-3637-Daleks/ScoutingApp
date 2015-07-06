package com.team3637.service;

import com.team3637.model.Match;

import javax.sql.DataSource;
import java.util.List;

public interface MatchService {
    void setDataSource(DataSource dataSource);
    void create(Match match);
    Match getMatch(Integer id);
    List<Match> getMatches();
    void delete(Integer id);
}
