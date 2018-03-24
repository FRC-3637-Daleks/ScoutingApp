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

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.team3637.model.TeamExportModel;

public class AllianceSelectionMySQLImpl implements AllianceSelectionService {

	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Integer> getTeamsforActiveEvent() {
		//@formatter:off
        String SQL = 
        		    "SELECT t.team " 
        		    + "from scoutingtags.teams t " 
        		    + "where t.event_id = (select event_id from scoutingtags.event where active = 1)";
        //@formatter:on
		return jdbcTemplateObject.queryForList(SQL, Integer.class);
	}

	@Override
	public List<Integer> getTeamsForEvent(String event) {
		//@formatter:off
        String SQL = 
        		    "SELECT t.team " 
        		    + "from scoutingtags.teams t " 
        		    + "where t.event_id = ?";
        //@formatter:on
		return jdbcTemplateObject.queryForList(SQL, new Object[] { event }, Integer.class);
	}

	@Override
	public List<Integer> getAllTeams() {
		//@formatter:off
        String SQL = 
        		    "SELECT team_number " 
        		    + "from scoutingtags.teams_list t";
        //@formatter:on
		return jdbcTemplateObject.queryForList(SQL, Integer.class);
	}

	@Override
	public void updateInsertTeam(TeamExportModel teamExportModel) {
		String selectSQL = "select modified_timestamp from scoutingtags.teams where team = ? and event_id = ?";
		Date currentModifiedDate = null;
		try {
			currentModifiedDate = jdbcTemplateObject.queryForObject(selectSQL, Timestamp.class,
					teamExportModel.getTeam(), teamExportModel.getEventId());
		} catch (EmptyResultDataAccessException e) {
			// NOOP
		}
		if (currentModifiedDate == null) {
			String insertSQL = "insert into scoutingtags.teams (team, name, scouting_comments, modified_timestamp, event_id) values (?, ?, ?, ?, ?)";
			jdbcTemplateObject.update(insertSQL, teamExportModel.getTeam(), teamExportModel.getName(),
					teamExportModel.getScoutingComments(), teamExportModel.getModifiedTimestamp(),
					teamExportModel.getEventId());
		} else if (teamExportModel.getModifiedTimestamp() == null
				|| currentModifiedDate.getTime() < teamExportModel.getModifiedTimestamp().getTime()) {
			String updateSQL = "update scoutingtags.teams set name=?, scouting_comments=?, modified_timestamp = ? where team = ? and event_id = ?";
			jdbcTemplateObject.update(updateSQL, teamExportModel.getName(), teamExportModel.getScoutingComments(),
					teamExportModel.getModifiedTimestamp(), teamExportModel.getTeam(), teamExportModel.getEventId());
		}
	}

	public String getDefaultEvent() {
		return jdbcTemplateObject.queryForObject("select event_id from scoutingtags.event where active = 1",
				String.class);
	}

}
