package com.shkubel.project.web;

import com.shkubel.project.models.KlassAppartament;
import com.shkubel.project.models.OrderUser;
import com.shkubel.project.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


@Controller
public class MainPageController {


    @GetMapping("/")
    public String newOrder(@ModelAttribute("userOrder") OrderUser order, Model model) {
        model.addAttribute("klassAp", KlassAppartament.values());
        return "static/index";
    }

}
