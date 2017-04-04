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

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import com.team3637.model.MatchStatistics;
import com.team3637.model.MatchTeams;
import com.team3637.model.Team;
import com.team3637.service.MatchService;
import com.team3637.service.ScheduleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AnalyticsController
{
	@Autowired
	private MatchService matchService;
	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private ServletContext context;

	@RequestMapping(value = "/teamAnalytics", method = RequestMethod.GET)
	public String teamAnalytics(@RequestParam(value = "team", required = false) Integer teamNum,
			@RequestParam(value = "event", required = false) String eventId,
			@RequestParam(value = "hideComments", required = false, defaultValue = "false") Boolean hideComments,
			Model model)
	{
		List<Team> teams = matchService.getTeamMatchSummaryInfo(teamNum, eventId);
		model.addAttribute("teams", teams);
		for (Team team : teams)
		{
			List<MatchStatistics> matchStatistics = matchService.getTeamMatchStatistics(team.getTeam(), eventId);
			team.setMatchStatistics(matchStatistics);
		}
		model.addAttribute("events", scheduleService.getEventList());
		model.addAttribute("selectedEvent", eventId);
		model.addAttribute("hideComments", hideComments);
		return "teamAnalytics";

	}

	@RequestMapping(value = "/teamAnalyticsByMatch", method = RequestMethod.GET)
	public String teamAnalyticsByMatch(@RequestParam(value = "match", required = false) Integer match,
			@RequestParam(value = "event", required = false) String eventId,
			@RequestParam(value = "hideComments", required = false, defaultValue = "false") Boolean hideComments,
			Model model)
	{
		if (eventId == null)
			eventId = matchService.getDefaultEvent();
		List<Team> teams = matchService.getTeamMatchSummaryInfo(null, eventId);
		for (Team team : teams)
		{
			List<MatchStatistics> matchStatistics = matchService.getTeamMatchStatistics(team.getTeam(), eventId);
			team.setMatchStatistics(matchStatistics);
		}
		List<MatchTeams> matchTeamsList = matchService.getMatchTeams(match, teams, eventId);
		model.addAttribute("matchTeamsList", matchTeamsList);
		model.addAttribute("events", scheduleService.getEventList());
		model.addAttribute("selectedEvent", eventId);
		model.addAttribute("hideComments", hideComments);
		return "matchAnalytics";

	}

	@RequestMapping(value = "/exportTeamAnalyticsByMatch", method = RequestMethod.GET)
	public String exportTeamAnalyticsByMatch(@RequestParam(value = "match", required = false) Integer match,
			@RequestParam(value = "event", required = false) String eventId, Model model, HttpServletResponse response)
	{
		if (eventId == null)
			eventId = matchService.getDefaultEvent();
		List<Team> teams = matchService.getTeamMatchSummaryInfo(null, eventId);
		for (Team team : teams)
		{
			List<MatchStatistics> matchStatistics = matchService.getTeamMatchStatistics(team.getTeam(), eventId);
			team.setMatchStatistics(matchStatistics);
		}
		List<MatchTeams> matchTeamsList = matchService.getMatchTeams(match, teams, eventId);
		model.addAttribute("matchTeamsList", matchTeamsList);
		model.addAttribute("export", true);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=teamAnalyticsByMatch.html");
		return "matchAnalytics";

	}

	@RequestMapping(value = "/exportTeamAnalytics", method = RequestMethod.GET)
	public String exportTeamAnalytics(@RequestParam(value = "team", required = false) Integer teamNum,
			@RequestParam(value = "event", required = false) String eventId, Model model, HttpServletResponse response)
	{
		List<Team> teams = matchService.getTeamMatchSummaryInfo(teamNum, eventId);
		model.addAttribute("teams", teams);
		for (Team team : teams)
		{
			List<MatchStatistics> matchStatistics = matchService.getTeamMatchStatistics(team.getTeam(), eventId);
			team.setMatchStatistics(matchStatistics);
		}
		model.addAttribute("export", true);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=teamAnalyticsByMatch.html");
		return "teamAnalytics";

	}
}