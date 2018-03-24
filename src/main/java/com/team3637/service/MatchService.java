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

import com.team3637.model.Match;
import com.team3637.model.MatchStatistics;
import com.team3637.model.MatchTeams;
import com.team3637.model.Team;
import com.team3637.model.TeamMatchResult;
import com.team3637.model.TeamMatchTag;

public interface MatchService extends Service {
	void setDataSource(DataSource dataSource);

	List<Match> getMatches();

	List<String> getTags();

	List<MatchStatistics> getTeamMatchStatistics(Integer teamNum, String eventId);

	List<TeamMatchTag> getTeamMatchTags(Integer teamNum, Integer matchNum);

	void incrementTag(Integer team, Integer match, String tag);

	void decrementTag(Integer team, Integer match, String tag);

	List<MatchTeams> getMatchTeams(Integer match, List<Team> teams, String eventId);

	void saveMatchResult(Integer team, Integer match, String result);

	void saveMatchScore(Integer team, Integer match, String score);

	TeamMatchResult getTeamMatchResult(Integer team, Integer match);

	void saveMatchPenalty(Integer team, Integer match, String penalty);

	void saveMatchStartPosition(Integer team, Integer match, String startPosition);

	String getDefaultEvent();

	List<Team> getTeamMatchSummaryInfo(Integer teamNum, String eventId);
}
