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
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.team3637.mapper.IntegerMapper;
import com.team3637.mapper.MatchMapper;
import com.team3637.mapper.TagMapper;
import com.team3637.mapper.TagStringMapper;
import com.team3637.mapper.TeamMapper;
import com.team3637.model.Match;
import com.team3637.model.Tag;
import com.team3637.model.Team;

public class TagServiceMySQLImpl implements TagService {

	private JdbcTemplate jdbcTemplateObject;
	private SimpleJdbcCall mergeTags;
	private SimpleJdbcCall deleteTag;

	@Override
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
		this.mergeTags = new SimpleJdbcCall(dataSource).withProcedureName("mergeTags");
		this.deleteTag = new SimpleJdbcCall(dataSource).withProcedureName("deleteTag");
	}

	@Override
	public void createTag(Tag tag) {
		String SQL = "INSERT INTO tags (tag, category, grouping, type, input_type) VALUES (?, ?, ?, ?, ?);";
		jdbcTemplateObject.update(SQL, tag.getTag(), tag.getCategory(), tag.getGrouping(), tag.getType(),
				tag.getInputType());
	}

	@Override
	public Tag getTag(Integer id) {
		String SQL = "SELECT id, tag, category, grouping, type, input_type FROM scoutingtags.tagsWHERE id = ?";
		return jdbcTemplateObject.queryForObject(SQL, new TagMapper(), id);
	}

	@Override
	public Tag getTagByName(String name) {
		String SQL = "SELECT * FROM tags WHERE tag = ?";
		Tag tag = null;
		try {
			tag = jdbcTemplateObject.queryForObject(SQL, new TagMapper(), name);
		} catch (IncorrectResultSizeDataAccessException e) {
			System.err.println("Could not find tag: " + name);
		}
		return tag;
	}

	@Override
	public List<Tag> getMatchTags() {
		String SQL = "SELECT id, tag, category, grouping, type, input_type FROM scoutingtags.tags WHERE type='matches' ORDER BY type, grouping, category";
		return jdbcTemplateObject.query(SQL, new TagMapper());
	}

	@Override
	public List<Tag> getTeamTags() {
		String SQL = "SELECT id, tag, category, grouping, type, input_type FROM scoutingtags.tags WHERE type='teams' ORDER BY type, grouping, category";
		return jdbcTemplateObject.query(SQL, new TagMapper());
	}

	@Override
	public List<Tag> getTags() {
		String SQL = "SELECT id, tag, category, grouping, type, input_type FROM scoutingtags.tags ORDER BY type, grouping, category";
		return jdbcTemplateObject.query(SQL, new TagMapper());
	}

	@Override
	public List<Team> search(String[] matchTags, String[] teamTags) {
		int matchRows = jdbcTemplateObject.queryForObject("SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE "
				+ "TABLE_SCHEMA = 'scoutingtags' AND table_name = 'matches'", Integer.class) - 4;
		int teamRows = jdbcTemplateObject.queryForObject("SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE "
				+ "TABLE_SCHEMA = 'scoutingtags' AND table_name = 'teams'", Integer.class) - 4;
		String SQL = "SELECT DISTINCT matches.team FROM matches JOIN teams ON matches.team=teams.team ";
		if (matchTags.length != 0 || teamTags.length != 0)
			SQL += "WHERE ";
		if (matchTags.length != 0) {
			for (int i = 0; i < matchTags.length; i++) {
				SQL += "(";
				for (int j = 0; j < matchRows; j++) {
					SQL += "matches.tag" + j + "=" + "'" + matchTags[i] + "'";
					if (j != matchRows - 1) {
						SQL += " OR ";
					}
				}
				SQL += ")";
				if (i != matchTags.length - 1)
					SQL += " AND ";
			}
		}
		if (matchTags.length != 0 && teamTags.length != 0)
			SQL += " AND ";
		if (teamTags.length != 0) {
			for (int i = 0; i < teamTags.length; i++) {
				SQL += "(";
				for (int j = 0; j < teamRows; j++) {
					SQL += "teams.tag" + j + "=" + "'" + teamTags[i] + "'";
					if (j != teamRows - 1) {
						SQL += " OR ";
					}
				}
				SQL += ")";
				if (i != teamTags.length - 1)
					SQL += " AND ";
			}
		}
		List<Integer> teams = jdbcTemplateObject.query(SQL, new IntegerMapper());
		if (teams.size() != 0) {
			SQL = "SELECT * FROM teams WHERE ";
			for (int i = 0; i < teams.size(); i++) {
				SQL += "team = " + teams.get(i);
				if (i == teams.size() - 1) {
					SQL += " OR ";
				}
			}
			return jdbcTemplateObject.query(SQL, new TeamMapper());
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public List<Team> search(Double minScore, Double maxScore, String[] matchTags, String[] teamTags) {
		int matchRows = jdbcTemplateObject.queryForObject("SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE "
				+ "TABLE_SCHEMA = 'scoutingtags' AND table_name = 'matches'", Integer.class) - 4;
		int teamRows = jdbcTemplateObject.queryForObject("SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE "
				+ "TABLE_SCHEMA = 'scoutingtags' AND table_name = 'teams'", Integer.class) - 4;
		String SQL = "SELECT DISTINCT matches.team FROM matches JOIN teams ON matches.team=teams.team WHERE ";
		if (matchTags.length != 0) {
			for (int i = 0; i < matchTags.length; i++) {
				SQL += "(";
				for (int j = 0; j < matchRows; j++) {
					SQL += "matches.tag" + j + "=" + "'" + matchTags[i] + "'";
					if (j != matchRows - 1) {
						SQL += " OR ";
					}
				}
				SQL += ")";
				if (i != matchTags.length - 1)
					SQL += " AND ";
			}
			SQL += " AND ";
		}
		if (teamTags.length != 0) {
			for (int i = 0; i < teamTags.length; i++) {
				SQL += "(";
				for (int j = 0; j < teamRows; j++) {
					SQL += "teams.tag" + j + "=" + "'" + teamTags[i] + "'";
					if (j != teamRows - 1) {
						SQL += " OR ";
					}
				}
				SQL += ")";
				if (i != teamTags.length - 1)
					SQL += " AND ";
			}
			SQL += " AND ";
		}
		SQL += " (avgscore >= " + minScore + ") AND (avgscore <= " + maxScore + ")";
		List<Integer> teams = jdbcTemplateObject.query(SQL, new IntegerMapper());
		if (teams.size() != 0) {
			SQL = "SELECT * FROM teams WHERE ";
			for (int i = 0; i < teams.size(); i++) {
				SQL += "team = " + teams.get(i) + " ";
				if (i != teams.size() - 1) {
					SQL += " OR ";
				}
			}
			return jdbcTemplateObject.query(SQL, new TeamMapper());
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public List<Match> searchMatches(String... params) {
		int rows = jdbcTemplateObject.queryForObject("SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE "
				+ "TABLE_SCHEMA = 'scoutingtags' AND table_name = 'matches'", Integer.class) - 4;
		String SQL = "SELECT * FROM matches WHERE ";
		for (int i = 0; i < params.length; i++) {
			SQL += "(";
			for (int j = 0; j < rows; j++) {
				SQL += "tag" + j + "=" + "'" + params[i] + "'";
				if (j != rows - 1) {
					SQL += " OR ";
				}
			}
			SQL += ")";
			if (i != params.length - 1)
				SQL += " AND ";
		}
		return jdbcTemplateObject.query(SQL, new MatchMapper());
	}

	@Override
	public List<Match> searchMatches(Double minScore, Double maxScore, String... params) {
		int rows = jdbcTemplateObject.queryForObject("SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE "
				+ "TABLE_SCHEMA = 'scoutingtags' AND table_name = 'matches'", Integer.class) - 4;
		String SQL = "SELECT * FROM matches WHERE ";
		for (int i = 0; i < params.length; i++) {
			SQL += "(";
			for (int j = 0; j < rows; j++) {
				SQL += "tag" + j + "=" + "'" + params[i] + "'";
				if (j != rows - 1) {
					SQL += " OR ";
				}
			}
			SQL += ")";
			if (i != params.length - 1)
				SQL += " AND ";
		}
		SQL += " AND (avgscore >= " + minScore + ") AND (avgscore <= " + maxScore + ")";
		return jdbcTemplateObject.query(SQL, new MatchMapper());
	}

	@Override
	public List<Team> searchTeams(String... params) {
		int rows = jdbcTemplateObject.queryForObject("SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE "
				+ "TABLE_SCHEMA = 'scoutingtags' AND table_name = 'teams'", Integer.class) - 4;
		String SQL = "SELECT * FROM teams WHERE ";
		for (int i = 0; i < params.length; i++) {
			SQL += "(";
			for (int j = 0; j < rows; j++) {
				SQL += "tag" + j + "=" + "'" + params[i] + "'";
				if (j != rows - 1) {
					SQL += " OR ";
				}
			}
			SQL += ")";
			if (i != params.length - 1)
				SQL += " AND ";
		}
		return jdbcTemplateObject.query(SQL, new TeamMapper());
	}

	@Override
	public List<Team> searchTeams(Double minScore, Double maxScore, String... params) {
		int rows = jdbcTemplateObject.queryForObject("SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE "
				+ "TABLE_SCHEMA = 'scoutingtags' AND table_name = 'teams'", Integer.class) - 4;
		String SQL = "SELECT * FROM teams WHERE ";
		for (int i = 0; i < params.length; i++) {
			SQL += "(";
			for (int j = 0; j < rows; j++) {
				SQL += "tag" + j + "=" + "'" + params[i] + "'";
				if (j != rows - 1) {
					SQL += " OR ";
				}
			}
			SQL += ")";
			if (i != params.length - 1)
				SQL += " AND ";
		}
		SQL += " AND (avgscore >= " + minScore + ") AND (avgscore <= " + maxScore + ")";
		return jdbcTemplateObject.query(SQL, new TeamMapper());
	}

	@Override
	public List<String> getMatchTagStringsForTeam(Integer teamNum) {
		int matchCols = jdbcTemplateObject.queryForObject("SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE "
				+ "TABLE_SCHEMA = 'scoutingtags' AND table_name = 'matches'", Integer.class) - 4;
		String SQL = "";
		for (int i = 0; i < matchCols; i++) {
			SQL += " SELECT `tag" + i + "` FROM matches WHERE `team`=" + teamNum + " AND `tag" + i
					+ "` IS NOT NULL UNION ALL";
		}
		SQL = SQL.substring(0, SQL.length() - 9);
		return jdbcTemplateObject.query(SQL, new TagStringMapper());
	}

	@Override
	public List<String> getMatchUniqueTagStringsForTeam(Integer teamNum) {
		int matchCols = jdbcTemplateObject.queryForObject("SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE "
				+ "TABLE_SCHEMA = 'scoutingtags' AND table_name = 'matches'", Integer.class) - 4;
		String SQL = "";
		for (int i = 0; i < matchCols; i++) {
			SQL += " SELECT DISTINCT `tag" + i + "` FROM matches WHERE `team`=" + teamNum + " AND `tag" + i
					+ "` IS NOT NULL UNION ALL";
		}
		SQL = SQL.substring(0, SQL.length() - 9);
		return jdbcTemplateObject.query(SQL, new TagStringMapper());
	}

	@Override
	public void updateInsertTag(Tag tag) {
		String SQL = "UPDATE scoutingtags.tags SET tag=?, type=?, category=?, grouping=?, input_type=? WHERE id=?";
		int updatedRows = jdbcTemplateObject.update(SQL, tag.getTag(), tag.getType(), tag.getCategory(),
				tag.getGrouping(), tag.getInputType(), tag.getId());
		if (updatedRows < 1) {
			String insertSQL = "insert into scoutingtags.tags (id, tag, type, category, grouping, input_type) values (?, ?, ?, ?, ?, ?)";
			jdbcTemplateObject.update(insertSQL, tag.getId(), tag.getTag(), tag.getType(), tag.getCategory(),
					tag.getGrouping(), tag.getInputType());
		}
	}

	@Override
	public void deleteTagById(Integer id) {
		String tag = jdbcTemplateObject.queryForObject("SELECT tag FROM tags WHERE id = ?", String.class, id);
		deleteTag(tag);
	}

	@Override
	public void deleteTag(String name) {
		SqlParameterSource args = new MapSqlParameterSource().addValue("tagName", name);
		deleteTag.execute(args);
		String SQL = "DELETE FROM tags WHERE tag = ?";
		jdbcTemplateObject.update(SQL, name);
	}

	@Override
	public boolean checkTagForId(Integer id) {
		String SQL = "SELECT count(*) FROM tags WHERE id = ?";
		Integer count = jdbcTemplateObject.queryForObject(SQL, Integer.class, id);
		return count != null && count > 0;
	}

	@Override
	public boolean checkForTag(Tag tag) {
		String SQL = "SELECT count(*) FROM tags WHERE tag = ? AND type = ?";
		Integer count = jdbcTemplateObject.queryForObject(SQL, Integer.class, tag.getTag(), tag.getType());
		return count != null && count > 0;
	}

	@Override
	public void mergeTags(Tag oldTag, Tag newTag) {
		if (!oldTag.getType().equals(newTag.getType()))
			return;
		SqlParameterSource args = new MapSqlParameterSource().addValue("tableName", oldTag.getType())
				.addValue("noTagCols", 4).addValue("oldTag", oldTag.getTag()).addValue("newTag", newTag.getTag());
		mergeTags.execute(args);
		deleteTag(oldTag.getTag());
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
				updateInsertTag(tag);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteTag(Integer id) {
		String sql = "DELETE FROM scoutingtags.tags WHERE id=?";
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
	public void saveTag(Integer id, String tag, Integer category, Integer grouping, Integer inputType) {
		String sql = "UPDATE scoutingtags.tags SET tag=?, category=?, grouping=?, inputType=? WHERE id=?";
		int rowsUpdated = jdbcTemplateObject.update(sql, tag, category, grouping, inputType, id);
		if (rowsUpdated < 1) {
			String sqlInsert = "INSERT INTO scoutingtags.tags (tag, category, grouping, inputType, id) VALUES (?,?,?,?,?)";
			jdbcTemplateObject.update(sqlInsert, tag, category, grouping, inputType, id);
		}
	}
}