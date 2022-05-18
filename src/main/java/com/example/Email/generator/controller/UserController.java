package com.example.Email.generator.controller;

import com.example.Email.generator.entity.User;
import com.example.Email.generator.repository.UserRepo;
import com.example.Email.generator.service.MailSender;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MailSender mailSender;

    @GetMapping("/user/add")
    public String userAdd(Model model) {
        return "/user/user-add";
    }

    @PostMapping("/user/add")
    public String userSave(@RequestParam String username, @RequestParam String email, Model model) {
        User user = new User(username, email, new Timestamp(System.currentTimeMillis()));
        try {
            userRepo.save(user);
        } catch (Exception e) {
            model.addAttribute("if", 1);
            model.addAttribute("message", "This user is already in the database");
            return "/user/user-add";
        }
        return "redirect:/user";
    }


    @GetMapping("/search-user")
    public String userSearch(
        @RequestParam String param,
        @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
        @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
        Model model)
    {
        Iterable<User> allUsers = userRepo.findAll();
        List<User> res = new ArrayList<>();
        allUsers.forEach(res::add);
        for (User el : res) {
            if (el.getEmail().equals(param) ^ el.getUsername().equals(param)) {
                model.addAttribute("personsPage", el);
                return "/user/user";
            }
        }
        model.addAttribute("ifSearch", 1);
        model.addAttribute("userNotSearch", param + ": Nothing found");
        return userMain(page,size,model);
    }

    @GetMapping("/user/{id}/edit")
    public String userEdit(@PathVariable(value = "id") Integer id, Model model) {
        if (!userRepo.existsById(id)) {
            return "redirect:/user";
        }
        Optional<User> user = userRepo.findById(id);
        ArrayList<User> result = new ArrayList<>();
        user.ifPresent(result::add);
        model.addAttribute("editUser", result);
        return "/user/user-edit";
    }

    @PostMapping("/user/{id}/edit")
    public String userUpdate(@PathVariable(value = "id") Integer id, @RequestParam String username,
        @RequestParam String email, Model model) {
        User user = userRepo.findById(id).orElseThrow();
        user.setUsername(username);
        user.setEmail(email);
        userRepo.save(user);
        return "redirect:/user";
    }

    @GetMapping("/user/{id}/delete")
    public String userDelete(@PathVariable(value = "id") Integer id, Model model) {
        if (!userRepo.existsById(id)) {
            return "redirect:/user";
        }
        userRepo.deleteById(id);
        return "redirect:/user";
    }

    @GetMapping("/user/{id}/email")
    public String sendMail(
        @PathVariable(value = "id") Integer id,
        @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
        @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
        Model model)
    {
        if (!userRepo.existsById(id)) {
            return "redirect:/user";
        }
        User user = userRepo.findById(id).orElseThrow();
        mailSender.send(user);
        model.addAttribute("ifSearch", 1);
        model.addAttribute("userNotSearch", "Message sent");
        return userMain(page,size,model);
    }

    @GetMapping("/user")
    public String userMain(
        @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
        @RequestParam(value = "size", required = false, defaultValue = "5") Integer size,
        Model model)
    {
        Page<User> users = userRepo.findAll(
            PageRequest.of(page, size, Direction.DESC, "createdOn"));

        model.addAttribute("ifPage", 1);
        model.addAttribute("personsPage", users);
        model.addAttribute("numbers", IntStream.range(0, users.getTotalPages()).toArray());
        return "/user/user";
    }

}
