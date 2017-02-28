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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.team3637.analytics.AnalyticsReportGenerator;
import com.team3637.model.MatchStatistics;
import com.team3637.model.MatchTeams;
import com.team3637.model.Team;
import com.team3637.service.MatchService;
import com.team3637.service.ScheduleService;
import com.team3637.service.TagService;
import com.team3637.service.TeamService;

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
	public String root(Model model) {
		model.addAttribute("schedule", scheduleService.getTeamsMatches(3637));
		return "analytics";
	}

	@RequestMapping("/cache-scouting-report")
	public String cacheScoutingReport(HttpServletRequest request) {
		String baseUrl = String.format("%s://%s:%d%s/", request.getScheme(), request.getServerName(),
				request.getServerPort(), context.getContextPath());
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

	@RequestMapping("/cache-prematch-report-{matchNum}")
	public String cachePreMatchReport(@PathVariable("matchNum") Integer matchNum, HttpServletRequest request) {
		String baseUrl = String.format("%s://%s:%d%s/", request.getScheme(), request.getServerName(),
				request.getServerPort(), context.getContextPath());
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "test/html");
		headers.add("Accept", "*/*");
		HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				baseUrl + "analytics/prematch-report-" + matchNum + ".html", HttpMethod.GET, requestEntity,
				String.class);
		String report = responseEntity.getBody();
		File cachedReport = new File(context.getRealPath("/") + "cached-prematch-report.html");
		try {
			FileWriter fileWriter = new FileWriter(cachedReport);
			fileWriter.write(report);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/cached-prematch-report.html";
	}

	@RequestMapping(value = "/teamAnalytics", method = RequestMethod.GET)
	public String teamAnalytics(@RequestParam(value = "team", required = false) Integer teamNum, Model model) {
		List<Team> teams = matchService.getTeamMatchSummaryInfo(teamNum);
		model.addAttribute("teams", teams);
		for (Team team : teams) {
			List<MatchStatistics> matchStatistics = matchService.getTeamMatchStatistics(team.getTeam());
			team.setMatchStatistics(matchStatistics);
		}
		return "teamAnalytics";

	}

	@RequestMapping(value = "/teamAnalyticsByMatch", method = RequestMethod.GET)
	public String teamAnalyticsByMatch(@RequestParam(value = "match", required = false) Integer match, Model model) {
		List<Team> teams = matchService.getTeamMatchSummaryInfo(null);
		for (Team team : teams) {
			List<MatchStatistics> matchStatistics = matchService.getTeamMatchStatistics(team.getTeam());
			team.setMatchStatistics(matchStatistics);
		}
		List<MatchTeams> matchTeamsList = matchService.getMatchTeams(match, teams);
		model.addAttribute("matchTeamsList", matchTeamsList);
		return "matchAnalytics";

	}
}