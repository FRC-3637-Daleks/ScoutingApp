/*Team 3637 Scouting App - An application for data collection/analytics at FIRST competitions
 Copyright (C) 2016  Team 3637

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.team3637.mapper;

import com.team3637.model.Schedule;
import org.springframework.jdbc.core.RowMapper;

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
