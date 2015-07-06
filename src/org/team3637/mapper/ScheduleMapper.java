package org.team3637.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.team3637.model.Schedule;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScheduleMapper implements RowMapper<Schedule> {
    @Override
    public Schedule mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Schedule schedule = new Schedule();
        for(Field field : Schedule.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                if (field.getType() == Boolean.class) {
                    field.set(schedule, resultSet.getBoolean(field.getName()));
                } else if (field.getType() == Integer.class) {
                    field.set(schedule, resultSet.getInt(field.getName()));
                } else if (field.getType() == Float.class) {
                    field.set(schedule, resultSet.getFloat(field.getName()));
                } else if (field.getType() == String.class) {
                    field.set(schedule, resultSet.getString(field.getName()));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return schedule;
    }
}
