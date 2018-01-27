/*Team 3637 Scouting App - An application for data collection/analytics at FIRST competitions
 Copyright (C) 2016  Team 3637

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.team3637.controller.match;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
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

import com.google.gson.Gson;
import com.team3637.model.TeamMatchResult;
import com.team3637.model.TeamMatchTag;
import com.team3637.service.MatchService;
import com.team3637.service.MatchTagService;
import com.team3637.service.TagService;
import com.team3637.service.TeamService;

@Controller
public class MatchController {

	@Autowired
	private MatchService matchService;
	@Autowired
	private MatchTagService matchTagService;
	@Autowired
	private TeamService teamService;
	@Autowired
	private TagService tagService;
	@Autowired
	private ServletContext context;

	@RequestMapping("/")
	public String index() {
		return "redirect:" + context.getContextPath() + "/";
	}

	@RequestMapping(value = "/tags", method = RequestMethod.GET)
	public String tags(Model model) {
		List<String> matchTags = matchService.getTags();
		List<String> teamTags = teamService.getTags();
		model.addAttribute("matchTags", matchTags);
		model.addAttribute("teamTags", teamTags);
		return "tags";
	}

	@RequestMapping(value = "/deleteTag", method = RequestMethod.GET)
	public void deleteTag(@RequestParam("id") Integer id, HttpServletResponse response) {
		tagService.deleteTag(id);
		response.setStatus(200);
	}

	@RequestMapping(value = "/saveTag", method = RequestMethod.GET)
	@ResponseBody
	public Integer saveTag(@RequestParam("id") Integer id, @RequestParam("tag") String tag,
			@RequestParam("type") String type, @RequestParam("category") String category,
			@RequestParam("grouping") String grouping, @RequestParam("inputType") String inputType,
			@RequestParam("pointValue") Float pointValue, @RequestParam("isRankingPoint") Integer isRankingPoint,
			HttpServletResponse response) {
		return tagService.saveTag(id, tag, type, category, grouping, inputType, pointValue, isRankingPoint);
	}

	@RequestMapping("/export/csv")
	@ResponseBody
	public String exportCSV() throws IOException {
		String file = "matches.csv";
		File exportDirectory = new File(context.getRealPath("/") + "/export");
		if (!exportDirectory.exists())
			exportDirectory.mkdir();
		String filePath = exportDirectory.getAbsolutePath() + "/" + file;
		matchService.exportCSV(filePath);
		return new String(Files.readAllBytes(FileSystems.getDefault().getPath(filePath)));
	}

	@RequestMapping("/matchTags")
	@ResponseBody
	public String getMatchTags() {
		return new Gson().toJson(matchService.getTags());
	}

	@RequestMapping("/teamTagGroupings")
	@ResponseBody
	public List<String> getTeamTagGroupings(HttpServletResponse response) {
		response.setContentType("application/json");
		return tagService.getTeamTagGroupings();
	}

	@RequestMapping("/matchTagGroupings")
	@ResponseBody
	public List<String> getMatchTagGroupings(HttpServletResponse response) {
		response.setContentType("application/json");
		return tagService.getMatchTagGroupings();
	}

	@RequestMapping("/teamTags")
	@ResponseBody
	public String getTeamTags() {
		return new Gson().toJson(teamService.getTags());
	}

	@RequestMapping(value = "/manageTags", method = RequestMethod.GET)
	public String manageTags(Model model) {

		model.addAttribute("teamTags", tagService.getTeamTags());
		model.addAttribute("matchTags", tagService.getMatchTags());
		return "manageTags";

	}

	@RequestMapping(value = "/matchEntry", method = RequestMethod.GET)
	public String matchEntry(@RequestParam("team") Integer team, @RequestParam("match") Integer match, Model model) {
		TeamMatchResult teamMatchResult = matchService.getTeamMatchResult(team, match);
		List<TeamMatchTag> matchTags = matchService.getTeamMatchTags(team, match);

		model.addAttribute("teamMatchResult", teamMatchResult);
		model.addAttribute("matchTags", matchTags);
		return "matchEntry";
	}

	@RequestMapping(value = "/incrementTag", method = RequestMethod.GET)
	public void incrementTag(@RequestParam("team") Integer team, @RequestParam("match") Integer match,
			@RequestParam("tag") String tag, HttpServletResponse response) {
		matchService.incrementTag(team, match, tag);
		response.setStatus(200);
	}

	@RequestMapping(value = "/decrementTag", method = RequestMethod.GET)
	public void decrementTag(@RequestParam("team") Integer team, @RequestParam("match") Integer match,
			@RequestParam("tag") String tag, HttpServletResponse response) {
		matchService.decrementTag(team, match, tag);
		response.setStatus(200);
	}

	@RequestMapping(value = "/saveMatchResult", method = RequestMethod.GET)
	public void saveMapResult(@RequestParam("team") Integer team, @RequestParam("match") Integer match,
			@RequestParam("result") String result, HttpServletResponse response) {
		matchService.saveMatchResult(team, match, result);
		response.setStatus(200);
	}

	@RequestMapping(value = "/saveMatchScore", method = RequestMethod.GET)
	public void saveMatchScore(@RequestParam("team") Integer team, @RequestParam("match") Integer match,
			@RequestParam("score") String score, HttpServletResponse response) {
		matchService.saveMatchScore(team, match, score);
		response.setStatus(200);
	}

	@RequestMapping(value = "/saveMatchStartPosition", method = RequestMethod.GET)
	public void saveMatchStartPosition(@RequestParam("team") Integer team, @RequestParam("match") Integer match,
			@RequestParam("startPosition") String startPosition, HttpServletResponse response) {
		matchService.saveMatchStartPosition(team, match, startPosition);
		response.setStatus(200);
	}

	@RequestMapping(value = "/saveMatchPenalty", method = RequestMethod.GET)
	public void saveMatchPenalty(@RequestParam("team") Integer team, @RequestParam("match") Integer match,
			@RequestParam("penalty") String penalty, HttpServletResponse response) {
		matchService.saveMatchPenalty(team, match, penalty);
		response.setStatus(200);
	}

	@RequestMapping("/getMatchTags")
	@ResponseBody
	public String getMatchTags(@RequestParam("team") Integer team, @RequestParam("tag") String tag,
			@RequestParam("event") String event) {
		return new Gson().toJson(matchTagService.getMatchTags(team, tag, event));
	}
}