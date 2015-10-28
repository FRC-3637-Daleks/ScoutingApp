package com.team3637.mapper;

import com.team3637.model.Team;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class TeamMapper implements RowMapper<Team> {
    @Override
    public Team mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Team team = new Team();
        team.setId(resultSet.getInt("id"));
        team.setTeam(resultSet.getInt("team"));
        team.setMatches(resultSet.getInt("matches"));
        team.setAvgscore(resultSet.getFloat("avgscore"));
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columns = rsmd.getColumnCount();
        for(int i = 5; i <= columns; i++) {
            if(resultSet.getString(i) != null) {
                team.getTags().add(resultSet.getString(i));
            }
        }
        return team;
    }
}
