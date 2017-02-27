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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.team3637.mapper.TagStringMapper;
import com.team3637.mapper.TeamMapper;
import com.team3637.model.Team;
import com.team3637.model.TeamTag;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

public class TeamServiceMySQLImpl implements TeamService
{

	private JdbcTemplate jdbcTemplateObject;
	private SimpleJdbcCall addCols;
	private SimpleJdbcCall addTag;
	private SimpleJdbcCall mergeTags;

	@Override
	public void setDataSource(DataSource dataSource)
	{
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
		this.addCols = new SimpleJdbcCall(dataSource).withProcedureName("addCols");
		this.addTag = new SimpleJdbcCall(dataSource).withProcedureName("addTag");
		this.mergeTags = new SimpleJdbcCall(dataSource).withProcedureName("mergeTags");
	}

	@Override
	public void create(Integer team)
	{

		String sql = "insert into scoutingtags.teams (team) values (?)";
		jdbcTemplateObject.update(sql, team);

	}

	@Override
	public Team getTeam(Integer team)
	{
		//@formatter:off
        String SQL = 
        		    "SELECT t.id, t.team," + 
        		    "               sum(m.score)/count(*) as avgscore, " +
                	"		         (select count(*)  from scoutingtags.match m2 where m2.team = t.team group by team) as matches," + 
        		    "	             sum(m.win) as wins," + 
        		    "	             sum(m.tie = 1) as ties, 				    		" + 
        		    "	             sum(m.loss = 1) as losses, " + 
        		    "	             (SELECT sum(occurences * t.point_value) " + 
        		    "				     FROM scoutingtags.matchtags mt " + 
        		    "			                      inner join scoutingtags.tags t on mt.tag = t.tag " + 
        		    "                 WHERE mt.team = t.team) as ourscore, " + 
        		    "			      (SELECT sum(ranking_points) " + 
        		    "                    FROM scoutingtags.match m3 " + 
        		    "			        WHERE m3.team = t.team) as ranking_points " + 
        		    "FROM scoutingtags.teams t" + 
        		    "             left outer join scoutingtags.match m on t.team = m.team " + 
        		    "WHERE t.team = ? " +
        		    "group by t.id, t.team";
        //@formatter:on				
		return jdbcTemplateObject.queryForObject(SQL, new TeamMapper(), team);
	}

	@Override
	public Team getTeamById(Integer id)
	{
		//@formatter:off
        String SQL = 
        		    "SELECT t.id, t.team," + 
                    "               sum(m.score)/count(*) as avgscore, " +
                	"		         (select count(*)  from scoutingtags.match m2 where m2.team = t.team group by team) as matches," + 
        		    "	             sum(m.win) as wins," + 
        		    "	             sum(m.tie = 1) as ties, 				    		" + 
        		    "	             sum(m.loss = 1) as losses, " + 
        		    "	             (SELECT sum(occurences * t.point_value) " + 
        		    "				     FROM scoutingtags.matchtags mt " + 
        		    "			                      inner join scoutingtags.tags t on mt.tag = t.tag " + 
        		    "                 WHERE mt.team = t.team) as ourscore, " + 
        		    "			      (SELECT sum(ranking_points) " + 
        		    "                    FROM scoutingtags.match m3 " + 
        		    "			        WHERE m3.team = t.team) as ranking_points " + 
        		    "FROM scoutingtags.teams t" + 
        		    "             left outer join scoutingtags.match m on t.team = m.team " + 
        		    "WHERE t.id = ? " +
        		    "group by t.id, t.team";
        //@formatter:on		
		return jdbcTemplateObject.queryForObject(SQL, new TeamMapper(), id);
	}

	@Override
	public List<Team> getTeams()
	{
		//@formatter:off
        String SQL = 
        		    "SELECT t.id, t.team," + 
                	"               sum(m.score)/count(*) as avgscore, " +
                	"		         (select count(*)  from scoutingtags.match m2 where m2.team = t.team group by team) as matches," + 
        		    "	             sum(m.win) as wins," + 
        		    "	             sum(m.tie = 1) as ties, 				    		" + 
        		    "	             sum(m.loss = 1) as losses, " + 
        		    "	             (SELECT sum(occurences * t.point_value) " + 
        		    "				     FROM scoutingtags.matchtags mt " + 
        		    "			                      inner join scoutingtags.tags t on mt.tag = t.tag " + 
        		    "                 WHERE mt.team = t.team) as ourscore, " + 
        		    "			      (SELECT sum(ranking_points) " + 
        		    "                    FROM scoutingtags.match m3 " + 
        		    "			        WHERE m3.team = t.team) as ranking_points " + 
        		    "FROM scoutingtags.teams t" + 
        		    "             left outer join scoutingtags.match m on t.team = m.team " + 
        		    "group by t.id, t.team " + 
        		    "order by t.team";
        //@formatter:on
		return jdbcTemplateObject.query(SQL, new TeamMapper());
	}

