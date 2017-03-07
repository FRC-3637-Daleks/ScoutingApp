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
package com.team3637.controller.schedule;

import java.util.List;

import javax.servlet.ServletContext;

import com.team3637.model.Schedule;
import com.team3637.service.ScheduleService;
import com.team3637.wrapper.ScheduleWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ScheduleController
{

	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private ServletContext context;

	@RequestMapping("/")
	public String schedule(Model model)
	{
		model.addAttribute("schedule", scheduleService.getSchedule());
		return "schedule";
	}

	@RequestMapping("/t/")
	public String teamRedirect(@RequestParam(value = "teamNum", required = false) String teamNum)
	{
		if (teamNum != null && !teamNum.equals(""))
			return "redirect:" + context.getContextPath() + "/s/t/" + teamNum;
		else
			return "redirect:" + context.getContextPath() + "/s/";
	}

	@RequestMapping("/t/{teamNum}")
	public String team(@PathVariable("teamNum") Integer teamNum, Model model)
	{
		model.addAttribute("teamNum", teamNum);
		model.addAttribute("schedule", scheduleService.getTeamsMatches(teamNum));
		return "schedule";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model)
	{
		List<Schedule> matches = scheduleService.getSchedule();
		model.addAttribute("scheduleWrapper", new ScheduleWrapper(matches, new boolean[matches.size()]));
		return "schedule-edit";
	}

	@RequestMapping("/edit/t/")
	public String teamEditRedirect(@RequestParam(value = "teamNum", required = false) String teamNum)
	{
		if (teamNum != null && !teamNum.equals(""))
			return "redirect:/s/edit/t/" + teamNum;
		else
			return "redirect:" + context.getContextPath() + "/s/edit/";
	}

	@RequestMapping("/edit/t/{teamNum}")
	public String editTeam(@PathVariable("teamNum") Integer teamNum, Model model)
	{
		List<Schedule> matches = scheduleService.getTeamsMatches(teamNum);
		model.addAttribute("wrapper", new ScheduleWrapper(matches, new boolean[matches.size()]));
		return "schedule-edit";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editSummit(@ModelAttribute("schedule") ScheduleWrapper wrapper)
	{
		if (wrapper.getSchedule() != null && wrapper.getSchedule().size() > 0)
		{
			for (int i = 0; i < wrapper.getSchedule().size(); i++)
			{
				if (wrapper.getDeleted()[i])
				{
					scheduleService.delete(wrapper.getSchedule().get(i).getMatchNum());
				}
				else
				{
					scheduleService.update(wrapper.getSchedule().get(i));
				}
			}
		}
		return "redirect:/s/";
	}
}
