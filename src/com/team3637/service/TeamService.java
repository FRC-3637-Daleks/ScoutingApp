package com.team3637.service;

import com.team3637.model.Match;
import com.team3637.model.Team;

import javax.sql.DataSource;
import java.util.List;

public interface TeamService extends Service {
    void setDataSource(DataSource dataSource);

    void create(Team team);

    Team getTeam(Integer id);

    Team getTeamById(Integer id);

    List<Team> getTeams();

    Team getTeamByNumber(Integer teamNum);

    Double[] getScoreRange();

    Integer[] getScoreRangeFor(Team team);

    void update(Team team);

    void delete(Team team);

    boolean checkForId(Integer id);

    boolean checkForTeam(Integer id);

    List<String> getTags();

    void mergeTags(String oldTag, String newTag);
}
