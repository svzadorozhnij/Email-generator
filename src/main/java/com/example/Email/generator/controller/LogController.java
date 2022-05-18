package com.example.Email.generator.controller;

import com.example.Email.generator.entity.Log;
import com.example.Email.generator.repository.LogRepository;
import com.example.Email.generator.repository.UserRepo;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogController {

    @Autowired
    private LogRepository logRepository;
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/log")
    public String logMain(Model model) {
        List<Log> allLog = logRepository.findAll();
        model.addAttribute("allLog", allLog);
        return "/log";
    }

    public void saveLog(int user_id, Enum type) {
        Log log = new Log(user_id, type, new Timestamp(System.currentTimeMillis()));
        logRepository.save(log);
    }

}
