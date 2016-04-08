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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileWriter;
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
    private AnalyticsReportGenerator analyticsReportGenerator;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "designations";
    }

    @RequestMapping("/cache-scouting-report")
    public String cacheScoutingReport(HttpServletRequest request) {
        String baseUrl = String.format("%s://%s:%d%s/",request.getScheme(),
                request.getServerName(), request.getServerPort(), context.getContextPath());
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "test/html");
        headers.add("Accept", "*/*");
        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
        ResponseEntity<String> responseEntity = rest.exchange(baseUrl + "analytics/scouting-report.html",
                HttpMethod.GET, requestEntity, String.class);
        String report = responseEntity.getBody();
        File cachedReport = new File(context.getRealPath("/") + "cached-scouting-report.html");
        try {
            FileWriter fileWriter = new FileWriter(cachedReport);
            fileWriter.write(report);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/cached-scouting-report.html";
    }

    @RequestMapping(value = "/scouting-report.html", method = RequestMethod.GET)
    public String generateScoutingReport(Model model) {
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
                reports.add(analyticsReportGenerator.generateAnalyticsReport(team, tags, matches, tableTags));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("reports", new AnalyticsReportWrapper(reports));
        return "scouting-report";
    }

}
