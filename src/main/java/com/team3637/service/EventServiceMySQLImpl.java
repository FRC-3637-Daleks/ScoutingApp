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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.jdbc.core.JdbcTemplate;

import com.team3637.mapper.EventMapper;
import com.team3637.model.Event;

public class EventServiceMySQLImpl implements EventService {

	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public void exportCSV(String outputFile) {
		List<Event> data = getEvents();
		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;
		try {
			fileWriter = new FileWriter(outputFile);
			csvFilePrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withRecordSeparator("\n"));
			for (Event event : data) {
				List<Object> line = new ArrayList<>();
				for (Field field : Event.class.getDeclaredFields()) {
					field.setAccessible(true);
					Object value = field.get(event);
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
	public void importCSV(String inputFile) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			String csvData = new String(Files.readAllBytes(FileSystems.getDefault().getPath(inputFile)));
			csvData = csvData.replaceAll("\\r", "");
			CSVParser parser = CSVParser.parse(csvData, CSVFormat.DEFAULT.withRecordSeparator("\n"));
			for (CSVRecord record : parser) {
				Event event = new Event();
				event.setId(Integer.parseInt(record.get(0)));
				event.setEventId(record.get(1));
				event.setActive(Integer.parseInt(record.get(2)));
				event.setYear(Integer.parseInt(record.get(3)));
				event.setEventDate(format.parse(record.get(4)));
				updateInsertEvent(event);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Event> getEvents() {
		String SQL = "SELECT id, event_id, active, year, event_date " + "FROM scoutingtags.event "
				+ "ORDER BY event_date desc";
		return jdbcTemplateObject.query(SQL, new EventMapper());
	}

	@Override
	public void createEvent(Event event) {
		String SQL = "INSERT INTO event (event_id, active, year, event_date) VALUES (?, ?, ?, ?, ?);";
		jdbcTemplateObject.update(SQL, event.getEventId(), event.getActive(), event.getYear(), event.getEventDate());
	}

	@Override
	public void deleteEvent(Integer id) {
		//@formatter:off
		String SQL = "DELETE FROM scoutingtags.event WHERE id=?";
		//@formatter:on
		jdbcTemplateObject.update(SQL, id);
	}

	@Override
	public void updateInsertEvent(Event event) {
		String SQL = "UPDATE scoutingtags.event SET event_id=?, active=?, year=?, event_date=?, WHERE id=?";
		int updatedRows = jdbcTemplateObject.update(SQL, event.getEventId(), event.getActive(), event.getYear(),
				event.getEventDate(), event.getId());
		if (updatedRows < 1) {
			String insertSQL = "insert into scoutingtags.event (id, event_id, active, year, event_date) values (?, ?, ?, ?, ?)";
			jdbcTemplateObject.update(insertSQL, event.getId(), event.getEventId(), event.getActive(), event.getYear(),
					event.getEventDate(), event.getId());
		}
	}

	@Override
	public Integer saveEvent(Integer id, String eventId, Date event_date, Integer year, Boolean active) {
		String SQL = "UPDATE scoutingtags.event SET event_id=?, active=?, year=?, event_date=? WHERE id=?";
		int rowsUpdated = jdbcTemplateObject.update(SQL, id, eventId, active, year, event_date);
		if (rowsUpdated < 1) {
			String sqlInsert = "INSERT INTO scoutingtags.event (event_id, active, event_date, id, year) VALUES (?, ?, ?, ?, ?)";
			jdbcTemplateObject.update(sqlInsert, eventId, active, year, event_date, id);
			id = jdbcTemplateObject.queryForObject(
					"select id from scoutingtags.event where event_id = ? and year = (select year from scoutingtags.competition_year where active = 1)",
					Integer.class, eventId);
		}
		return null;
	}
}