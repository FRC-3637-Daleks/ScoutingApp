package com.team3637.controller.match;

import com.google.gson.Gson;
import com.team3637.model.Match;
import com.team3637.model.Tag;
import com.team3637.model.Team;
import com.team3637.service.MatchService;
import com.team3637.service.TagService;
import com.team3637.service.TeamService;
import com.team3637.wrapper.MatchWrapper;
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
        return "redirect:" + context.getContextPath() + "/";
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
                                 @RequestParam("teamTags") String teamTags) {

        if(match.getScore() == null) {
            return new ResponseEntity<>("400 - Bad Request", HttpStatus.BAD_REQUEST);
        }

        Team team = new Team();
        team.setTeam(match.getTeam());
        team.setTags(new ArrayList<>(new LinkedHashSet<>(Arrays.asList(teamTags.split(", ")))));
        match.setTags(new ArrayList<>(new LinkedHashSet<>(Arrays.asList(matchTags.split(", ")))));

        if(team.getTags().size() > 50 || team.getTags().size() < 1 ||
                match.getTags().size() > 50 || match.getTags().size() < 1)
            return new ResponseEntity<>("400 - Bad Request", HttpStatus.BAD_REQUEST);

        if (teamService.checkForTeam(team.getTeam()))
            teamService.update(team);
        else
            teamService.create(team);
        if (matchService.checkForMatch(match.getMatchNum(), match.getTeam()))
            matchService.update(match);
        else
            matchService.create(match);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", context.getContextPath() + "/s/");
        return new ResponseEntity<byte[]>(null, headers, HttpStatus.FOUND);
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

    @RequestMapping(value = "/tags", method = RequestMethod.POST)
    public String tags(@RequestParam("matchTags") String matchTagsString,
                       @RequestParam("teamTags") String teamTagsString) {
        List<String> matchTags = new ArrayList<>(new LinkedHashSet<>(Arrays.asList(matchTagsString.split(", "))));
        List<String> teamTags = new ArrayList<>(new LinkedHashSet<>(Arrays.asList(teamTagsString.split(", "))));
        List<String> currentMatchTags = matchService.getTags();
        List<String> currentTeamTags = teamService.getTags();
        boolean dne;
        for (String matchTag : matchTags) {
            if (!tagService.checkForTag(new Tag(matchTag, "matches"))) {
                tagService.create(new Tag(matchTag, "matches"));
            }
        }
        for (String currentMatchTag : currentMatchTags) {
            dne = true;
            for (String matchTag : matchTags) {
                if (currentMatchTag.equals(matchTag)) {
                    dne = false;
                }
            }
            if (dne) {
                tagService.delete(currentMatchTag);
            }
        }
        for (String teamTag : teamTags) {
            if (!tagService.checkForTag(new Tag(teamTag, "teams"))) {
                tagService.create(new Tag(teamTag, "teams"));
            }
        }
        for (String currentTeamTag : currentTeamTags) {
            dne = true;
            for (String teamTag : teamTags) {
                if (currentTeamTag.equals(teamTag)) {
                    dne = false;
                }
            }
            if (dne) {
                tagService.delete(currentTeamTag);
            }
        }
        return "redirect:" + context.getContextPath() + "/m/tags";
    }

    @RequestMapping(value = "/tags/mergeMatch", method = RequestMethod.GET)
    public String mergeMatchTags(Model model) {
        List<String> matchTags = matchService.getTags();
        model.addAttribute("matchTags", matchTags);
        return "merge-match-tags";
    }

    @RequestMapping(value = "/tags/mergeMatch", method = RequestMethod.POST)
    public String mergeMatchTags(@RequestParam("oldTag") String oldTag,
                            @RequestParam("newTag") String newTag) {
        tagService.mergeTags(new Tag(oldTag, "matches"), new Tag(newTag, "matches"));
        return "redirect:" + context.getContextPath() + "/m/tags";
    }

    @RequestMapping(value = "/tags/mergeTeam", method = RequestMethod.GET)
    public String mergeTeamTags(Model model) {
        List<String> teamTags = teamService.getTags();
        model.addAttribute("teamTags", teamTags);
        return "merge-team-tags";
    }

    @RequestMapping(value = "/tags/mergeTeam", method = RequestMethod.POST)
    public String mergeTeamTags(@RequestParam("oldTag") String oldTag,
                                 @RequestParam("newTag") String newTag) {
        tagService.mergeTags(new Tag(oldTag, "teams"), new Tag(newTag, "teams"));
        return "redirect:" + context.getContextPath() +  "/m/tags";
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
        return  new Gson().toJson(matchService.getTags());
    }

    @RequestMapping("/teamTags")
    @ResponseBody
    public String getTeamTags() {
        return  new Gson().toJson(teamService.getTags());
    }
}