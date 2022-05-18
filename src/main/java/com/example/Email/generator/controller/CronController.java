package com.example.Email.generator.controller;

import com.example.Email.generator.entity.Cron;
import com.example.Email.generator.repository.CronRepository;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.IntStream;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CronController {

    @Autowired
    private CronRepository cronRepository;

    @PostMapping("/cron")
    public String cronSave(@RequestParam String expression, Model model) throws SchedulerException {
        if (!CronExpression.isValidExpression(expression)) {
            model.addAttribute("ifCron", 1);
            model.addAttribute("messageCron", "Incorrect cron format");
            return cronMain(0,5,model);
        }
        if (!cronRepository.findByExpression(expression).isEmpty()) {
            model.addAttribute("ifCron", 1);
            model.addAttribute("messageCron", "Cron is already in the database");
            return cronMain(0,5,model);
        }
        Cron cron = new Cron(expression, new Timestamp(System.currentTimeMillis()));
        cronRepository.save(cron);
        return "redirect:/cron";
    }

    @GetMapping("/cron/{id}/delete")
    public String userDelete(@PathVariable(value = "id") Integer id, Model model) {
        if (!cronRepository.existsById(id)) {
            return "redirect:/cron";
        }
        cronRepository.deleteById(id);
        return "redirect:/cron";
    }

    @GetMapping("/cron/{id}/edit")
    public String cronEdit(@PathVariable(value = "id") Integer id, Model model) {
        if (!cronRepository.existsById(id)) {
            return "redirect:/user";
        }
        Optional<Cron> cron = cronRepository.findById(id);
        ArrayList<Cron> result = new ArrayList<>();
        cron.ifPresent(result::add);
        model.addAttribute("editCron", result);
        return "/cron/cron-edit";
    }

    @PostMapping("/cron/{id}/edit")
    public String cronUpdate(@PathVariable(value = "id") Integer id,
        @RequestParam String expression, Model model) {
        if (!cronRepository.findByExpression(expression).isEmpty()) {
            model.addAttribute("ifCron", 1);
            model.addAttribute("messageCron", "Cron is already in the database");
            return cronEdit(id, model);
        }
        Cron cron = cronRepository.findById(id).orElseThrow();
        cron.setExpression(expression);
        cronRepository.save(cron);
        return "redirect:/cron";
    }

    @GetMapping("/cron")
    public String cronMain(
        @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
        @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
        Model model) {
        Page<Cron> crons = cronRepository.findAll(PageRequest.of(page, size));

        model.addAttribute("ifPage", 1);
        model.addAttribute("cronPage", crons);
        model.addAttribute("numbers", IntStream.range(0, crons.getTotalPages()).toArray());
        return "/cron";
    }

}
