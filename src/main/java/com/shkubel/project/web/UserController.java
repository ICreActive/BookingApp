package com.shkubel.project.web;

import com.shkubel.project.models.User;
import com.shkubel.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/new")
    public String newUser(@ModelAttribute("userNew") User user) {
        return "users/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("userNew") @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/users/new";
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            model.addAttribute("passwordError", "The passwords don't match");
            return "/users/new";
        }
        if (!userService.saveUser(user)) {
            model.addAttribute("usernameError", "This username already exists. ");
            return "/users/new";
        } 
        userService.saveUser(user);

        return "redirect:/";
    }
    @GetMapping ("/myprofile")
    public String getProfile (Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("userId", user.getId());
        model.addAttribute("userName", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "users/myprofile";
    }

    @GetMapping("/myprofile/{id}/edit")
    public String userEdit(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "users/edit";
    }

    @PostMapping("/myprofile/{id}/edit")
    public String userUpd(@ModelAttribute("user") @Valid User user,
                          BindingResult bindingResult, @PathVariable("id") long id) {
        if (bindingResult.hasErrors()) {
            return "users/edit";
        }
        userService.updateUser(id, user);
        return "redirect:/myprofile";
    }


}
