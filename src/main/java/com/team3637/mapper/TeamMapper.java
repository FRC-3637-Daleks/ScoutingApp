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

import org.springframework.jdbc.core.RowMapper;

import com.team3637.model.Team;

public class TeamMapper implements RowMapper<Team> {
	@Override
	public Team mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Team team = new Team();
		team.setTeam(resultSet.getInt("team"));
		team.setMatches(resultSet.getInt("matches"));
		team.setAvgScore(resultSet.getDouble("avgscore"));
		team.setWins(resultSet.getInt("wins"));
		team.setTies(resultSet.getInt("ties"));
		team.setLosses(resultSet.getInt("losses"));
		team.setOurScore(resultSet.getDouble("ourscore"));
		team.setRankingpoints(resultSet.getInt("ranking_points"));
		team.setTagsEntered(resultSet.getInt("tagsentered"));
		return team;
	}
}
