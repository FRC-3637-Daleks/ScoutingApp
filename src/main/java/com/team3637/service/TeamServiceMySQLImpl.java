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
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.team3637.mapper.TagStringMapper;
import com.team3637.mapper.TeamMapper;
import com.team3637.model.Team;
import com.team3637.model.TeamTag;
import com.team3637.model.TeamTagExportModel;

public class TeamServiceMySQLImpl implements TeamService {

	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public void create(Integer team) {

		String sql = "insert into scoutingtags.teams (team) values (?)";
		jdbcTemplateObject.update(sql, team);

	}

	@Override
	public Team getTeam(Integer team) {
		//@formatter:off
        String SQL = 
        		    "SELECT t.team," + 
        		    "               sum(m.score)/count(m.team) as avgscore, " +
                	"		         (select count(*)  from scoutingtags.match m2 where m2.team = t.team group by team) as matches," + 
        		    "	             sum(m.win) as wins," + 
        		    "	             sum(m.tie = 1) as ties, 				    		" + 
        		    "	             sum(m.loss = 1) as losses, " + 
        		    "	             (SELECT sum(occurrences * t.point_value) " + 
        		    "				     FROM scoutingtags.matchtags mt " + 
        		    "			                      inner join scoutingtags.tags t on mt.tag = t.tag " + 
        		    "                 WHERE mt.team = t.team) as ourscore, " + 
        		    "	             (SELECT count(*) " + 
        		    "				     FROM scoutingtags.teamtags mt " + 
        		    "			                      inner join scoutingtags.tags tg on mt.tag = tg.tag " + 
        		    "                 WHERE mt.team = t.team and occurrences > 0) as tagsentered, " +
        		    "			      (SELECT sum(ranking_points) " + 
        		    "                    FROM scoutingtags.match m3 " + 
        		    "			        WHERE m3.team = t.team) as ranking_points " + 
        		    "FROM (select ? as team) t" + 
        		    "             left outer join scoutingtags.match m on t.team = m.team " + 
        		    "WHERE t.team = ? " +
        		    "group by  t.team";
        //@formatter:on				
		return jdbcTemplateObject.queryForObject(SQL, new TeamMapper(), team, team);
	}

	@Override
	public List<Team> getTeams() {
		//@formatter:off
        String SQL = 
        		    "SELECT t.team," + 
                	"               sum(m.score)/count(m.team) as avgscore, " +
                	"		         (select count(*)  from scoutingtags.match m2 where m2.team = t.team group by team) as matches," + 
        		    "	             sum(m.win) as wins," + 
        		    "	             sum(m.tie = 1) as ties, " + 
        		    "	             sum(m.loss = 1) as losses, " + 
        		    "	             (SELECT sum(occurrences * tg.point_value) " + 
        		    "				     FROM scoutingtags.matchtags mt " + 
        		    "			                      inner join scoutingtags.tags tg on mt.tag = tg.tag " + 
        		    "                 WHERE mt.team = t.team) as ourscore, " +
        		    "	             (SELECT count(*) " + 
        		    "				     FROM scoutingtags.teamtags mt " + 
        		    "			                      inner join scoutingtags.tags tg on mt.tag = tg.tag " + 
        		    "                 WHERE mt.team = t.team and occurrences > 0) as tagsentered, " +
        		    "			      (SELECT sum(ranking_points) " + 
        		    "                    FROM scoutingtags.match m3 " + 
        		    "			        WHERE m3.team = t.team) as ranking_points " + 
    			    "FROM (select distinct team from" +
    				"                  (select distinct b1 as team" + 
    				"                     from scoutingtags.schedule" +
    				"                   union" +
    				"                   select distinct b2 as team" + 
    				"                    from scoutingtags.schedule" + 
    				"                   union" + 
    				"                  select distinct b3 as team" + 
    				"                   from scoutingtags.schedule" + 
    				"                  union" + 
    				"                  select distinct r1 as team" + 
    				"                   from scoutingtags.schedule"  +
    				"                  union" + 
    				"                  select distinct r2 as team" + 
    				"                   from scoutingtags.schedule" + 
    				"                  union" + 
    				"                  select distinct r3 as team" + 
    				"                   from scoutingtags.schedule) a) t" + 
        		    "             left outer join scoutingtags.match m on t.team = m.team " + 
        		    "group by t.team " + 
        		    "order by t.team";
        //@formatter:on
		return jdbcTemplateObject.query(SQL, new TeamMapper());
	}

	@Override
	public void delete(Team team) {
		String SQL = "DELETE FROM teams WHERE id = ?";
		jdbcTemplateObject.update(SQL, team.getId());
	}

	@Override
	public List<String> getTags() {
		String SQL = "SELECT tag FROM tags WHERE type = 'teams' ORDER BY tag";
		return jdbcTemplateObject.query(SQL, new TagStringMapper());
	}

	@Override
	public List<TeamTagExportModel> getTeamTagsForExport() {
		String SQL = "select team, tag, occurrences, modified_timestamp from teamtags order by team, tag";
		return jdbcTemplateObject.query(SQL, new RowMapper<TeamTagExportModel>() {

			@Override
			public TeamTagExportModel mapRow(ResultSet resultSet, int rowNum) throws SQLException {

				TeamTagExportModel teamTagExportModel = new TeamTagExportModel();
				teamTagExportModel.setTeam(resultSet.getInt("team"));
				teamTagExportModel.setTag(resultSet.getString("tag"));
				teamTagExportModel.setOccurrences(resultSet.getInt("occurrences"));
				teamTagExportModel.setModifiedTimestamp(resultSet.getTimestamp("modified_timestamp"));
				return teamTagExportModel;
			}
		});
	}

