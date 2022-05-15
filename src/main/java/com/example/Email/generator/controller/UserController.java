package com.example.Email.generator.controller;

import com.example.Email.generator.entity.User;
import com.example.Email.generator.repository.UserRepo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/user")
    public String userMain(Model model) {
        Iterable<User> allUsers = userRepo.findAll();
        List<User> result = new ArrayList<>();
        allUsers.forEach(result::add);
        model.addAttribute("allUsers", result);
        return "/user/user";
    }

    @GetMapping("/user/add")
    public String userAdd(Model model) {
        return "/user/user-add";
    }

    @PostMapping("/user/add")
    public String userSave(@RequestParam String username, @RequestParam String email, Model model) {
        User user = new User(username, email, new Date());
        userRepo.save(user);
        return "redirect:/user";
    }

    @GetMapping("/search-user")
    public String userSearch(@RequestParam String username, Model model) {
        Iterable<User> allUsers = userRepo.findAll();
        List<User> res = new ArrayList<>();
        allUsers.forEach(res::add);
        if (!username.isEmpty()) {
            User user = null;
            for (User el : res) {
                if (el.getEmail().equalsIgnoreCase(username)) {
                    user = el;
                }
            }
            model.addAttribute("allUsers", user);
        } else {
            model.addAttribute("allUsers", allUsers);
        }
        return "/user/user";
    }

    @GetMapping("/user/search/email")
    public String userSearchEmail(@RequestParam String email, Model model) {
        Iterable<User> allUsers = userRepo.findAll();
        List<User> res = new ArrayList<>();
        allUsers.forEach(res::add);
        if (!email.isEmpty()) {
            User user = null;
            for (User el : res) {
                if (el.getEmail().equalsIgnoreCase(email)) {
                    user = el;
                }
            }
            model.addAttribute("allUsers", user);
        } else {
            model.addAttribute("allUsers", allUsers);
        }
        return "/user/user";
    }

    @GetMapping("/user/{id}/edit")
    public String userEdit(@PathVariable(value = "id") Integer id, Model model) {
        if (!userRepo.existsById(id)) {
            return "redirect:/user";
        }
        Optional<User> user = userRepo.findById(id);
        ArrayList<User> result = new ArrayList<>();
        user.ifPresent(result::add);
        model.addAttribute("editUser",result);
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

}
