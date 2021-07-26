package com.shkubel.project.web;

import com.shkubel.project.models.entity.User;
import com.shkubel.project.service.UserService;
import com.shkubel.project.util.MailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FeedbackController {

    private final MailSender mailSender;

    private final UserService userService;

    public FeedbackController(MailSender mailSender, UserService userService) {
        this.mailSender = mailSender;
        this.userService = userService;
    }

    @GetMapping("/contact")
    public String sendMessage() {
        return "info/contact";
    }

    @PostMapping("/contact")
    public String sendMessage(@RequestParam(defaultValue = "") String name,
                              @RequestParam(defaultValue = "") String email,
                              @RequestParam(defaultValue = "") String message,
                              Model model) {
        List<String> emails = new ArrayList<>();
        List<User> admins = userService.findAdmins();
        admins.forEach(admin -> emails.add(admin.getEmail()));

        for (String admEmail : emails
        ) {
            mailSender.send(admEmail, "New message from user BookingApp", name + "\n" + message + "\nPlease answer to e-mail: " + email);
        }
        model.addAttribute("message", "Thank you for e-mail");
        return "message";
    }

}