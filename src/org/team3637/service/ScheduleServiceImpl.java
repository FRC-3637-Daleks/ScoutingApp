package org.team3637.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.team3637.mapper.ScheduleMapper;
import org.team3637.model.Schedule;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.List;

public class ScheduleServiceImpl implements ScheduleService {

    private JdbcTemplate jdbcTemplateObject;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(Schedule schedule) {
        Field[] fields = Schedule.class.getDeclaredFields();
        String fieldsSting = "", valuesSting = "", SQL;
        try {
            for (int i = 0; i < fields.length; i++) {
                if (i == fields.length - 1) {
                    fieldsSting += fields[i].getName();
                    valuesSting += fields[i].get(schedule);
                } else {
                    fieldsSting += fields[i].getName() + ", ";
                    valuesSting += fields[i].get(schedule) + ", ";
                }
            }
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }
        SQL = "INSERT INTO schedule (" + fieldsSting + ") VALUES (" + valuesSting + ")";

        jdbcTemplateObject.update(SQL);
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
        String SQL = "SELECT * FROM schedule";
        List<Schedule> schedule = jdbcTemplateObject.query(SQL, new ScheduleMapper());
        return schedule;
    }

    @Override
    public void delete(Integer matchNum) {
        String SQL = "delete from schedule where matchNum = ?";
        jdbcTemplateObject.update(SQL, matchNum);
    }
}
