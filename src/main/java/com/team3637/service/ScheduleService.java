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
package com.team3637.service;

import java.util.List;

import javax.sql.DataSource;

import com.team3637.model.Schedule;

public interface ScheduleService extends Service
{
	void setDataSource(DataSource dataSource);

	void initDB(String initScript);

	Schedule getMatch(Integer matchNum);

	List<Schedule> getTeamsMatches(Integer teamNum);

	List<Schedule> getSchedule();

	int update(Schedule schedule);

	void delete(Integer matchNum);

	void addNewMatch();
}
