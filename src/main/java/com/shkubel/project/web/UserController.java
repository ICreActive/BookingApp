package com.shkubel.project.web;

import com.shkubel.project.mapper.UserMapper;
import com.shkubel.project.models.entity.User;
import com.shkubel.project.service.impl.UserServiceImpl;
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
    private UserServiceImpl userService;

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

        return "redirect:/";
    }
    @GetMapping ("/myprofile")
    public String getProfile (Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", UserMapper.INSTANCE.toDTO(user));
        return "users/myprofile";
    }

    @GetMapping("/myprofile/{id}/edit")
    public String userEdit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "users/edit";
    }

    @PostMapping("/myprofile/{id}/edit")
    public String userUpd(@ModelAttribute("user") @Valid User user,
                          BindingResult bindingResult, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            return "users/edit";
        }
        userService.updateUser(id, user);
        return "redirect:/myprofile";
    }


}
