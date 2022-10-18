package com.example.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {
    @GetMapping("/hi")
    public String NiceToMeetYou(Model model){
        model.addAttribute("username","SoonHo");
        return "greetings"; //templates/greetings.mustach -> 브라우저로 전송
    }

    @GetMapping("/bye")
    public String SeeYouLater(Model model){
        model.addAttribute("nickname","Genius");
        return "goodbye";
    }
}
