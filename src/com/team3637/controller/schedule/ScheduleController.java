package com.team3637.controller.schedule;

import com.team3637.model.Schedule;
import com.team3637.service.ScheduleService;
import com.team3637.wrapper.ScheduleWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private ServletContext context;

    @RequestMapping("/")
    public String schedule(Model model) {
        model.addAttribute("schedule", scheduleService.getSchedule());
        return "schedule";
    }

    @RequestMapping("/t/")
    public String teamRedirect(@RequestParam(value = "teamNum", required = false) String teamNum) {
        if (teamNum != null && !teamNum.equals(""))
            return "redirect:/s/t/" + teamNum;
        else
            return "redirect:/s/";
    }

    @RequestMapping("/t/{teamNum}")
    public String team(@PathVariable("teamNum") Integer teamNum, Model model) {
        model.addAttribute("teamNum", teamNum);
        model.addAttribute("schedule", scheduleService.getTeamsMatches(teamNum));
        return "schedule";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Model model) {
        List<Schedule> matches = scheduleService.getSchedule();
        model.addAttribute("wrapper", new ScheduleWrapper(matches, new boolean[matches.size()]));
        return "schedule-edit";
    }

    @RequestMapping("/edit/t/")
    public String teamEditRedirect(@RequestParam(value = "teamNum", required = false) String teamNum) {
        if (teamNum != null && !teamNum.equals(""))
            return "redirect:/s/edit/t/" + teamNum;
        else
            return "redirect:/s/edit/";
    }

    @RequestMapping("/edit/t/{teamNum}")
    public String editTeam(@PathVariable("teamNum") Integer teamNum, Model model) {
        List<Schedule> matches = scheduleService.getTeamsMatches(teamNum);
        model.addAttribute("wrapper", new ScheduleWrapper(matches, new boolean[matches.size()]));
        return "schedule-edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editSummit(@ModelAttribute("schedule") ScheduleWrapper wrapper) {
        if (wrapper.getSchedule() != null && wrapper.getSchedule().size() > 0) {
            for (int i = 0; i < wrapper.getSchedule().size(); i++) {
                if (wrapper.getDeleted()[i]) {
                    scheduleService.delete(wrapper.getSchedule().get(i).getMatchNum());
                } else {
                    scheduleService.update(wrapper.getSchedule().get(i));
                }
            }
        }
        return "redirect:/s/";
    }

    @RequestMapping("/export/csv")
    @ResponseBody
    public String exportCSV() {
        String directory = "export";
        String file = "schedule.csv";
        String filePath = context.getContextPath() + "/" + directory + "/" + file;

        File exportDirectory = new File(context.getRealPath("/") + "/export");
        if (!exportDirectory.exists())
            exportDirectory.mkdir();

        scheduleService.exportCSV(exportDirectory.getAbsolutePath() + "/" + file, new ArrayList<>(scheduleService.getSchedule()));

        return filePath;
    }

}
