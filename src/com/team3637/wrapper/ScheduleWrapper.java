package com.team3637.wrapper;

import com.team3637.model.Schedule;

import java.util.List;

public class ScheduleWrapper {

    private List<Schedule> schedule;
    private boolean[] deleted;

    public ScheduleWrapper() {}

    public ScheduleWrapper(List<Schedule> schedule, boolean[] deleted) {
        this.schedule = schedule;
        this.deleted = deleted;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }

    public boolean[] getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean[] deleted) {
        this.deleted = deleted;
    }
}
