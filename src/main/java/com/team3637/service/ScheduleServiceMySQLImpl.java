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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;

import com.team3637.mapper.ScheduleMapper;
import com.team3637.model.Schedule;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.jdbc.core.JdbcTemplate;

public class ScheduleServiceMySQLImpl implements ScheduleService
{

	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource dataSource)
	{
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public void initDB(String initScript)
	{
		String script = "";
		try
		{
			Scanner sc = new Scanner(new File(initScript));
			while (sc.hasNext())
				script += sc.nextLine();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		jdbcTemplateObject.execute(script);
	}

	@Override
	public void create(Schedule schedule)
	{
		Field[] fields = Schedule.class.getDeclaredFields();
		String fieldsSting = "", valuesSting = "", SQL;
		List<Object> values = new ArrayList<>();
		try
		{
			for (int i = 1; i < fields.length; i++)
			{
				fields[i].setAccessible(true);
				values.add(fields[i].get(schedule));
				if (i == fields.length - 1)
				{
					fieldsSting += fields[i].getName();
					valuesSting += "?";
				}
				else
				{
					fieldsSting += fields[i].getName() + ", ";
					valuesSting += "?, ";
				}
			}
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		SQL = "INSERT INTO schedule (" + fieldsSting + ") VALUES (" + valuesSting + ");";

		jdbcTemplateObject.update(SQL, values.toArray());
	}

	@Override
	public Schedule getMathById(Integer id)
	{
		String SQL = "SELECT * FROM schedule WHERE id = ?";
		return jdbcTemplateObject.queryForObject(SQL, new Object[] { id }, new ScheduleMapper());
	}

	@Override
	public Schedule getMatch(Integer matchNum)
	{
		String SQL = "SELECT * FROM schedule WHERE matchNum = ?";
		return jdbcTemplateObject.queryForObject(SQL, new Object[] { matchNum }, new ScheduleMapper());
	}

	@Override
	public List<Schedule> getTeamsMatches(Integer teamNum)
	{
		String SQL = "SELECT * FROM schedule WHERE b1 = ? OR b2 = ? OR b3 = ? OR r1 = ? OR r2 = ? OR r3 = ?";
		return jdbcTemplateObject.query(SQL, new ScheduleMapper(), teamNum, teamNum, teamNum, teamNum, teamNum,
				teamNum);
	}

	@Override
	public List<Schedule> getSchedule()
	{
		String SQL = "SELECT * FROM schedule";
		List<Schedule> schedule = jdbcTemplateObject.query(SQL, new ScheduleMapper());
		return schedule;
	}

	@Override
	public void update(Schedule schedule)
	{
		Field[] fields = Schedule.class.getDeclaredFields();
		String valuesSting = "", SQL;
		List<Object> values = new ArrayList<>();
		try
		{
			for (int i = 1; i < fields.length; i++)
			{
				fields[i].setAccessible(true);
				values.add(fields[i].get(schedule));
				if (i == fields.length - 1)
				{
					valuesSting += fields[i].getName() + "=?";
				}
				else
				{
					valuesSting += fields[i].getName() + "=?, ";
				}
			}
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		SQL = "UPDATE schedule SET " + valuesSting + " WHERE id=" + schedule.getId() + ";";
		jdbcTemplateObject.update(SQL, values.toArray());
	}

	@Override
	public void delete(Integer matchNum)
	{
		String SQL = "DELETE FROM schedule WHERE matchNum = ?";
		jdbcTemplateObject.update(SQL, matchNum);
	}

	@Override
	public void deleteById(Integer id)
	{
		String SQL = "DELETE FROM schedule WHERE id = ?";
		jdbcTemplateObject.update(SQL, id);
	}

	@Override
	public boolean checkForId(Integer id)
	{
		String SQL = "SELECT count(*) FROM schedule WHERE id = ?";
		Integer count = jdbcTemplateObject.queryForObject(SQL, Integer.class, id);
		return count != null && count > 0;
	}

	@Override
	public boolean checkForMatch(Schedule schedule)
	{
		String SQL = "SELECT count(*) FROM schedule WHERE matchNum = ?";
		Integer count = jdbcTemplateObject.queryForObject(SQL, Integer.class, schedule.getMatchNum());
		return count != null && count > 0;
	}

	@Override
	public void exportCSV(String outputFile)
	{
		List<Schedule> data = getSchedule();
		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;
		try
		{
			fileWriter = new FileWriter(outputFile);
			csvFilePrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withRecordSeparator("\n"));
			for (int i = 0; i < data.size(); i++)
			{
				List<Object> line = new ArrayList<>();
				for (Field field : Schedule.class.getDeclaredFields())
				{
					field.setAccessible(true);
					Object value = field.get(data.get(i));
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
			String csvData = new String(Files.readAllBytes(FileSystems.getDefault().getPath(inputFile)));
			csvData = csvData.replaceAll("\\r", "");
			CSVParser parser = CSVParser.parse(csvData, CSVFormat.DEFAULT.withRecordSeparator("\n"));
			for (CSVRecord record : parser)
			{
				Schedule schedule = new Schedule();
				schedule.setId(Integer.parseInt(record.get(0)));
				schedule.setMatchNum(Integer.parseInt(record.get(1)));
				schedule.setB1(Integer.parseInt(record.get(2)));
				schedule.setB2(Integer.parseInt(record.get(3)));
				schedule.setB3(Integer.parseInt(record.get(4)));
				schedule.setR1(Integer.parseInt(record.get(5)));
				schedule.setR2(Integer.parseInt(record.get(6)));
				schedule.setR3(Integer.parseInt(record.get(7)));
				if (checkForMatch(schedule))
					update(schedule);
				else
					create(schedule);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
