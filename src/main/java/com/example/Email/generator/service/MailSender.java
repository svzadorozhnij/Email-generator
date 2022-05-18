package com.example.Email.generator.service;

import com.example.Email.generator.controller.LogController;
import com.example.Email.generator.entity.User;
import com.example.Email.generator.introductory.TypeMail;
import com.example.Email.generator.repository.UserRepo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSender {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    LogController logController;
    @Autowired
    UserRepo userRepo;
    @Value("${spring.mail.username}")
    private String username;

    /**
     * Sends an email to the user.
     * The text of the letter is set in the method.
     * After sending, the information is recorded in the log database
     * @param user
     */
    public void send(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Вітання!");
        mailMessage.setText("Ім'я користувача:" + user.getUsername() + "\n"
                            + "Дата та час створення:" + user.getCreatedOn());
        mailSender.send(mailMessage);

        logController.saveLog(user.getId(), TypeMail.rest);
    }


    public void sendAll() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);

        Iterable<User> users = userRepo.findAll();
        List<User> result = new ArrayList<>();
        users.forEach(result::add);

        for (User user : result) {
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Вітання!");
            mailMessage.setText("Ім'я користувача: " + user.getUsername() + "\n"
                + "Дата та час створення: " + user.getCreatedOn());
            mailSender.send(mailMessage);
            logController.saveLog(user.getId(),TypeMail.cron);
        }
    }
}
