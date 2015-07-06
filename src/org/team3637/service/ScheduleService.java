package org.team3637.service;

import org.team3637.model.Schedule;

import javax.sql.DataSource;
import java.util.List;

public interface ScheduleService {
    void setDataSource(DataSource dataSource);
    void create(Schedule schedule);
    Schedule getMatch(Integer matchNum);
    List<Schedule> getTeamsMatches(Integer teamNum);
    List<Schedule> getSchedule();
    void delete(Integer matchNum);
}
