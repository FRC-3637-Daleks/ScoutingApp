package com.team3637.matchController;

import com.team3637.model.Match;
import com.team3637.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MatchController {

    @Autowired
    private MatchService matchService;

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
    public String submitNewMatch(@ModelAttribute("match") Match match, BindingResult bindingResult) {
        if (matchService.checkForId(match.getId()))
            matchService.update(match);
        else
            matchService.create(match);

        return "redirect:/s/";
    }
}