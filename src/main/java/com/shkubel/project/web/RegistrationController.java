package com.shkubel.project.web;

import com.shkubel.project.exception.UserNotFoundException;
import com.shkubel.project.models.entity.User;
import com.shkubel.project.service.UserService;
import com.shkubel.project.util.MailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class RegistrationController {

    private final UserService userService;
    private final MailSender mailSender;

    public RegistrationController(UserService userService, MailSender mailSender) {
        this.userService = userService;

        this.mailSender = mailSender;
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("userNew") User user) {
        return "users/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("userNew") @Valid User user, BindingResult bindingResult, HttpServletRequest request, Model model) {
        final String s = "/users/new";
        if (bindingResult.hasErrors()) {
            return s;
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            model.addAttribute("passwordError", "The passwords don't match");
            return s;
        }
        if (!userService.saveUser(user)) {
            model.addAttribute("usernameError", "This username or e-mail already exists");
            return s;
        }
        mailSender.sendActivationMessage(user, request);

        return "redirect:/";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable("code") String code) {

        try {
            boolean isActivated = userService.activateUser(code);
            if (isActivated) {
                model.addAttribute("message", "User successfully activated");
            } else {
                model.addAttribute("message", "Activation code is not found");
            }
            return "login";

        } catch (UserNotFoundException e) {
            model.addAttribute("message", e.getMessage() + " Please, try again");
            return "login";
        }
    }
}
