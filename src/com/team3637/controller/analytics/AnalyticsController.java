package com.team3637.controller.analytics;

import com.team3637.analytics.TagDesignationGenerator;
import com.team3637.model.Match;
import com.team3637.model.Tag;
import com.team3637.model.Team;
import com.team3637.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.script.ScriptException;
import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private TagDesignationGenerator designationGenerator;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "designations";
    }


    @RequestMapping(value = "/designations.txt", method = RequestMethod.GET)
    @ResponseBody
    public String generateDesignations() {
        String designations = "";
        List<Team> teams = teamService.getTeams();
        for (Team team : teams) {
            List<Tag> tags = new ArrayList<>();
            List<Match> matches = matchService.getForTeam(team.getTeam());
            for(Match match : matches)
                for(String tag : match.getTags())
                    if (tag != null)
                        tags.add(tagService.getTagByName(tag));;
            designations += designationGenerator.generateDesignation(team.getTeam(), team.getAvgscore(), tags) + "\n";
        }
        return designations;
    }
}
