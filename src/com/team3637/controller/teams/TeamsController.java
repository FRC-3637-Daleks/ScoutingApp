package com.team3637.controller.teams;

import com.team3637.model.Team;
import com.team3637.service.MatchService;
import com.team3637.service.TagService;
import com.team3637.service.TeamService;
import com.team3637.wrapper.TeamWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletContext;
import java.util.List;

@Controller
public class TeamsController {

    @Autowired
    private MatchService matchService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private TagService tagService;
    @Autowired
    private ServletContext context;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String listTeams(Model model) {
        List<Team> teams = teamService.getTeams();
        model.addAttribute("teamWrapper", new TeamWrapper(teams, new boolean[teams.size()]));
        return "team-list";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String listTeams(@ModelAttribute("teamWrapper") TeamWrapper wrapper) {
        if (wrapper.getTeams() != null && wrapper.getTeams().size() > 0) {
            for (int i = 0; i < wrapper.getTeams().size(); i++) {
                if (wrapper.getDeleted()[i]) {
                    teamService.delete(wrapper.getTeams().get(i));
                }
            }
        }
        return "redirect:/t/";
    }

    @RequestMapping("/{teamNum}")
    public String getTeam(@PathVariable("teamNum") Integer teamNum, Model model) {
        Team team = teamService.getTeam(teamNum);
        List<String> teamTags = team.getTags();
        List<String> matchTags = tagService.getMatchTagsForTeam(teamNum);
        model.addAttribute("team", team);
        model.addAttribute("matchTags", matchTags);
        return "team";
    }

}
