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

import com.team3637.model.MatchTagExportModel;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class MatchTagServiceMySQLImpl implements MatchTagService
{
	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource dataSource)
	{
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public void exportCSV(String outputFile)
	{
		List<MatchTagExportModel> data = getMatchTagsForExport();
		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;
		try
		{
			fileWriter = new FileWriter(outputFile);
			csvFilePrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withRecordSeparator("\n"));
			for (MatchTagExportModel teamTag : data)
			{
				List<Object> line = new ArrayList<>();
				for (Field field : MatchTagExportModel.class.getDeclaredFields())
				{
					field.setAccessible(true);
					Object value = field.get(teamTag);
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
	public List<MatchTagExportModel> getMatchTagsForExport()
	{
		String SQL = "select team, matchNum, tag, occurrences, modified_timestamp from scoutingtags.matchtags order by team, matchNum, tag";
		return jdbcTemplateObject.query(SQL, new RowMapper<MatchTagExportModel>()
		{

			@Override
			public MatchTagExportModel mapRow(ResultSet resultSet, int rowNum) throws SQLException
			{

				MatchTagExportModel matchTagExportModel = new MatchTagExportModel();
				matchTagExportModel.setTeam(resultSet.getInt("team"));
				matchTagExportModel.setMatch(resultSet.getInt("matchNum"));
				matchTagExportModel.setTag(resultSet.getString("tag"));
				matchTagExportModel.setOccurrences(resultSet.getInt("occurrences"));
				matchTagExportModel.setModifiedTimestamp(resultSet.getTimestamp("modified_timestamp"));
				return matchTagExportModel;
			}
		});
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
				deleteAllMatchTags();
			CSVParser parser = CSVParser.parse(csvData, CSVFormat.DEFAULT.withRecordSeparator("\n"));
			for (CSVRecord record : parser)
			{
				MatchTagExportModel matchTagExportModel = new MatchTagExportModel();
				matchTagExportModel.setTeam(new Integer(record.get(0)));
				matchTagExportModel.setMatch(new Integer(record.get(1)));
				matchTagExportModel.setTag(record.get(2));
				matchTagExportModel.setOccurrences(new Integer(record.get(3)));
				matchTagExportModel.setModifiedTimestamp(sdf.parse(record.get(4)));
				updateInsertMatchTag(matchTagExportModel);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void updateInsertMatchTag(MatchTagExportModel matchTagExportModel)
	{
		String selectSQL = "select modified_timestamp from scoutingtags.matchtags where team = ? and matchNum = ? and tag = ?";
		Date currentModifiedDate = null;
		try
		{
			currentModifiedDate = jdbcTemplateObject.queryForObject(selectSQL, Timestamp.class,
					matchTagExportModel.getTeam(), matchTagExportModel.getMatch(), matchTagExportModel.getTag());
		}
		catch (EmptyResultDataAccessException e)
		{
			// NOOP
		}
		if (currentModifiedDate == null)
		{
			String insertSQL = "insert into scoutingtags.matchtags (team, matchNum, tag, occurrences, modified_timestamp) values (?, ?, ?, ?, ?)";
			jdbcTemplateObject.update(insertSQL, matchTagExportModel.getTeam(), matchTagExportModel.getMatch(),
					matchTagExportModel.getTag(), matchTagExportModel.getOccurrences(),
					matchTagExportModel.getModifiedTimestamp());
		}
		else if (currentModifiedDate.getTime() < matchTagExportModel.getModifiedTimestamp().getTime())
		{
			String updateSQL = "update scoutingtags.matchtags set occurrences=?, modified_timestamp = ? where team = ? and matchNum = ? and tag = ?";
			jdbcTemplateObject.update(updateSQL, matchTagExportModel.getOccurrences(),
					matchTagExportModel.getModifiedTimestamp(), matchTagExportModel.getTeam(),
					matchTagExportModel.getMatch(), matchTagExportModel.getTag());
		}
	}

	private void deleteAllMatchTags()
	{
		String sql = "delete from scoutingtags.matchtags";
		jdbcTemplateObject.update(sql);
	}

}
