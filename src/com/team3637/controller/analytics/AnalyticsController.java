/*
 * Team 3637 Scouting App - An application for data collection/analytics at FIRST competitions
 *  Copyright (C) 2016  Team 3637
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.team3637.controller.analytics;

import com.team3637.analytics.AnalyticsReportGenerator;
import com.team3637.model.AnalyticsReport;
import com.team3637.model.Match;
import com.team3637.model.Tag;
import com.team3637.model.Team;
import com.team3637.service.*;
import com.team3637.wrapper.AnalyticsReportWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletContext;
import java.io.IOException;
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
    private AnalyticsReportGenerator designationGenerator;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "designations";
    }


/*    @RequestMapping(value = "/designations.txt", method = RequestMethod.GET)
    @ResponseBody
    public String generateDesignations() {
        String designations = "";
        List<Team> teams = teamService.getTeams();
        for (Team team : teams) {
            List<Tag> tags = new ArrayList<>();
            List<Match> matches = matchService.getForTeam(team.getTeam());
            for (Match match : matches) {
                for (String tag : match.getTags())
                    tags.add(tagService.getTagByName(tag));
            }
            designations += designationGenerator.generateCodedDesignation(team.getTeam(), team.getAvgscore(), tags) + "\n";
        }
        return designations;
    }*/

    @RequestMapping(value = "/scouting-report.html", method = RequestMethod.GET)
    public String generateScoutingReport(Model model) {
        AnalyticsReportGenerator generator = new AnalyticsReportGenerator();
        List<AnalyticsReport> reports = new ArrayList<>();
        List<Team> teams = teamService.getTeams();
        for (Team team : teams) {
            List<Match> matches = matchService.getForTeam(team.getTeam());
            List<Tag> tags = new ArrayList<>();
            List<String> tagStrings = tagService.getMatchTagStringsForTeam(team.getTeam());
            for (String tagString : tagStrings)
                tags.add(tagService.getTagByName(tagString));
            List<Tag> tableTags = new ArrayList<>();
            for (Tag tag : tags) {
                if (tag.isInTable()) {
                    boolean inList = false;
                    for (Tag tagInTable : tableTags)
                        if (tag.compareTo(tagInTable) == 0)
                            inList = true;
                    if (!inList)
                        tableTags.add(tag);
                }
            }
            try {
                reports.add(generator.generateAnalyticsReport(team, tags, matches, tableTags));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("reports", new AnalyticsReportWrapper(reports));
        return "scouting-report";
    }

}
