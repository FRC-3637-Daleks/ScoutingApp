package com.team3637.mapper;

import org.springframework.jdbc.core.RowMapper;
import com.team3637.model.Match;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchMapper implements RowMapper<Match> {
    @Override
    public Match mapRow(ResultSet resultSet, int i) throws SQLException {
        Match match = new Match();
        for(Field field : Match.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                if (field.getType() == Boolean.class) {
                    field.set(match, resultSet.getBoolean(field.getName()));
                } else if (field.getType() == Integer.class) {
                    field.set(match, resultSet.getInt(field.getName()));
                } else if (field.getType() == Float.class) {
                    field.set(match, resultSet.getFloat(field.getName()));
                } else if (field.getType() == String.class) {
                    field.set(match, resultSet.getString(field.getName()));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return match;
    }
}
