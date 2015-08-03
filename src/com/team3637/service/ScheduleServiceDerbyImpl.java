package com.team3637.service;

import com.team3637.mapper.ScheduleMapper;
import com.team3637.model.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ScheduleServiceDerbyImpl implements ScheduleService {

    private JdbcTemplate jdbcTemplateObject;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(Schedule schedule) {
        Field[] fields = Schedule.class.getDeclaredFields();
        String fieldsSting = "", valuesSting = "", SQL;
        List<Object> values = new ArrayList<>();
        try {
            for (int i = 1; i < fields.length; i++) {
                fields[i].setAccessible(true);
                values.add(fields[i].get(schedule));
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
    public Schedule getMathById(Integer id) {
        String SQL = "SELECT * FROM schedule WHERE id = ?";
        return jdbcTemplateObject.queryForObject(SQL, new Object[]{id}, new ScheduleMapper());
    }

    @Override
    public Schedule getMatch(Integer matchNum) {
        String SQL = "SELECT * FROM schedule WHERE matchNum = ?";
        return jdbcTemplateObject.queryForObject(SQL, new Object[]{matchNum}, new ScheduleMapper());
    }

    @Override
    public List<Schedule> getTeamsMatches(Integer teamNum) {
        String SQL = "SELECT * FROM schedule WHERE b1 = ? OR b2 = ? OR b3 = ? OR r1 = ? OR r2 = ? OR r3 = ?";
        return jdbcTemplateObject.query(SQL, new ScheduleMapper(), teamNum, teamNum, teamNum, teamNum, teamNum, teamNum);
    }

    @Override
    public List<Schedule> getSchedule() {
        jdbcTemplateObject.update("INSERT INTO schedule (matchNum, b1, b2, b3, r1, r2, r3) VALUES (CURRENT SCHEMA, 1, 2, 3, 4, 5, 6)");
        String SQL = "SELECT * FROM schedule";
        List<Schedule> schedule = jdbcTemplateObject.query(SQL, new ScheduleMapper());
        return schedule;
    }

    @Override
    public void update(Schedule schedule) {
        Field[] fields = Schedule.class.getDeclaredFields();
        String valuesSting = "", SQL;
        List<Object> values = new ArrayList<>();
        try {
            for (int i = 1; i < fields.length; i++) {
                fields[i].setAccessible(true);
                values.add(fields[i].get(schedule));
                if (i == fields.length - 1) {
                    valuesSting += fields[i].getName() + "=?";
                } else {
                    valuesSting += fields[i].getName() + "=?, ";
                }
            }
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }
        SQL = "UPDATE schedule SET " + valuesSting + " WHERE id=" + schedule.getId() + ";";
        jdbcTemplateObject.update(SQL, values.toArray());
    }

    @Override
    public void delete(Integer matchNum) {
        String SQL = "delete from schedule where matchNum = ?";
        jdbcTemplateObject.update(SQL, matchNum);
    }

    @Override
    public void deleteById(Integer id) {
        String SQL = "DELETE FROM schedule WHERE id = ?";
        jdbcTemplateObject.update(SQL, id);
    }

    @Override
    public void importCSV(String file) {
        String sql = "CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE (null, 'schedule', '" + file + "', '\n', ',', null ,0)";
        jdbcTemplateObject.execute(sql);
    }
}