	@Override
	public void exportCSV(String outputFile) {
		List<TeamTagExportModel> data = getTeamTagsForExport();
		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;
		try {
			fileWriter = new FileWriter(outputFile);
			csvFilePrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withRecordSeparator("\n"));
			for (TeamTagExportModel teamTag : data) {
				List<Object> line = new ArrayList<>();
				for (Field field : TeamTagExportModel.class.getDeclaredFields()) {
					field.setAccessible(true);
					Object value = field.get(teamTag);
					line.add(value);
				}
				csvFilePrinter.printRecord(line);
			}
		} catch (IOException | IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileWriter != null) {
					fileWriter.flush();
					fileWriter.close();
				}
				if (csvFilePrinter != null) {
					csvFilePrinter.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void incrementTag(Integer team, String tag) {
		String sql = "UPDATE scoutingtags.teamtags SET occurrences=occurrences+1 WHERE team=?  AND tag=?";
		int rowsUpdated = jdbcTemplateObject.update(sql, team, tag);
		if (rowsUpdated < 1) {
			String sqlInsert = "INSERT INTO scoutingtags.teamtags (team, tag, occurrences) VALUES (?,?,?)";
			jdbcTemplateObject.update(sqlInsert, team, tag, 1);
		}
	}

	@Override
	public void decrementTag(Integer team, String tag) {
		String sql = "UPDATE scoutingtags.teamtags SET occurrences=occurrences-1 WHERE team=?  AND tag=?";
		jdbcTemplateObject.update(sql, team, tag);
	}

	@Override
	public List<TeamTag> getTeamTags(Integer teamNum) {
		//@formatter:off
		String sql = 
				   "SELECT grouping, category, t.tag, occurrences, input_type " 
		        + "FROM scoutingtags.tags t "
				+ "             LEFT OUTER JOIN scoutingtags.teamtags m on m.tag = t.tag and team = ? "
				+ " WHERE t.type = 'teams' " 
				+ "ORDER BY grouping, category, t.tag;";
		//@formatter:on
		return jdbcTemplateObject.query(sql, new RowMapper<TeamTag>() {
			@Override
			public TeamTag mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				TeamTag teamTag = new TeamTag();
				teamTag.setGrouping(resultSet.getString("grouping"));
				teamTag.setCategory(resultSet.getString("category"));
				teamTag.setOccurrences(resultSet.getInt("occurrences"));
				teamTag.setTag(resultSet.getString("tag"));
				teamTag.setInputType(resultSet.getString("input_type"));
				return teamTag;
			}
		}, teamNum);
	}

	@Override
	public void importCSV(String inputFile, Boolean delete) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			String csvData = new String(Files.readAllBytes(FileSystems.getDefault().getPath(inputFile)));
			csvData = csvData.replaceAll("\\r", "");
			if (delete)
				deleteAllTeamTags();
			CSVParser parser = CSVParser.parse(csvData, CSVFormat.DEFAULT.withRecordSeparator("\n"));
			for (CSVRecord record : parser) {
				TeamTagExportModel teamTagExportModel = new TeamTagExportModel();
				teamTagExportModel.setTeam(new Integer(record.get(0)));
				teamTagExportModel.setTag(record.get(1));
				teamTagExportModel.setOccurrences(new Integer(record.get(2)));
				teamTagExportModel.setModifiedTimestamp(sdf.parse(record.get(3)));
				updateInsertTeamTag(teamTagExportModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateInsertTeamTag(TeamTagExportModel teamTagExportModel) {
		String selectSQL = "select modified_timestamp from scoutingtags.teamtags where team = ? and tag = ?";
		Date currentModifiedDate = null;
		try {
			currentModifiedDate = jdbcTemplateObject.queryForObject(selectSQL, Timestamp.class,
					teamTagExportModel.getTeam(), teamTagExportModel.getTag());
		} catch (EmptyResultDataAccessException e) {
			// NOOP
		}
		if (currentModifiedDate == null) {
			String insertSQL = "insert into scoutingtags.teamtags (team, tag, occurrences, modified_timestamp) values (?, ?, ?, ?)";
			jdbcTemplateObject.update(insertSQL, teamTagExportModel.getTeam(), teamTagExportModel.getTag(),
					teamTagExportModel.getOccurrences(), teamTagExportModel.getModifiedTimestamp());
		} else if (currentModifiedDate.getTime() < teamTagExportModel.getModifiedTimestamp().getTime()) {
			String updateSQL = "update scoutingtags.teamtags set occurrences=?, modified_timestamp = ? where team = ? and tag = ?";
			jdbcTemplateObject.update(updateSQL, teamTagExportModel.getOccurrences(),
					teamTagExportModel.getModifiedTimestamp(), teamTagExportModel.getTeam(),
					teamTagExportModel.getTag());
		}
	}

	private void deleteAllTeamTags() {
		String sql = "delete from scoutingtags.teamtags";
		jdbcTemplateObject.update(sql);
	}
}
