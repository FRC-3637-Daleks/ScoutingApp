package com.team3637.service;

import com.team3637.mapper.MatchMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import com.team3637.model.Match;

import javax.sql.DataSource;
import java.lang.reflect.Field;
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
        try {
            for (int i = 0; i < fields.length; i++) {
                if (i == fields.length - 1) {
                    fieldsSting += fields[i].getName();
                    valuesSting += fields[i].get(match);
                } else {
                    fieldsSting += fields[i].getName() + ", ";
                    valuesSting += fields[i].get(match) + ", ";
                }
            }
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }
        SQL = "INSERT INTO schedule (" + fieldsSting + ") VALUES (" + valuesSting + ")";

        jdbcTemplateObject.update(SQL);
    }

    @Override
    public Match getMatch(Integer id) {
        String SQL = "SELECT * FROM matches WHERE id = ?";
        return jdbcTemplateObject.queryForObject(SQL, new Object[]{id}, new MatchMapper());
    }

    @Override
    public List<Match> getMatches() {
        String SQL = "SELECT * FROM schedule";
        List<Match> matches = jdbcTemplateObject.query(SQL, new MatchMapper());
        return matches;
    }

    @Override
    public void delete(Integer id) {
        String SQL = "delete from matches where id = ?";
        jdbcTemplateObject.update(SQL, id);
    }
}
