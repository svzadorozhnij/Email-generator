package com.example.Email.generator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("/")
    public String user(Model model) {
        model.addAttribute("title", "Working with users");
        return "user";
    }

    @GetMapping("/email")
    public String email(Model model) {
        model.addAttribute("title", "Work with email");
        return "email";
    }

    @GetMapping("/log")
    public String log(Model model) {
        model.addAttribute("title", "Logbook");
        return "log";
    }

}
