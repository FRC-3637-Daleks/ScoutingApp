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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.team3637.bluealliance.api.dataaccess.TeamRankingDao;
import com.team3637.bluealliance.api.model.TeamRanking;
import com.team3637.model.Alliance;
import com.team3637.model.MatchStatistics;
import com.team3637.model.MatchTeams;
import com.team3637.model.Tag;
import com.team3637.model.TagAnalytics;
import com.team3637.model.TagAnalyticsTeamData;
import com.team3637.model.Team;
import com.team3637.model.TeamAwards;
import com.team3637.service.AwardsDao;
import com.team3637.service.MatchService;
import com.team3637.service.ScheduleService;
import com.team3637.service.TagService;
import com.team3637.service.TeamService;

@Controller
public class AnalyticsController {
	@Autowired
	private MatchService matchService;
	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private ServletContext context;
	@Autowired
	private TagService tagService;
	@Autowired
	private TeamService teamService;
	@Autowired
	private AwardsDao awardsDao;
	@Autowired
	private TeamRankingDao teamRankingDao;

	@RequestMapping(value = "/teamAnalytics", method = RequestMethod.GET)
	public String teamAnalytics(@RequestParam(value = "team", required = false) Integer teamNum,
			@RequestParam(value = "event", required = false) String eventId,
			@RequestParam(value = "hideComments", required = false, defaultValue = "false") Boolean hideComments,
			Model model) {
		List<Team> teams = matchService.getTeamMatchSummaryInfo(teamNum, eventId);
		model.addAttribute("teams", teams);
		for (Team team : teams) {
			List<MatchStatistics> matchStatistics = matchService.getTeamMatchStatistics(team.getTeam(), eventId);
			team.setMatchStatistics(matchStatistics);
			if (team.getRankingpoints() == null || team.getRankingpoints() == 0) {
				int rankingPoints = team.getTies() + (team.getWins() * 2);
				for (MatchStatistics matchStatistic : matchStatistics) {
					if (matchStatistic.isRankingPoint()) {
						rankingPoints += matchStatistic.getTotalOccurrences();
					}
					team.setRankingpoints(rankingPoints);
				}
			}
		}
		model.addAttribute("events", scheduleService.getEventList());
		model.addAttribute("selectedEvent", eventId);
		model.addAttribute("hideComments", hideComments);
		model.addAttribute("selectedReportType", "teamAnalytics");
		return "teamAnalytics";

	}

	@RequestMapping(value = "/teamAnalyticsByMatch", method = RequestMethod.GET)
	public String teamAnalyticsByMatch(@RequestParam(value = "match", required = false) Integer match,
			@RequestParam(value = "event", required = false) String eventId,
			@RequestParam(value = "hideComments", required = false, defaultValue = "false") Boolean hideComments,
			@RequestParam(value = "selectedTeam", required = false) Integer selectedTeam, Model model) {
		if (eventId == null)
			eventId = matchService.getDefaultEvent();
		List<Team> teams = matchService.getTeamMatchSummaryInfo(null, eventId);
		for (Team team : teams) {
			List<MatchStatistics> matchStatistics = matchService.getTeamMatchStatistics(team.getTeam(), eventId);
			team.setMatchStatistics(matchStatistics);
			if (team.getRankingpoints() == null || team.getRankingpoints() == 0) {
				int rankingPoints = team.getTies() + (team.getWins() * 2);
				for (MatchStatistics matchStatistic : matchStatistics) {
					if (matchStatistic.isRankingPoint()) {
						rankingPoints += matchStatistic.getTotalOccurrences();
					}
					team.setRankingpoints(rankingPoints);
				}
			}
		}
		List<MatchTeams> matchTeamsList = matchService.getMatchTeams(match, teams, eventId);
		List<MatchTeams> filteredMatchTeamsList;
		if (selectedTeam == null || selectedTeam == 0)
			filteredMatchTeamsList = matchTeamsList;
		else {
			filteredMatchTeamsList = new ArrayList<MatchTeams>();
			for (MatchTeams matchTeams : matchTeamsList) {
				Boolean containsTeam = false;
				Iterator<Team> teamIterator = matchTeams.getTeams().iterator();
				while (teamIterator.hasNext() && !containsTeam) {
					Team nextTeam = teamIterator.next();
					containsTeam = nextTeam.getTeam().equals(selectedTeam);
				}
				if (containsTeam) {
					filteredMatchTeamsList.add(matchTeams);
				}
			}
		}
		model.addAttribute("matchTeamsList", filteredMatchTeamsList);
		model.addAttribute("events", scheduleService.getEventList());
		model.addAttribute("selectedEvent", eventId);
		model.addAttribute("hideComments", hideComments);
		model.addAttribute("selectedReportType", "teamAnalyticsByMatch");
		model.addAttribute("teams", teams);
		model.addAttribute("selectedTeam", selectedTeam);
		return "matchAnalytics";

	}

