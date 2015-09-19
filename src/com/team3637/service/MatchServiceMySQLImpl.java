package com.team3637.service;

import com.team3637.mapper.MatchMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.jdbc.core.JdbcTemplate;
import com.team3637.model.Match;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MatchServiceMySQLImpl implements MatchService {


    private JdbcTemplate jdbcTemplateObject;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public void initDB(String initScript) {
        String script = "";
        try {
            Scanner sc = new Scanner(new File(initScript));
            while (sc.hasNext())
                script += sc.nextLine() + "\n";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.print(script);
        jdbcTemplateObject.execute(script);
    }

    @Override
    public void create(Match match) {
        Field[] fields = Match.class.getDeclaredFields();
        String fieldsSting = "", valuesSting = "", SQL;
        List<Object> values = new ArrayList<>();
        try {
            for (int i = 1; i < fields.length; i++) {
                fields[i].setAccessible(true);
                values.add(fields[i].get(match));
                if (i == fields.length - 1) {
                    fieldsSting += fields[i].getName();
                    valuesSting += "?";
                } else {
                    fieldsSting += fields[i].getName() + ", ";
                    valuesSting += "?, ";
                }
            }
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }
        SQL = "INSERT INTO schedule (" + fieldsSting + ") VALUES (" + valuesSting + ");";

        jdbcTemplateObject.update(SQL, values.toArray());
    }

    @Override
    public Match getMatch(Integer id) {
        String SQL = "SELECT * FROM matches WHERE id = ?";
        return jdbcTemplateObject.queryForObject(SQL, new MatchMapper(), id);
    }

    @Override
    public List<Match> getMatches() {
        String SQL = "SELECT * FROM matches";
        return jdbcTemplateObject.query(SQL, new MatchMapper());
    }

    @Override
    public List<Match> getForMatch(Integer teamNum) {
        String SQL = "SELECT * FROM matches WHERE team = ?";
        return jdbcTemplateObject.query(SQL, new MatchMapper(), teamNum);
    }

    @Override
    public List<Match> getForTeam(Integer matchNum) {
        String SQL = "SELECT * FROM matches WHERE matchNum = ?";
        return jdbcTemplateObject.query(SQL, new MatchMapper());
    }

    @Override
    public List<Match> getForMatchAndTeam(Integer matchNum, Integer teamNum) {
        String SQL = "SELECT * FROM matches WHERE matchNum = ? AND team = ?";
        return jdbcTemplateObject.query(SQL, new MatchMapper(), matchNum, teamNum);
    }

    @Override
    public void update(Match match) {
        Field[] fields = Match.class.getDeclaredFields();
        String valuesSting = "", SQL;
        List<Object> values = new ArrayList<>();
        try {
            for (int i = 1; i < fields.length; i++) {
                fields[i].setAccessible(true);
                values.add(fields[i].get(match));
                if (i == fields.length - 1) {
                    valuesSting += fields[i].getName() + "=?";
                } else {
                    valuesSting += fields[i].getName() + "=?, ";
                }
            }
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }
        SQL = "UPDATE matches SET " + valuesSting + " WHERE id=" + match.getId() + ";";
        jdbcTemplateObject.update(SQL, values.toArray());
    }

    @Override
    public void delete(Integer id) {
        String SQL = "DELETE FROM matches WHERE id = ?";
        jdbcTemplateObject.update(SQL, id);
    }

    @Override
    public boolean checkForId(Integer id) {
        String SQL = "SELECT count(*) FROM matches WHERE id = ?";
        Integer count = jdbcTemplateObject.queryForObject(SQL, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void exportCSV(String outputFile, List<Match> data) {
        FileWriter fileWriter = null;
        CSVPrinter csvFilePrinter = null;
        try {
            fileWriter = new FileWriter(outputFile);
            csvFilePrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withRecordSeparator("\n"));
            for(int i = 0; i < data.size(); i++) {
                List<Object> line = new ArrayList<>();
                for(Field field : Match.class.getDeclaredFields()) {
                    field.setAccessible(true);
                    Object value = field.get(data.get(i));
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
}