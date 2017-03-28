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

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import com.team3637.mapper.MatchMapper;
import com.team3637.mapper.TagStringMapper;
import com.team3637.model.Match;
import com.team3637.model.MatchStatistics;
import com.team3637.model.MatchTeams;
import com.team3637.model.Team;
import com.team3637.model.TeamMatchResult;
import com.team3637.model.TeamMatchTag;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class MatchServiceMySQLImpl implements MatchService
{

	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource dataSource)
	{
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Match> getMatches()
	{
		String SQL = "SELECT * FROM scoutingtags.match ORDER BY team ASC ";
		return jdbcTemplateObject.query(SQL, new MatchMapper());
	}

	@Override
	public List<String> getTags()
	{
		String SQL = "SELECT tag FROM tags WHERE type = 'matches' ORDER BY tag";
		return jdbcTemplateObject.query(SQL, new TagStringMapper());
	}

	@Override
	public void exportCSV(String outputFile)
	{
		List<Match> data = getMatches();
		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;
		try
		{
			fileWriter = new FileWriter(outputFile);
			csvFilePrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withRecordSeparator("\n"));
			for (Match match : data)
			{
				List<Object> line = new ArrayList<>();
				for (Field field : Match.class.getDeclaredFields())
				{
					field.setAccessible(true);
					Object value = field.get(match);
					line.add(value);
				}
				csvFilePrinter.printRecord(line);
			}
		}
		catch (IOException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (fileWriter != null)
				{
					fileWriter.flush();
					fileWriter.close();
				}
				if (csvFilePrinter != null)
				{
					csvFilePrinter.close();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void importCSV(String inputFile, Boolean delete)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			String csvData = new String(Files.readAllBytes(FileSystems.getDefault().getPath(inputFile)));
			csvData = csvData.replaceAll("\\r", "");
			if (delete)
				deleteAllMatches();
			CSVParser parser = CSVParser.parse(csvData, CSVFormat.DEFAULT.withRecordSeparator(""));
			for (CSVRecord record : parser)
			{
				Match match = new Match();
				match.setId(Integer.parseInt(record.get(0)));
				match.setMatchNum(Integer.parseInt(record.get(1)));
				match.setTeam(Integer.parseInt(record.get(2)));
				match.setScore(Integer.parseInt(record.get(3)));
				match.setWin(Integer.parseInt(record.get(4)));
				match.setLoss(Integer.parseInt(record.get(5)));
				match.setTie(Integer.parseInt(record.get(6)));
				match.setRankingPoints(Integer.parseInt(record.get(7)));
				match.setPenalty(Integer.parseInt(record.get(8)));
				match.setModifiedTimestamp(sdf.parse(record.get(9)));
				match.setStartPosition(Integer.parseInt(record.get(10)));
				updateInsertMatch(match);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void deleteAllMatches()
	{
		String sql = "delete from scoutingtags.match";
		jdbcTemplateObject.update(sql);
	}

	private void updateInsertMatch(Match match)
	{

		String selectSQL = "select modified_timestamp from scoutingtags.match where team = ? and matchNum = ?";
		Date currentModifiedDate = null;
		try
		{
			currentModifiedDate = jdbcTemplateObject.queryForObject(selectSQL, Timestamp.class, match.getTeam(),
					match.getMatchNum());
		}
		catch (EmptyResultDataAccessException e)
		{
			// NOOP
		}
		if (currentModifiedDate == null)
		{
			String insertSQL = "insert into scoutingtags.match (team, matchNum, score, win, loss, tie, ranking_points, penalty, modified_timestamp, start_positiion) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			jdbcTemplateObject.update(insertSQL, match.getTeam(), match.getMatchNum(), match.getScore(), match.getWin(),
					match.getLoss(), match.getTie(), match.getPenalty(), match.getRankingPoints(),
					match.getModifiedTimestamp());
		}
		else if (currentModifiedDate.getTime() < match.getModifiedTimestamp().getTime())
		{
			String updateSQL = "update scoutingtags.match set score=?, win=?, loss=?, tie=?, ranking_points=?, penalty=?, modified_timestamp=?, start_position = ? where team=? and matchNum=?";
			jdbcTemplateObject.update(updateSQL, match.getScore(), match.getWin(), match.getLoss(), match.getTie(),
					match.getRankingPoints(), match.getPenalty(), match.getModifiedTimestamp(),
					match.getStartPosition(), match.getTeam(), match.getMatchNum());
		}
	}

	@Override
	public List<Team> getTeamMatchSummaryInfo(Integer teamNum)
	{
		//@formatter:off
		String sql = 
				    "SELECT t.team, sum(score)/count(m.team) as avgscore, count(m.team) as matches,"
				+ "               (SELECT count(*) FROM scoutingtags.match m2 WHERE m2.team = m.team and m2.win = 1) as wins,"
				+ "               (SELECT count(*) FROM scoutingtags.match m2 WHERE m2.team = m.team and m2.tie = 1) as ties, "				    		
				+ "               (SELECT count(*) FROM scoutingtags.match m2  WHERE m2.team = m.team and m2.loss = 1) as losses, "
				+ "				  (SELECT sum(occurrences * t.point_value) "
				+ "                FROM scoutingtags.matchtags mt "
			    + "				        inner join scoutingtags.tags t on mt.tag = t.tag "
                + "                where mt.team = m.team) as ourscore, "
			    + "               (SELECT sum(ranking_points) "
                + "                FROM scoutingtags.match m3 "
			    + "                where m3.team = m.team) as rankingpoints, " 
                + "                scouting_comments "
				+ "FROM scoutingtags.teams t" 
				+ "             left outer join scoutingtags.match m on m.team = t.team "
				+ "WHERE ? is null or t.team = ? "
				+ "GROUP BY t.team";
		//@formatter:on		
		return jdbcTemplateObject.query(sql, new RowMapper<Team>()
		{
			@Override
			public Team mapRow(ResultSet resultSet, int rowNum) throws SQLException
			{
				Team team = new Team();
				team.setTeam(resultSet.getInt("team"));
				team.setMatches(resultSet.getInt("matches"));
				team.setAvgScore(resultSet.getDouble("avgscore"));
				team.setWins(resultSet.getInt("wins"));
				team.setTies(resultSet.getInt("ties"));
				team.setLosses(resultSet.getInt("losses"));
				team.setOurScore(resultSet.getDouble("ourscore"));
				team.setRankingpoints(resultSet.getInt("rankingpoints"));
				team.setScoutingComments(resultSet.getString("scouting_comments"));
				return team;
			}
		}, teamNum, teamNum);
	}

	@Override
	public List<MatchStatistics> getTeamMatchStatistics(Integer teamNum)
	{
		//@formatter:off
		String sql = 
				  "SELECT team, t.grouping, category, m.tag, tg.sequence, sum(occurrences) as occurrences "
				+ " FROM scoutingtags.matchtags m " 
			    + "      inner join scoutingtags.tags t on m.tag = t.tag "
			    + "      inner join scoutingtags.taggrouping tg on tg.grouping = t.grouping "
				+ "WHERE (? is null or team = ?) "
			    + "GROUP BY team, t.grouping,sequence, category, m.tag "
			    + "union "
				+ "SELECT team, t.grouping, category, m.tag, tg.sequence, sum(occurrences) as occurrences "
				+ " FROM scoutingtags.teamtags m " 
			    + "      inner join scoutingtags.tags t on m.tag = t.tag "
			    + "      inner join scoutingtags.taggrouping tg on tg.grouping = t.grouping "
				+ "WHERE (? is null or team = ?) "
				+ "GROUP BY team, t.grouping, sequence, category, m.tag "
				+ "ORDER BY team, sequence, category, tag";
        //@formatter:on		
		return jdbcTemplateObject.query(sql, new RowMapper<MatchStatistics>()
		{
			@Override
			public MatchStatistics mapRow(ResultSet resultSet, int rowNum) throws SQLException
			{
				MatchStatistics matchStatistics = new MatchStatistics();
				matchStatistics.setTeam(resultSet.getInt("team"));
				matchStatistics.setGrouping(resultSet.getString("grouping"));
				matchStatistics.setCategory(resultSet.getString("category"));
				matchStatistics.setTotalOccurrences(resultSet.getInt("occurrences"));
				matchStatistics.setTag(resultSet.getString("tag"));
				return matchStatistics;
			}
		}, teamNum, teamNum, teamNum, teamNum);
	}

	@Override
	public List<TeamMatchTag> getTeamMatchTags(Integer teamNum, Integer matchNum)
	{
		//@formatter:off
		String sql = 
				   "SELECT grouping, category, t.tag, occurrences, input_type " 
		        + "FROM scoutingtags.tags t "
				+ "             LEFT OUTER JOIN scoutingtags.matchtags m on m.tag = t.tag and team = ? AND matchNum = ? "
				+ " WHERE t.type = 'matches' " 
				+ "ORDER BY grouping, category, t.tag;";
		//@formatter:on
		return jdbcTemplateObject.query(sql, new RowMapper<TeamMatchTag>()
		{
			@Override
			public TeamMatchTag mapRow(ResultSet resultSet, int rowNum) throws SQLException
			{
				TeamMatchTag teamMatchTag = new TeamMatchTag();
				teamMatchTag.setGrouping(resultSet.getString("grouping"));
				teamMatchTag.setCategory(resultSet.getString("category"));
				teamMatchTag.setOccurrences(resultSet.getInt("occurrences"));
				teamMatchTag.setTag(resultSet.getString("tag"));
				teamMatchTag.setInputType(resultSet.getString("input_type"));
				return teamMatchTag;
			}
		}, teamNum, matchNum);
	}

	@Override
	public List<MatchTeams> getMatchTeams(Integer match, final List<Team> teams)
	{
		//@formatter:off
		String sql = 
			   "SELECT matchNum, b1, b2, b3, r1, r2, r3 "
		    + "FROM scoutingtags.schedule "
			+ "WHERE ? is null or matchNum = ? " 
		    + "ORDER BY matchNum";
		//@formatter:oN
		return jdbcTemplateObject.query(sql, new RowMapper<MatchTeams>()
		{
			@Override
			public MatchTeams mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				MatchTeams matchTeams = new MatchTeams();
				matchTeams.setMatch(resultSet.getInt("matchNum"));
				Integer team = resultSet.getInt("b1");
				matchTeams.getTeams().add(findTeam(teams, team));
				matchTeams.getAllianceMap().put(team.toString(), "Blue");
				team = resultSet.getInt("b2");
				matchTeams.getTeams().add(findTeam(teams, team));
				matchTeams.getAllianceMap().put(team.toString(), "Blue");
				team = resultSet.getInt("b3");
				matchTeams.getTeams().add(findTeam(teams, team));
				matchTeams.getAllianceMap().put(team.toString(), "Blue");
				team = resultSet.getInt("r1");
				matchTeams.getTeams().add(findTeam(teams, team));
				matchTeams.getAllianceMap().put(team.toString(), "Red");
				team = resultSet.getInt("r2");
				matchTeams.getTeams().add(findTeam(teams, team));
				matchTeams.getAllianceMap().put(team.toString(), "Red");
				team = resultSet.getInt("r3");
				matchTeams.getTeams().add(findTeam(teams, team));
				matchTeams.getAllianceMap().put(team.toString(), "Red");
				Iterator<Team> teamIt = matchTeams.getTeams().iterator();
				while (!matchTeams.getHasData() && teamIt.hasNext())
				{
					Team nextTeam = teamIt.next();
					Iterator<MatchStatistics> matchStatsIt = nextTeam.getMatchStatistics().iterator();
					while (!matchTeams.getHasData() && matchStatsIt.hasNext())
					{
						MatchStatistics matchStatistics = matchStatsIt.next();
						if (matchStatistics.getTotalOccurrences() > 0)
							matchTeams.setHasData(true);
					}
				}
				return matchTeams;
			}
		}, match, match);
	}

	private Team findTeam(List<Team> teams, Integer teamNum) {
		Team matchTeam = null;
		for (int i = 0; i < teams.size() && matchTeam == null; i++) {
			Team team = teams.get(i);
			if (team.getTeam().equals(teamNum)) {
				matchTeam = team;
			}
		}
		return matchTeam;
	}

	@Override
	public void incrementTag(Integer team, Integer match, String tag) {
		String sql = "UPDATE scoutingtags.matchtags SET occurrences=occurrences+1 WHERE team=? AND matchNum=? AND tag=?";
		int rowsUpdated = jdbcTemplateObject.update(sql, team, match, tag);
		if (rowsUpdated < 1) {
			String sqlInsert = "INSERT INTO scoutingtags.matchtags (team, matchNum, tag, occurrences) VALUES (?,?,?,?)";
			jdbcTemplateObject.update(sqlInsert, team, match, tag, 1);
		}
	}

	@Override
	public void decrementTag(Integer team, Integer match, String tag) {
		String sql = "UPDATE scoutingtags.matchtags SET occurrences=occurrences-1 WHERE team=? AND matchNum=? AND tag=?";
		jdbcTemplateObject.update(sql, team, match, tag);
	}

	@Override
	public void saveMatchResult(Integer team, Integer match, String result) {
		Integer win = null;
		Integer tie = null;
		Integer loss = null;
		if (result.equals("win"))
			win = 1;
		else if (result.equals("tie"))
			tie = 1;
		else if (result.equals("loss"))
			loss = 1;
		String sql = "UPDATE scoutingtags.match SET win = ?, tie = ?,  loss = ? WHERE team=? AND matchNum=?";
		int rowsUpdated = jdbcTemplateObject.update(sql, win, tie, loss, team, match);
		if (rowsUpdated < 1) {
			String sqlInsert = "INSERT INTO scoutingtags.match (team, matchNum, win, tie, loss) VALUES (?,?,?,?,?)";
			jdbcTemplateObject.update(sqlInsert, team, match, win, tie, loss);
		}
	}

	@Override
	public void saveMatchScore(Integer team, Integer match, String score) {
		String sql = "UPDATE scoutingtags.match SET score = ? WHERE team=? AND matchNum=?";
		int rowsUpdated = jdbcTemplateObject.update(sql, score, team, match);
		if (rowsUpdated < 1) {
			String sqlInsert = "INSERT INTO scoutingtags.match (team, matchNum, score) VALUES (?,?,?)";
			jdbcTemplateObject.update(sqlInsert, team, match, score);
		}
	}

	@Override

	public void saveMatchRankingPoints(Integer team, Integer match, String rankingPoints) {
		String sql = "UPDATE scoutingtags.match SET ranking_points = ? WHERE team=? AND matchNum=?";
		int rowsUpdated = jdbcTemplateObject.update(sql, rankingPoints, team, match);
		if (rowsUpdated < 1) {
			String sqlInsert = "INSERT INTO scoutingtags.match (team, matchNum, ranking_points) VALUES (?,?,?)";
			jdbcTemplateObject.update(sqlInsert, team, match, rankingPoints);
		}
	}

	@Override
	public void saveMatchPenalty(Integer team, Integer match, String penalty) {
		String sql = "UPDATE scoutingtags.match SET penalty = ? WHERE team=? AND matchNum=?";
		int rowsUpdated = jdbcTemplateObject.update(sql, penalty, team, match);
		if (rowsUpdated < 1) {
			String sqlInsert = "INSERT INTO scoutingtags.match (team, matchNum, penalty) VALUES (?,?,?)";
			jdbcTemplateObject.update(sqlInsert, team, match, penalty);
		}
	}

	@Override
	public TeamMatchResult getTeamMatchResult(Integer team, Integer match) {
		// @formatter:off
		String sql = "SELECT team, matchNum, score, win, tie, loss, ranking_points, penalty, start_position "
				           + "FROM scoutingtags.match m " 
				           + "WHERE  team = ? and matchNum = ?";
		// @formatter:on
		TeamMatchResult teamMatchResult;
		try
		{
			teamMatchResult = jdbcTemplateObject.queryForObject(sql, new RowMapper<TeamMatchResult>()
			{

				@Override
				public TeamMatchResult mapRow(ResultSet resultSet, int rowNum) throws SQLException
				{
					TeamMatchResult teamMatchResult = new TeamMatchResult();
					teamMatchResult.setTeam(resultSet.getInt("team"));
					teamMatchResult.setMatch(resultSet.getInt("matchNum"));
					Integer score = resultSet.getInt("score");
					if (!resultSet.wasNull())
						teamMatchResult.setScore(score);
					Integer rankingPoints = resultSet.getInt("ranking_points");
					if (!resultSet.wasNull())
						teamMatchResult.setRankingPoints(rankingPoints);
					Integer penalty = resultSet.getInt("penalty");
					if (!resultSet.wasNull())
						teamMatchResult.setPenalty(penalty);
					Integer result = resultSet.getInt("win");
					teamMatchResult.setWin(!resultSet.wasNull() && result > 0);
					result = resultSet.getInt("tie");
					teamMatchResult.setTie(!resultSet.wasNull() && result > 0);
					result = resultSet.getInt("loss");
					teamMatchResult.setLoss(!resultSet.wasNull() && result > 0);
					Integer startPosition = resultSet.getInt("start_position");
					if (!resultSet.wasNull())
						teamMatchResult.setStartPosition(startPosition);
					return teamMatchResult;
				}
			}, team, match);
		}
		catch (EmptyResultDataAccessException e)
		{
			// @formatter:off
			String insertSQL = "insert into  scoutingtags.match (team, matchNum, win, tie, loss, ranking_points, penalty, score, start_position) values (?, ?, 0, 0, 0, 0, 0, 0, ?)";
			// @formatter:on
			jdbcTemplateObject.update(insertSQL, team, match);
			teamMatchResult = new TeamMatchResult();
			teamMatchResult.setTeam(team);
			teamMatchResult.setMatch(match);
			teamMatchResult.setScore(0);
			teamMatchResult.setPenalty(0);
			teamMatchResult.setRankingPoints(0);
			teamMatchResult.setWin(false);
			teamMatchResult.setTie(false);
			teamMatchResult.setLoss(false);
		}
		return teamMatchResult;
	}

	@Override
	public void saveMatchStartPosition(Integer team, Integer match, String startPosition)
	{
		String sql = "UPDATE scoutingtags.match SET start_position = ? WHERE team=? AND matchNum=?";
		int rowsUpdated = jdbcTemplateObject.update(sql, startPosition, team, match);
		if (rowsUpdated < 1)
		{
			String sqlInsert = "INSERT INTO scoutingtags.match (team, matchNum, start_position) VALUES (?,?,?)";
			jdbcTemplateObject.update(sqlInsert, team, match, startPosition);
		}

	}
}
