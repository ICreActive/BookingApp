package com.shkubel.project.web;

import com.shkubel.project.models.entity.User;
import com.shkubel.project.service.impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;


    public UserController(UserServiceImpl userService) {
        this.userService = userService;

    }

    @GetMapping("/myprofile")
    public String getProfile(Model model, Principal principal) {
        model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
        return "users/myprofile";
    }

    @GetMapping("/myprofile/{id}/edit")
    public String userEdit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "users/edit";
    }

    @PostMapping("/myprofile/{id}/edit")
    public String userUpd(@ModelAttribute("user") @Valid User user,
                          BindingResult bindingResult, @PathVariable("id") Long id, Model model) {
        final String s = "users/edit";
        if (bindingResult.hasErrors()) {
            return s;
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            model.addAttribute("passwordError", "The passwords don't match");
            return s;
        }
        if (!userService.updateUser(id, user)) {
            model.addAttribute("usernameError", "This username or e-mail already exists");
            return s;
        }
        userService.updateUser(id, user);
        return "redirect:/users/myprofile";
    }

}
