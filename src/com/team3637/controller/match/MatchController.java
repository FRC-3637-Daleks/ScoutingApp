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
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
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

        if (teamService.checkForId(team.getId()))
            teamService.update(team);
        else
            teamService.create(team);
        if (matchService.checkForId(match.getId()))
            matchService.update(match);
        else
            matchService.create(match);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/s/");
        return new ResponseEntity<byte []>(null, headers, HttpStatus.FOUND);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listMatches(Model model) {
        List<Match> matches = matchService.getMatches();
        model.addAttribute("matchWrapper", new MatchWrapper(matches, new boolean[matches.size()]));
        return "match-list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String listMatches(@ModelAttribute("matchWrapper") MatchWrapper wrapper) {
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
        model.addAttribute("tagWrapper", new TagWrapper(tags, new boolean[tags.size()]));
        return "tags";
    }

    @RequestMapping(value = "/tags", method = RequestMethod.POST)
    public String tags(@ModelAttribute("tagWrapper") TagWrapper wrapper) {
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
    public String exportCSV() throws IOException {
        String file = "matches.csv";
        File exportDirectory = new File(context.getRealPath("/") + "/export");
        if (!exportDirectory.exists())
            exportDirectory.mkdir();
        String filePath = exportDirectory.getAbsolutePath() + "/" + file;
        matchService.exportCSV(filePath, new ArrayList<>(matchService.getMatches()));
        return new String(Files.readAllBytes(FileSystems.getDefault().getPath(filePath)));
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
}