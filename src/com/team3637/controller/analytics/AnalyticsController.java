package com.team3637.controller.analytics;

import com.team3637.service.MatchService;
import com.team3637.service.ScheduleService;
import com.team3637.service.TagService;
import com.team3637.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletContext;

@Controller
public class AnalyticsController {

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private MatchService matchService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private TagService tagService;
    @Autowired
    private ServletContext context;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String designations() {
        return "designations";
    }

}
