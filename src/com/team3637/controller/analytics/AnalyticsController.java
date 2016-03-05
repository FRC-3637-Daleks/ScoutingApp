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
        return "redirect:/";
    }

    @RequestMapping(value = "/designations", method = RequestMethod.GET)
    public String designations() {

        String designations;
        List<Match> matches = matchService.getMatches();
        for(Match match : matches) {

        }

        return "designations";
    }


    @RequestMapping(value = "/designations/gen", method = RequestMethod.GET)
    @ResponseBody
    public String generateDesignations() {

        String designations = "";
        List<Match> matches = matchService.getMatches();
        try {
            for(Match match : matches) {
                List<Tag> tags = new ArrayList<>();
                Team team = teamService.getTeamByNumber(match.getTeam());
                for(String tag : match.getTags())
                    tags.add(tagService.getTagByName(tag));
                designations += designationGenerator.generateDesignation(team.getTeam(), team.getAvgscore(), tags) + "\n";
            }
        } catch (ScriptException e) {
            e.printStackTrace();
        }

        return designations;
    }
}
