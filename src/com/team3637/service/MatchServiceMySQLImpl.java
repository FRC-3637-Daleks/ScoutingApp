package com.team3637.service;

import com.team3637.mapper.MatchMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.jdbc.core.JdbcTemplate;
import com.team3637.model.Match;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MatchServiceMySQLImpl implements MatchService {


    private JdbcTemplate jdbcTemplateObject;
    private SimpleJdbcCall jdbcCall;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
        this.jdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("addCols");
    }

    @Override
    public void create(Match match) {
        String fieldsSting = "id, matchNum, team, score", valuesSting = "?, ?, ?, ?", SQL;
        List<Object> values = new ArrayList<>();
        values.add(match.getId());
        values.add(match.getMatchNum());
        values.add(match.getTeam());
        values.add(match.getTeam());
        for(int i = 0; i < match.getTags().size(); i++) {
            fieldsSting += ", tag" + i;
            valuesSting += ", ?";
            values.add(match.getTags().get(i));
        }
        SQL = "INSERT INTO matches (" + fieldsSting + ") VALUES (" +
                valuesSting + ");";
        SqlParameterSource in = new MapSqlParameterSource().addValue("newCols", match.getTags().size()).addValue
                ("tableName", "matches");
        jdbcCall.execute(in);
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
        String valuesSting = "id=?, matchNum=?, team=?, score=?", SQL;
        List<Object> values = new ArrayList<>();
        values.add(match.getId());
        values.add(match.getMatchNum());
        values.add(match.getTeam());
        values.add(match.getTeam());
        for(int i = 0; i < match.getTags().size(); i++) {
            valuesSting += ", tag" + i + "=?";
            values.add(match.getTags().get(i));
        }
        SQL = "UPDATE matches SET " + valuesSting + " WHERE id=" + match.getId() + ";";
        SqlParameterSource in = new MapSqlParameterSource().addValue("newCols", match.getTags().size()).addValue
                ("tableName", "matches");;
        jdbcCall.execute(in);
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
            for (Match match : data) {
                List<Object> line = new ArrayList<>();
                for (Field field : Match.class.getDeclaredFields()) {
                    field.setAccessible(true);
                    Object value = field.get(match);
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