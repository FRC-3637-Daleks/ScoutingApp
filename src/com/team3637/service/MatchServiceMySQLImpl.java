package com.team3637.service;

import com.team3637.mapper.MatchMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import com.team3637.model.Match;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MatchServiceMySQLImpl implements MatchService {


    private JdbcTemplate jdbcTemplateObject;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
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
    public void exportCSV(String outputFile) {
        String SQL = "SELECT * FROM matches INTO OUTFILE ? FIELDS TERMINATED BY ','\n ENCLOSED BY '\"' LINES TERMINATED BY '\\n'";
        jdbcTemplateObject.query(SQL, new MatchMapper(), outputFile);
    }
}