package com.team3637.service;

import com.team3637.mapper.MatchMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import com.team3637.model.Match;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MatchServiceImpl implements MatchService {


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
                    //valuesSting += fields[i].get(match);
                    valuesSting += "?";
                } else {
                    fieldsSting += fields[i].getName() + ", ";
                    //valuesSting +=  + ", ";
                    valuesSting += "?, ";
                }
            }
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }
        SQL = "INSERT INTO matches (" + fieldsSting + ") VALUES (" + valuesSting + ");";

        jdbcTemplateObject.update(SQL, values.toArray());
    }

    @Override
    public Match getMatch(Integer id) {
        String SQL = "SELECT * FROM matches WHERE id = ?";
        return jdbcTemplateObject.queryForObject(SQL, new Object[]{id}, new MatchMapper());
    }

    @Override
    public List<Match> getMatches() {
        String SQL = "SELECT * FROM matches";
        List<Match> matches = jdbcTemplateObject.query(SQL, new MatchMapper());
        return matches;
    }

    @Override
    public void delete(Integer id) {
        String SQL = "delete from matches where id = ?";
        jdbcTemplateObject.update(SQL, id);
    }
}
