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
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.team3637.mapper.TagMapper;
import com.team3637.model.Tag;
import com.team3637.model.TagAnalyticsTeamData;

public class TagServiceMySQLImpl implements TagService {

	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public void createTag(Tag tag) {
		String SQL = "INSERT INTO tags (tag, category, grouping, type, input_type, is_ranking_point) VALUES (?, ?, ?, ?, ?, ?);";
		jdbcTemplateObject.update(SQL, tag.getTag(), tag.getCategory(), tag.getGrouping(), tag.getType(),
				tag.getInputType(), tag.getIsRankingPoint());
	}

	@Override
	public List<Tag> getMatchTags(String eventId) {
		//@formatter:off
		String SQL = "SELECT id, tag, category, grouping, type, input_type, point_value, is_ranking_point, year "
				+ "FROM scoutingtags.tags "
				+ "WHERE type='matches' and year = (select year from scoutingtags.event where event_Id = ?)"
				+ "ORDER BY category, grouping, tag";
		return jdbcTemplateObject.query(SQL, new TagMapper(), eventId);
	}

	@Override
	public List<Tag> getMatchTags() {
		//@formatter:off
		String SQL = "SELECT id, tag, category, grouping, type, input_type, point_value, is_ranking_point, year "
				+ "FROM scoutingtags.tags "
				+ "WHERE type='matches' and year = (select year from scoutingtags.competition_year where active = 1)"
				+ "ORDER BY grouping, category, tag";
		return jdbcTemplateObject.query(SQL, new TagMapper());
	}

	@Override
	public List<Tag> getTeamTags() {
		//@formatter:off
		String SQL = "SELECT id, tag, category, grouping, type, input_type, point_value, is_ranking_point, year "
				+ "FROM scoutingtags.tags "
				+ "WHERE type='teams' and year = (select year from scoutingtags.competition_year where active = 1)"
				+ "ORDER BY grouping, category, tag";
		//@formatter:on
		return jdbcTemplateObject.query(SQL, new TagMapper());
	}

	@Override
	public List<Tag> getTags() {
		//@formatter:off
		String SQL = "SELECT id, tag, category, grouping, type, input_type, point_value, year, is_ranking_point FROM scoutingtags.tags "
					+ "where year = (select year from scoutingtags.competition_year where active = 1)"
					+ "ORDER BY type, grouping, category";
		//@formatter:on
		return jdbcTemplateObject.query(SQL, new TagMapper());
	}

