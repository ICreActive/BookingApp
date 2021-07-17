package com.shkubel.project.web;

import com.shkubel.project.models.entity.User;
import com.shkubel.project.service.impl.UserServiceImpl;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

public class RegistrationController {

    private final UserServiceImpl userService;


    public RegistrationController(UserServiceImpl userService) {
        this.userService = userService;

    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("userNew") User user) {
        return "users/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("userNew") @Valid User user, BindingResult bindingResult, Model model) {
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

        return "redirect:/";
    }

    @GetMapping("/activate/{code}")
    public String activate (Model model, @PathVariable("code") String code) {
        boolean isActivated = userService.activateUser(code);

        if(isActivated) {
            model.addAttribute("message","User successfully activated");
        } else {
            model.addAttribute("message","Activation code is not found");
        }
        return "login";
    }
}
