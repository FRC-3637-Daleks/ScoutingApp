/*
 * Created on Mar 11, 2017
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
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

import com.team3637.model.MatchTag;
import com.team3637.model.MatchTagExportModel;

public class MatchTagServiceMySQLImpl implements MatchTagService {
	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public void exportCSV(String outputFile) {
		List<MatchTagExportModel> data = getMatchTagsForExport();
		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;
		try {
			fileWriter = new FileWriter(outputFile);
			csvFilePrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withRecordSeparator("\n"));
			for (MatchTagExportModel teamTag : data) {
				List<Object> line = new ArrayList<>();
				for (Field field : MatchTagExportModel.class.getDeclaredFields()) {
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
	public List<MatchTagExportModel> getMatchTagsForExport() {
		//@formatter:off
		String SQL = "select team, matchNum, tag, occurrences, modified_timestamp, event_id "
					+ "from scoutingtags.matchtags " 
					+ "where event_id = (select event_id from event where active = 1)"
					+ "order by team, matchNum, tag";
		//@formatter:on
		return jdbcTemplateObject.query(SQL, new RowMapper<MatchTagExportModel>() {

			@Override
			public MatchTagExportModel mapRow(ResultSet resultSet, int rowNum) throws SQLException {

				MatchTagExportModel matchTagExportModel = new MatchTagExportModel();
				matchTagExportModel.setTeam(resultSet.getInt("team"));
				matchTagExportModel.setMatch(resultSet.getInt("matchNum"));
				matchTagExportModel.setTag(resultSet.getString("tag"));
				matchTagExportModel.setOccurrences(resultSet.getInt("occurrences"));
				matchTagExportModel.setModifiedTimestamp(resultSet.getTimestamp("modified_timestamp"));
				matchTagExportModel.setEventId(resultSet.getString("event_id"));
				return matchTagExportModel;
			}
		});
	}

	@Override
	public void importCSV(String inputFile) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		String csvData = new String(Files.readAllBytes(FileSystems.getDefault().getPath(inputFile)));
		csvData = csvData.replaceAll("\\r", "");

		CSVParser parser = CSVParser.parse(csvData, CSVFormat.DEFAULT.withRecordSeparator("\n"));
		for (CSVRecord record : parser) {
			MatchTagExportModel matchTagExportModel = new MatchTagExportModel();
			matchTagExportModel.setTeam(new Integer(record.get(0)));
			matchTagExportModel.setMatch(new Integer(record.get(1)));
			matchTagExportModel.setTag(record.get(2));
			try {
				matchTagExportModel.setOccurrences(new Integer(record.get(3)));
			} catch (Exception e) {
				// It's okay that the value of occurrences is null
			}
			matchTagExportModel.setModifiedTimestamp(sdf.parse(record.get(4)));
			matchTagExportModel.setEventId(record.get(5));
			updateInsertMatchTag(matchTagExportModel);
		}
	}

	private void updateInsertMatchTag(MatchTagExportModel matchTagExportModel) {
		String selectSQL = "select modified_timestamp from scoutingtags.matchtags where team = ? and matchNum = ? and tag = ? and event_id = ?";
		Date currentModifiedDate = null;
		try {
			currentModifiedDate = jdbcTemplateObject.queryForObject(selectSQL, Timestamp.class,
					matchTagExportModel.getTeam(), matchTagExportModel.getMatch(), matchTagExportModel.getTag(),
					matchTagExportModel.getEventId());
		} catch (EmptyResultDataAccessException e) {
			// NOOP
		}
		if (currentModifiedDate == null) {
			String insertSQL = "insert into scoutingtags.matchtags (team, matchNum, tag, occurrences, modified_timestamp, event_id) values (?, ?, ?, ?, ?, ?)";
			jdbcTemplateObject.update(insertSQL, matchTagExportModel.getTeam(), matchTagExportModel.getMatch(),
					matchTagExportModel.getTag(), matchTagExportModel.getOccurrences(),
					matchTagExportModel.getModifiedTimestamp(), matchTagExportModel.getEventId());
		} else if (currentModifiedDate.getTime() < matchTagExportModel.getModifiedTimestamp().getTime()) {
			String updateSQL = "update scoutingtags.matchtags set occurrences=?, modified_timestamp = ? where team = ? and matchNum = ? and tag = ? and event_id = ?";
			jdbcTemplateObject.update(updateSQL, matchTagExportModel.getOccurrences(),
					matchTagExportModel.getModifiedTimestamp(), matchTagExportModel.getTeam(),
					matchTagExportModel.getMatch(), matchTagExportModel.getTag(), matchTagExportModel.getEventId());
		}
	}

	@Override
	public List<MatchTag> getMatchTags(Integer team, String tag, String eventId) {
		if (eventId == null)
			eventId = getDefaultEvent();
		//@formatter:off
		String SQL = "select team, tag, matchNum, occurrences, event_id from scoutingtags.matchtags "
					+ "where team = ? and tag = ?  and event_id = ?"
					+ "order by matchNum";
		//@formatter:on
		return jdbcTemplateObject.query(SQL, new RowMapper<MatchTag>() {

			@Override
			public MatchTag mapRow(ResultSet resultSet, int rowNum) throws SQLException {

				MatchTag matchTag = new MatchTag();
				matchTag.setTeam(resultSet.getInt("team"));
				matchTag.setTag(resultSet.getString("tag"));
				matchTag.setMatch(resultSet.getInt("matchNum"));
				matchTag.setOccurrences(resultSet.getInt("occurrences"));
				matchTag.setEventId(resultSet.getString("event_id"));
				return matchTag;
			}
		}, team, tag, eventId);
	}

	@Override
	public String getDefaultEvent() {
		return jdbcTemplateObject.queryForObject("select event_id from scoutingtags.event where active = 1",
				String.class);
	}
}
