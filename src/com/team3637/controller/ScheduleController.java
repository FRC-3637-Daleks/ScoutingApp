package com.team3637.controller;

import com.team3637.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping("/")
    public String schedule(Model model) {
        model.addAttribute("schedule", scheduleService.getSchedule());
        return "schedule";
    }

    @RequestMapping("/t/")
    public String teamRedirect(@RequestParam("teamNum")String teamNum) {
        if(teamNum != null && !teamNum.equals(""))
            return "redirect:/s/t/" + teamNum;
        else
            return "redirect:/s/t/";
    }

    @RequestMapping("/t/{teamNum}")
    public String team(Model model, @PathVariable("teamNum") String teamNum) {
        model.addAttribute("teamNum", teamNum);
        try {
            Integer team = Integer.parseInt(teamNum);
            model.addAttribute("schedule", scheduleService.getTeamsMatches(team));
        } catch (NumberFormatException e) {
            return "redirect:/s/";
        }
        return "schedule";
    }

}