	@Override
	public void updateInsertTag(Tag tag) {
		String SQL = "UPDATE scoutingtags.tags SET tag=?, type=?, category=?, grouping=?, input_type=?, point_value=?, year = ?, is_ranking_point=? WHERE id=?";
		int updatedRows = jdbcTemplateObject.update(SQL, tag.getTag(), tag.getType(), tag.getCategory(),
				tag.getGrouping(), tag.getInputType(), tag.getPointValue(), tag.getYear(), tag.getIsRankingPoint(),
				tag.getId());
		if (updatedRows < 1) {
			String insertSQL = "insert into scoutingtags.tags (id, tag, type, category, grouping, input_type, point_value, year, is_ranking_point) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			jdbcTemplateObject.update(insertSQL, tag.getId(), tag.getTag(), tag.getType(), tag.getCategory(),
					tag.getGrouping(), tag.getInputType(), tag.getPointValue(), tag.getYear(), tag.getIsRankingPoint());
		}
	}

	@Override
	public void exportCSV(String outputFile) {
		List<Tag> data = getTags();
		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;
		try {
			fileWriter = new FileWriter(outputFile);
			csvFilePrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withRecordSeparator("\n"));
			for (Tag tag : data) {
				List<Object> line = new ArrayList<>();
				for (Field field : Tag.class.getDeclaredFields()) {
					field.setAccessible(true);
					Object value = field.get(tag);
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
	public void importCSV(String inputFile) {
		try {
			String csvData = new String(Files.readAllBytes(FileSystems.getDefault().getPath(inputFile)));
			csvData = csvData.replaceAll("\\r", "");
			CSVParser parser = CSVParser.parse(csvData, CSVFormat.DEFAULT.withRecordSeparator("\n"));
			for (CSVRecord record : parser) {
				Tag tag = new Tag();
				tag.setId(Integer.parseInt(record.get(0)));
				tag.setTag(record.get(1));
				tag.setType(record.get(2));
				tag.setCategory(record.get(3));
				tag.setGrouping(record.get(4));
				tag.setInputType(record.get(5));
				tag.setPointValue(Float.parseFloat(record.get(6)));
				tag.setYear(Integer.parseInt(record.get(7)));
				tag.setIsRankingPoint(Integer.parseInt(record.get(8)));
				updateInsertTag(tag);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteTag(Integer id) {
		//@formatter:off
		String sql = "DELETE FROM scoutingtags.tags WHERE id=?"
					+ " and not exists (select 1 from scoutingtags.teamtags tt "
					+ "					where tt.tag = tags.tag and tags.year = (select year from scoutingtags.event e where e.event_id = tt.event_id))"
					+ " and not exists (select 1 from scoutingtags.matchtags mt"
					+ "				   where mt.tag = tags.tag and tags.year = (select year from scoutingtags.event e where e.event_id = mt.event_id))";
		//@formatter:on

		jdbcTemplateObject.update(sql, id);

	}

	@Override
	public List<String> getTeamTagGroupings() {
		String SQL = "SELECT grouping FROM scoutingtags.taggrouping where type = 'teams' ORDER BY sequence";
		return jdbcTemplateObject.queryForList(SQL, String.class);

	}

	@Override
	public List<String> getMatchTagGroupings() {
		String SQL = "SELECT grouping FROM scoutingtags.taggrouping where type = 'matches' ORDER BY sequence";
		return jdbcTemplateObject.queryForList(SQL, String.class);

	}

	@Override
	public Integer saveTag(Integer id, String tag, String type, String category, String grouping, String inputType,
			Float pointValue, Integer isRankingPoint) {
		String sql = "UPDATE scoutingtags.tags SET tag=?, type=?, category=?, grouping=?, input_type=?, point_value=?, is_ranking_point=? WHERE id=?";
		int rowsUpdated = jdbcTemplateObject.update(sql, tag, type, category, grouping, inputType, pointValue,
				isRankingPoint, id);
		if (rowsUpdated < 1) {
			String sqlInsert = "INSERT INTO scoutingtags.tags (tag, type, category, grouping, input_type, point_value, is_ranking_point, year) VALUES (?,?,?,?,?,?,?,(select year from scoutingtags.competition_year where active = 1))";
			jdbcTemplateObject.update(sqlInsert, tag, type, category, grouping, inputType, pointValue, isRankingPoint);
			id = jdbcTemplateObject.queryForObject(
					"select id from scoutingtags.tags where tag = ? and year = (select year from scoutingtags.competition_year where active = 1) and type = ?",
					Integer.class, tag, type);
		}
		return id;
	}

	@Override
	public List<TagAnalyticsTeamData> getTopTenTeamsForTag(Tag tag, String eventId) {

		//@formatter:off
		String sql = "SELECT sum(occurrences) * t.point_value as score, team, sum(occurrences) as occurrences "
					+	"FROM scoutingtags.matchtags m "
					+	"		inner join scoutingtags.tags t on t.tag = m.tag "
					+	"where event_id = ? and m.tag = ? "
					+	"group by team order by 3 desc limit 10";
		
		//@formatter:on
		return jdbcTemplateObject.query(sql, new RowMapper<TagAnalyticsTeamData>() {
			@Override
			public TagAnalyticsTeamData mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				TagAnalyticsTeamData tagAnalyticsTeamData = new TagAnalyticsTeamData();
				tagAnalyticsTeamData.setTeam(resultSet.getInt("team"));
				tagAnalyticsTeamData.setScore(resultSet.getInt("score"));
				tagAnalyticsTeamData.setOccurrences(resultSet.getInt("occurrences"));
				return tagAnalyticsTeamData;
			}
		}, eventId, tag.getTag());
	}
}