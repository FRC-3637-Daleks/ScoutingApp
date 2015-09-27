package com.team3637.matchController;

import com.team3637.model.Match;
import com.team3637.service.MatchService;
import com.team3637.wrapper.MatchWrapper;
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
public class MatchController {

    @Autowired
    private MatchService matchService;
    @Autowired
    private ServletContext context;

    @PostConstruct
    public void init() {
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "";
    }

    @RequestMapping("/")
    public String index() {
        return "redirect:/";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String newMatch(@RequestParam("teamNum") Integer teamNum,
                           @RequestParam("matchNum") Integer matchNum,
                           Model model) {

        List<Match> matches = matchService.getForMatchAndTeam(matchNum, teamNum);
        Match match;
        if (matches.size() == 0) {
            match = new Match();
        } else {
            match = matches.get(0);
        }
        model.addAttribute("match", match);
        model.addAttribute("teamNum", teamNum);
        model.addAttribute("matchNum", matchNum);

        return "match";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String submitNewMatch(@ModelAttribute("match") Match match) {
        if (matchService.checkForId(match.getId()))
            matchService.update(match);
        else
            matchService.create(match);

        return "redirect:/s/";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listMatches(Model model) {
        List<Match> matches = matchService.getMatches();
        model.addAttribute("wrapper", new MatchWrapper(matches, new boolean[matches.size()]));
        return "match-list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String submitListMatches(@ModelAttribute("wrapper") MatchWrapper wrapper) {
        if (wrapper.getMatches() != null && wrapper.getMatches().size() > 0) {
            for (int i = 0; i < wrapper.getMatches().size(); i++) {
                if (wrapper.getDeleted()[i]) {
                    matchService.delete(wrapper.getMatches().get(i).getId());
                }
            }
        }
        return "redirect:/s/";
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
}