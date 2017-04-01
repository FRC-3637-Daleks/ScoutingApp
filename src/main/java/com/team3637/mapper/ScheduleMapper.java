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

import java.sql.ResultSet;
import java.sql.SQLException;

import com.team3637.model.Schedule;

import org.springframework.jdbc.core.RowMapper;

public class ScheduleMapper implements RowMapper<Schedule>
{
	@Override
	public Schedule mapRow(ResultSet resultSet, int rowNum) throws SQLException
	{
		Schedule schedule = new Schedule();
		schedule.setEventId(resultSet.getString("event_id"));
		schedule.setMatchNum(resultSet.getInt("matchNum"));
		schedule.setB1(resultSet.getInt("b1"));
		schedule.setB2(resultSet.getInt("b2"));
		schedule.setB3(resultSet.getInt("b3"));
		schedule.setR1(resultSet.getInt("r1"));
		schedule.setR2(resultSet.getInt("r2"));
		schedule.setR3(resultSet.getInt("r3"));
		return schedule;
	}
}
