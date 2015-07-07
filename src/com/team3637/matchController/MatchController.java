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

@Controller
public class MatchController {

    @Autowired
    private MatchService matchService;

    @RequestMapping("/")
    public String index() {
        return "redirect:/";
    }

    @ModelAttribute("match")
    public Match getMatch() {
        return new Match();
    }

    @RequestMapping(value = "/add.jsp", method = RequestMethod.GET)
    public String newMatch(@RequestParam("teamNum") Integer teamNum,
                           @RequestParam("matchNum") Integer matchNum,
                           Model model) {

        model.addAttribute("teamNum", teamNum);
        model.addAttribute("matchNum", matchNum);

        return "match-add";
    }

    @RequestMapping(value = "/add.jsp", method = RequestMethod.POST)
    public String submitNewMatch(@ModelAttribute("match") Match match, BindingResult bindingResult) {

        matchService.create(match);

        return "redirect:/s/";
    }
}