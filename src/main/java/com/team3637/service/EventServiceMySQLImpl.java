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

import org.springframework.jdbc.core.JdbcTemplate;

import com.team3637.model.Event;

public class EventServiceMySQLImpl implements EventService {

	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public void exportCSV(String outputFile) {
		// TODO Auto-generated method stub

	}

	@Override
	public void importCSV(String inputFile) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Event> getEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createEvent(Event event) {
		String SQL = "INSERT INTO event (event_id, active, year, event, event_date) VALUES (?, ?, ?, ?, ?, ?, ?);";
		jdbcTemplateObject.update(SQL, tag.getTag(), tag.getCategory(), tag.getGrouping(), tag.getType(),
				tag.getInputType(), tag.getIsRankingPoint(), tag.getMaxValue());
	}

	@Override
	public void deleteEvent(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateInsertEvent(Event event) {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer saveEvent(Integer id, Integer year, Boolean active) {
		// TODO Auto-generated method stub
		return null;
	}
}