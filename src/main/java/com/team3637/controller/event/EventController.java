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
import java.util.Date;
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

import com.team3637.model.Event;
import com.team3637.service.EventService;

@Controller
public class EventController {

	@Autowired
	private EventService eventService;
	@Autowired
	private ServletContext context;

	@RequestMapping(value = "/deleteEvent", method = RequestMethod.GET)
	public void deleteTag(@RequestParam("id") Integer id, HttpServletResponse response) {
		eventService.deleteEvent(id);
		response.setStatus(200);
	}

	@RequestMapping(value = "/saveEvent", method = RequestMethod.GET)
	@ResponseBody
	public Integer saveTag(@RequestParam("id") Integer id, @RequestParam("event_id") String eventId,
			@RequestParam("active") Boolean active, @RequestParam("year") Integer year,
			@RequestParam("event_date") Date eventDate, HttpServletResponse response) {
		return eventService.saveEvent(id, eventId, active, year, eventDate);
	}

	@RequestMapping("/export/csv")
	@ResponseBody
	public String exportCSV() throws IOException {
		String file = "events.csv";
		File exportDirectory = new File(context.getRealPath("/") + "/export");
		if (!exportDirectory.exists())
			exportDirectory.mkdir();
		String filePath = exportDirectory.getAbsolutePath() + "/" + file;
		eventService.exportCSV(filePath);
		return new String(Files.readAllBytes(FileSystems.getDefault().getPath(filePath)));
	}

	@RequestMapping(value = "/manageEvents", method = RequestMethod.GET)
	public String manageEvents(Model model) {
		List<Event> events = eventService.getEvents();
		model.addAttribute("events", events);

		return "manageEvents";
	}
}