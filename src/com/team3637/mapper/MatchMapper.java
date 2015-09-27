package com.team3637.mapper;

import org.springframework.jdbc.core.RowMapper;
import com.team3637.model.Match;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class MatchMapper implements RowMapper<Match> {
    @Override
    public Match mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Match match = new Match();
        match.setId(resultSet.getInt("id"));
        match.setMatchNum(resultSet.getInt("matchNum"));
        match.setTeam(resultSet.getInt("team"));
        match.setScore(resultSet.getInt("score"));
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columns = rsmd.getColumnCount();
        for(int i = 5; i <= columns; i++) {
            match.getTags().add(resultSet.getString(i));
        }
        return match;
    }
}
