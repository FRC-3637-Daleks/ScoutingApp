package com.team3637.controller.match;

import com.google.gson.Gson;
import com.team3637.model.Match;
import com.team3637.model.Tag;
import com.team3637.model.Team;
import com.team3637.service.MatchService;
import com.team3637.service.TagService;
import com.team3637.service.TeamService;
import com.team3637.wrapper.MatchWrapper;
import com.team3637.wrapper.TagWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

@Controller
public class MatchController {

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

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String newMatch(@RequestParam("teamNum") Integer teamNum,
                           @RequestParam("matchNum") Integer matchNum,
                           Model model) {

        List<Match> matches = matchService.getForMatchAndTeam(matchNum, teamNum);
        List<Team> teams = teamService.getTeamByNumber(teamNum);
        List<String> matchTags = matchService.getTags();
        List<String> teamTags = teamService.getTags();
        Match match;
        Team team;
        if (matches.size() == 0) {
            match = new Match();
        } else {
            match = matches.get(0);
        }
        if(teams.size() == 0) {
            team = new Team();
        } else {
            team = teams.get(0);
        }
        model.addAttribute("match", match);
        model.addAttribute("team", team);
        model.addAttribute("teamNum", teamNum);
        model.addAttribute("matchNum", matchNum);
        model.addAttribute("matchTags", matchTags);
        model.addAttribute("teamTags", teamTags);

        return "match";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> submitNewMatch(@ModelAttribute("match") Match match,
                                 @RequestParam("matchTags") String matchTags,
                                 @RequestParam("teamId") Integer teamId,
                                 @RequestParam("teamTags") String teamTags) {

        if(match.getScore() == null) {
            return new ResponseEntity<>("400 - Bad Request", HttpStatus.BAD_REQUEST);
        }

        Team team = new Team();
        team.setId(teamId);
        team.setTeam(match.getTeam());
        team.setTags(new ArrayList<>(new LinkedHashSet<>(Arrays.asList(teamTags.split(", ")))));
        match.setTags(new ArrayList<>(new LinkedHashSet<>(Arrays.asList(matchTags.split(", ")))));

        if (matchService.checkForId(match.getId()))
            matchService.update(match);
        else
            matchService.create(match);
        if (teamService.checkForId(team.getId()))
            teamService.update(team);
        else
            teamService.create(team);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/s/");
        return new ResponseEntity<byte []>(null, headers, HttpStatus.FOUND);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listMatches(Model model) {
        List<Match> matches = matchService.getMatches();
        model.addAttribute("wrapper", new MatchWrapper(matches, new boolean[matches.size()]));
        return "match-list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String listMatches(@ModelAttribute("wrapper") MatchWrapper wrapper) {
        if (wrapper.getMatches() != null && wrapper.getMatches().size() > 0) {
            for (int i = 0; i < wrapper.getMatches().size(); i++) {
                if (wrapper.getDeleted()[i]) {
                    matchService.delete(wrapper.getMatches().get(i));
                }
            }
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/tags", method = RequestMethod.GET)
    public String tags(Model model) {
        List<Tag> tags = tagService.getTags();
        model.addAttribute("wrapper", new TagWrapper(tags, new boolean[tags.size()]));
        return "tags";
    }

    @RequestMapping(value = "/tags", method = RequestMethod.POST)
    public String tags(@ModelAttribute("wrapper") TagWrapper wrapper) {
        if (wrapper.getTags() != null && wrapper.getTags().size() > 0) {
            for (int i = 0; i < wrapper.getTags().size(); i++) {
                if (wrapper.getDeleted()[i]) {
                    tagService.deleteById(wrapper.getTags().get(i).getId());
                }
            }
        }
        return "redirect:/";
    }

    @RequestMapping("/export/csv")
    @ResponseBody
    public String exportCSV() {
        String directory = "export";
        String file = "matches.csv";
        String filePath = context.getContextPath() + "/" + directory + "/" + file;
        File exportDirectory = new File(context.getRealPath("/") + "/export");
        if (!exportDirectory.exists())
            exportDirectory.mkdir();

        matchService.exportCSV(exportDirectory.getAbsolutePath() + "/" + file, new ArrayList<>(matchService.getMatches()));

        return filePath;
    }

    @RequestMapping("/matchTags")
    @ResponseBody
    public String getMatchTags() {
        return  new Gson().toJson(matchService.getTags());
    }

    @RequestMapping("/teamTags")
    @ResponseBody
    public String getTeamTags() {
        return  new Gson().toJson(teamService.getTags());
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        List<Integer> teams = tagService.search(new String[]{"Hello"}, new String[]{"Foo"});
        return "";
    }
}