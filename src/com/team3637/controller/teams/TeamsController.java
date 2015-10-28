package com.team3637.controller.teams;

import com.team3637.model.Team;
import com.team3637.service.MatchService;
import com.team3637.service.TagService;
import com.team3637.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/")
    public String index() {
        return "redirect:/";
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
