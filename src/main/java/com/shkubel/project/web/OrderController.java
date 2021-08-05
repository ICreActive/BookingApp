package com.shkubel.project.web;

import com.shkubel.project.config.CustomAuthProvider;
import com.shkubel.project.exception.DateValidationException;
import com.shkubel.project.exception.UserNotFoundException;
import com.shkubel.project.models.entity.KlassAppartament;
import com.shkubel.project.models.entity.OrderUser;
import com.shkubel.project.models.entity.User;
import com.shkubel.project.models.oidc.CustomOidUser;
import com.shkubel.project.service.OrderService;
import com.shkubel.project.service.UserService;
import com.shkubel.project.util.ValidationUtil;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Controller
public class OrderController {


    private final OrderService orderService;
    private final ValidationUtil validationUtil;
    private final UserService userService;

    public OrderController(OrderService orderService, ValidationUtil validationUtil, UserService userService) {
        this.orderService = orderService;
        this.validationUtil = validationUtil;
        this.userService = userService;
    }

    @GetMapping("/orders/new")
    public String newOrder(@ModelAttribute("userOrder") OrderUser order, Model model) {
        model.addAttribute("klassAp", KlassAppartament.values());
        return "order/new";
    }

    @PostMapping("/orders/new")
    public String orderSave(Principal principal, @ModelAttribute("userOrder") OrderUser order, BindingResult bindingResult, Model model) {
        try {
            validationUtil.validationDate(order.getLocalDateStart(), order.getLocalDateFinish());
            User user = userService.findUserByUserName(principal.getName());
            order.setUser(user);
            orderService.create(order);
            return "redirect:/home";
        } catch (UserNotFoundException | DateValidationException e) {
            model.addAttribute("message", e.getMessage());
            model.addAttribute("klassAp", KlassAppartament.values());
            return "order/new";
        }
    }


    @GetMapping("/myprofile/orders")
    public String userOrders(Model model, Principal principal) {
        User user;
        try {
            user = userService.findUserByUserName(principal.getName());
            List<OrderUser> orders = orderService.findOrderUsersByUserId(user.getId());
            model.addAttribute("userOrder", orders);

        } catch (UserNotFoundException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "order/orders";
    }

    @PostMapping("/myprofile/orders")
    public String delete(@RequestParam(defaultValue = "") Long orderId,
                         @RequestParam(defaultValue = "") String action) {
        if (action.equals("delete")) {
            orderService.deleteOrderById(orderId);
        }
        return "redirect:order/orders";
    }

    @GetMapping("/myprofile/orders/{id}")
    public String orderDetail(@PathVariable("id") Long id, Model model) {
        OrderUser order = orderService.findOrderById(id);
        model.addAttribute("order", order);
        return "order/id";
    }


}
