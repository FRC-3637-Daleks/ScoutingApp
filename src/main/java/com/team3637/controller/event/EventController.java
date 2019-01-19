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
package com.team3637.controller.event;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EventController {

	@Autowired
	private EventService eventService;
	@Autowired
	private ServletContext context;

	@RequestMapping(value = "/deleteEvent", method = RequestMethod.GET)
	public void deleteTag(@RequestParam("id") Integer id, HttpServletResponse response) {
		tagService.deleteEvent(id);
		response.setStatus(200);
	}

	@RequestMapping(value = "/saveEvent", method = RequestMethod.GET)
	@ResponseBody
	public Integer saveTag(@RequestParam("id") Integer id, @RequestParam("tag") String tag,
			@RequestParam("type") String type, @RequestParam("category") String category,
			@RequestParam("grouping") String grouping, @RequestParam("inputType") String inputType,
			@RequestParam("pointValue") Float pointValue, @RequestParam("isRankingPoint") Integer isRankingPoint,
			@RequestParam("maxValue") Integer maxValue, HttpServletResponse response) {
		return tagService.saveTag(id, tag, type, category, grouping, inputType, pointValue, isRankingPoint, maxValue);
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

	@RequestMapping(value = "/manageEvents", method = RequestMethod.GET)
	public String manageTags(Model model) {

		model.addAttribute("teamTags", tagService.getTeamTags());
		model.addAttribute("matchTags", tagService.getMatchTags());
		return "manageEvents";

	}
}