	@Override
	public Team getTeamByNumber(Integer teamNum)
	{
		//@formatter:off
        String SQL = 
        		    "SELECT t.id, t.team," + 
                    "               sum(m.score)/count(*) as avgscore, " +
                	"		         (select count(*)  from scoutingtags.match m2 where m2.team = t.team group by team) as matches," + 
        		    "	             sum(m.win) as wins," + 
        		    "	             sum(m.tie = 1) as ties, 				    		" + 
        		    "	             sum(m.loss = 1) as losses, " + 
        		    "	             (SELECT sum(occurences * t.point_value) " + 
        		    "				     FROM scoutingtags.matchtags mt " + 
        		    "			                      inner join scoutingtags.tags t on mt.tag = t.tag " + 
        		    "                 WHERE mt.team = t.team) as ourscore, " + 
        		    "			      (SELECT sum(ranking_points) " + 
        		    "                    FROM scoutingtags.match m3 " + 
        		    "			        WHERE m3.team = t.team) as ranking_points " + 
        		    "FROM scoutingtags.teams t" + 
        		    "             left outer join scoutingtags.match m on t.team = m.team " + 
        		    "WHERE t.team = ? " +
        		    "group by t.id, t.team";
        //@formatter:on		
		List<Team> results = jdbcTemplateObject.query(SQL, new TeamMapper(), teamNum);
		return (results.size() > 0) ? results.get(0) : null;
	}

	@Override
	public Double[] getScoreRange()
	{
		Double[] scores = new Double[2];
		String SQL = "SELECT MIN(avgscore) FROM teams";
		scores[0] = jdbcTemplateObject.queryForObject(SQL, Double.class);
		SQL = "SELECT MAX(avgscore) FROM teams";
		scores[1] = jdbcTemplateObject.queryForObject(SQL, Double.class);
		return scores;
	}

	@Override
	public Integer[] getScoreRangeFor(Team team)
	{
		Integer[] scores = new Integer[2];
		String SQL = "SELECT MIN(avgscore) FROM teams WHERE team = ?";
		scores[0] = jdbcTemplateObject.queryForObject(SQL, Integer.class, team.getTeam());
		SQL = "SELECT MAX(avgscore) FROM teams WHERE team = ?";
		scores[1] = jdbcTemplateObject.queryForObject(SQL, Integer.class, team.getTeam());
		return scores;
	}

	@Override
	public void delete(Team team)
	{
		String SQL = "DELETE FROM teams WHERE id = ?";
		jdbcTemplateObject.update(SQL, team.getId());
	}

	@Override
	public boolean checkForId(Integer id)
	{
		String SQL = "SELECT count(*) FROM teams WHERE id = ?";
		Integer count = jdbcTemplateObject.queryForObject(SQL, Integer.class, id);
		return count != null && count > 0;
	}

	@Override
	public boolean checkForTeam(Integer team)
	{
		String SQL = "SELECT count(*) FROM teams WHERE team = ?";
		Integer count = jdbcTemplateObject.queryForObject(SQL, Integer.class, team);
		return count != null && count > 0;
	}

	@Override
	public List<String> getTags()
	{
		String SQL = "SELECT tag FROM tags WHERE type = 'teams' ORDER BY tag";
		return jdbcTemplateObject.query(SQL, new TagStringMapper());
	}

	@Override
	public void mergeTags(String oldTag, String newTag)
	{
		SqlParameterSource args = new MapSqlParameterSource().addValue("tableName", "teams").addValue("noTagCols", 4)
				.addValue("oldTag", oldTag).addValue("newTag", newTag);
		mergeTags.execute(args);
	}

	@Override
	public void exportCSV(String outputFile)
	{
		List<Team> data = getTeams();
		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;
		try
		{
			fileWriter = new FileWriter(outputFile);
			csvFilePrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withRecordSeparator("\n"));
			for (Team team : data)
			{
				List<Object> line = new ArrayList<>();
				for (Field field : Team.class.getDeclaredFields())
				{
					field.setAccessible(true);
					Object value = field.get(team);
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
	public void incrementTag(Integer team, String tag)
	{
		String sql = "UPDATE scoutingtags.teamtags SET occurences=occurences+1 WHERE team=?  AND tag=?";
		int rowsUpdated = jdbcTemplateObject.update(sql, team, tag);
		if (rowsUpdated < 1)
		{
			String sqlInsert = "INSERT INTO scoutingtags.teamtags (team, tag, occurences) VALUES (?,?,?)";
			jdbcTemplateObject.update(sqlInsert, team, tag, 1);
		}
	}

	@Override
	public void decrementTag(Integer team, String tag)
	{
		String sql = "UPDATE scoutingtags.teamtags SET occurences=occurences-1 WHERE team=?  AND tag=?";
		jdbcTemplateObject.update(sql, team, tag);
	}

	@Override
	public List<TeamTag> getTeamTags(Integer teamNum)
	{
		//@formatter:off
		String sql = 
				   "SELECT grouping, category, t.tag, occurences, input_type " 
		        + "FROM scoutingtags.tags t "
				+ "             LEFT OUTER JOIN scoutingtags.teamtags m on m.tag = t.tag and team = ? "
				+ " WHERE t.type = 'teams' " 
				+ "ORDER BY grouping, category, t.tag;";
		//@formatter:on
		return jdbcTemplateObject.query(sql, new RowMapper<TeamTag>()
		{
			@Override
			public TeamTag mapRow(ResultSet resultSet, int rowNum) throws SQLException
			{
				TeamTag teamTag = new TeamTag();
				teamTag.setGrouping(resultSet.getString("grouping"));
				teamTag.setCategory(resultSet.getString("category"));
				teamTag.setOccurences(resultSet.getInt("occurences"));
				teamTag.setTag(resultSet.getString("tag"));
				teamTag.setInputType(resultSet.getString("input_type"));
				return teamTag;
			}
		}, teamNum);
	}

	@Override
	public void update(Team team)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void importCSV(String inputFile)
	{
		// TODO Auto-generated method stub

	}
}