	@RequestMapping(value = "/exportTeamAnalyticsByMatch", method = RequestMethod.GET)
	public String exportTeamAnalyticsByMatch(@RequestParam(value = "match", required = false) Integer match,
			@RequestParam(value = "event", required = false) String eventId, Model model,
			HttpServletResponse response) {
		if (eventId == null)
			eventId = matchService.getDefaultEvent();
		List<Team> teams = matchService.getTeamMatchSummaryInfo(null, eventId);
		for (Team team : teams) {
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
			@RequestParam(value = "event", required = false) String eventId, Model model,
			HttpServletResponse response) {
		List<Team> teams = matchService.getTeamMatchSummaryInfo(teamNum, eventId);
		model.addAttribute("teams", teams);
		for (Team team : teams) {
			List<MatchStatistics> matchStatistics = matchService.getTeamMatchStatistics(team.getTeam(), eventId);
			team.setMatchStatistics(matchStatistics);
		}
		model.addAttribute("export", true);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=teamAnalyticsByMatch.html");
		return "teamAnalytics";

	}

	@RequestMapping(value = "/tagAnalytics", method = RequestMethod.GET)
	public String tagAnalytics(@RequestParam(value = "event", required = false) String eventId,
			@RequestParam(value = "hideComments", required = false, defaultValue = "false") Boolean hideComments,
			@RequestParam(value = "selectedCategory", defaultValue = "All", required = false) String selectedCategory,
			@RequestParam(value = "selectedGrouping", defaultValue = "All", required = false) String selectedGrouping,
			Model model) {
		if (eventId == null)
			eventId = matchService.getDefaultEvent();
		List<TagAnalytics> tagAnalytics = new ArrayList<TagAnalytics>();
		List<Tag> tags = tagService.getMatchTags(eventId);
		List<String> categories = new ArrayList<String>();
		categories.add("All");
		List<String> groupings = new ArrayList<String>();
		groupings.add("All");
		for (Tag tag : tags) {
			List<TagAnalyticsTeamData> tagAnalyticsTeamData = tagService.getTopTenTeamsForTag(tag, eventId);
			TagAnalytics nextTag = new TagAnalytics();
			nextTag.setTag(tag);
			nextTag.setTopScoringTeams(tagAnalyticsTeamData);
			if (("All".equals(selectedCategory) || tag.getCategory().equals(selectedCategory))
					&& ("All".equals(selectedGrouping) || tag.getGrouping().equals(selectedGrouping)))
				tagAnalytics.add(nextTag);
			if (!categories.contains(tag.getCategory())) {
				categories.add(tag.getCategory());
			}
			if (!groupings.contains(tag.getGrouping())) {
				groupings.add(tag.getGrouping());
			}
		}
		model.addAttribute("tagAnalyticsList", tagAnalytics);
		model.addAttribute("events", scheduleService.getEventList());
		model.addAttribute("selectedEvent", eventId);
		model.addAttribute("hideComments", hideComments);
		model.addAttribute("selectedReportType", "tagAnalytics");
		model.addAttribute("categories", categories);
		model.addAttribute("selectedCategory", selectedCategory);
		model.addAttribute("groupings", groupings);
		model.addAttribute("selectedGrouping", selectedGrouping);
		return "tagAnalytics";

	}

	@RequestMapping(value = "/awardAnalytics", method = RequestMethod.GET)
	public String awardAnalytics(@RequestParam(value = "event", required = false) String eventId, Model model) {
		List<Integer> teams = teamService.getTeamsForEvent(eventId);
		if (eventId == null)
			eventId = matchService.getDefaultEvent();
		List<TeamAwards> teamAwardsList = new ArrayList<TeamAwards>();
		for (Integer team : teams) {
			TeamAwards teamAwards = new TeamAwards();
			teamAwards.setTeam(team);
			teamAwards.setAwards(awardsDao.getAwardsForTeam(team));
			teamAwardsList.add(teamAwards);
		}
		model.addAttribute("teamAwardsList", teamAwardsList);
		model.addAttribute("events", scheduleService.getEventList());
		model.addAttribute("selectedEvent", eventId);
		model.addAttribute("selectedReportType", "awardAnalytics");
		return "awardAnalytics";

	}

	@RequestMapping(value = "/blueAllianceRankings", method = RequestMethod.GET)
	public String blueAllianceRankings(@RequestParam(value = "event", required = false) String eventId, Model model) {
		if (eventId == null)
			eventId = matchService.getDefaultEvent();
		List<TeamRanking> teamRankingsList = teamRankingDao.getTeamRankings(eventId);
		model.addAttribute("teamRankingsList", teamRankingsList);
		model.addAttribute("events", scheduleService.getEventList());
		model.addAttribute("selectedEvent", eventId);
		model.addAttribute("selectedReportType", "blueAllianceRankings");
		return "blueAllianceRankings";

	}

	@RequestMapping(value = "/allianceSelection", method = RequestMethod.GET)
	public String allianceSelection(@RequestParam(value = "event", required = false) String eventId,
			@RequestParam(value = "hideComments", required = false, defaultValue = "false") Boolean hideComments,
			Model model) {

		List<Team> teams = matchService.getTeamMatchSummaryInfo(null, eventId);

		HashMap<Integer, Alliance> allianceSelection = new HashMap<Integer, Alliance>();
		allianceSelection.put(new Integer(1), new Alliance());
		allianceSelection.put(new Integer(2), new Alliance());
		allianceSelection.put(new Integer(3), new Alliance());
		allianceSelection.put(new Integer(4), new Alliance());
		allianceSelection.put(new Integer(5), new Alliance());
		allianceSelection.put(new Integer(6), new Alliance());
		allianceSelection.put(new Integer(7), new Alliance());
		allianceSelection.put(new Integer(8), new Alliance());
		for (Team team : teams) {
			team.setRankingpoints(0);
			List<MatchStatistics> matchStatistics = matchService.getTeamMatchStatistics(team.getTeam(), eventId);
			team.setMatchStatistics(matchStatistics);
			if (team.getRankingpoints() == null || team.getRankingpoints() == 0) {
				int rankingPoints = team.getTies() + (team.getWins() * 2);
				for (MatchStatistics matchStatistic : matchStatistics) {
					if (matchStatistic.isRankingPoint()) {
						rankingPoints += matchStatistic.getTotalOccurrences();
					}
					team.setRankingpoints(rankingPoints);
				}
			}
			if (team.getAlliance() != null && team.getAllianceOrder() != null) {
				Alliance matchingAlliance = allianceSelection.get(team.getAlliance());
				if (matchingAlliance == null) {
					matchingAlliance = new Alliance();
					allianceSelection.put(team.getAlliance(), matchingAlliance);
				}
				if (team.getAllianceOrder().equals(1))
					matchingAlliance.setTeam1(team.getTeam());
				if (team.getAllianceOrder().equals(2))
					matchingAlliance.setTeam2(team.getTeam());
				if (team.getAllianceOrder().equals(3))
					matchingAlliance.setTeam3(team.getTeam());
			}
		}
		teams.sort(new Comparator<Team>() {

			@Override
			public int compare(Team t1, Team t2) {
				if (t2.getRankingpoints() > t1.getRankingpoints())
					return 1;
				else if (t2.getRankingpoints() == t1.getRankingpoints()) {
					if (t1.getTeam() > t2.getTeam())
						return 1;
					else
						return -1;
				} else
					return -1;
			}
		});
		model.addAttribute("teams", teams);
		model.addAttribute("events", scheduleService.getEventList());
		model.addAttribute("selectedEvent", eventId);
		model.addAttribute("hideComments", hideComments);
		model.addAttribute("allianceSelection", allianceSelection);
		model.addAttribute("selectedReportType", "allianceSelection");
		return "allianceSelection";

	}

	@RequestMapping(value = "/saveAllianceSelection", method = RequestMethod.GET)
	@ResponseBody
	public void saveAllianceSelection(@RequestParam(value = "event") String eventId,
			@RequestParam(value = "team") Integer team, @RequestParam(value = "alliance") Integer alliance,
			@RequestParam(value = "order") Integer order, Model model) {
		teamService.saveAllianceSelection(eventId, team, alliance, order);
	}
}