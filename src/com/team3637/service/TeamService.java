package com.team3637.service;

import com.team3637.model.Match;
import com.team3637.model.Team;

import javax.sql.DataSource;
import java.util.List;

public interface TeamService {
    void setDataSource(DataSource dataSource);
    void create(Team team);
    Match getTeam(Integer id);
    List<Team> getTeams();
    List<Team> getTeamByNumber(Integer teamNum);
    void update(Team team);
    void delete(Integer id);
    boolean checkForId(Integer id);
    void mergeTags(String oldTag, String newTag);
    void exportCSV(String outputFile, List<Team> data);
}
