package com.shkubel.project.web;

import com.shkubel.project.models.OrderUser;
import com.shkubel.project.models.User;
import com.shkubel.project.repo.OrderRepository;
import com.shkubel.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class OrderController {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;

   @PostMapping("/")
    public String OrderSave(@AuthenticationPrincipal User user, @ModelAttribute("userOrder") OrderUser order, BindingResult bindingResult, Model model) throws Exception {
       if (orderService.ValidationDate(order.getLocalDateStart(),order.getLocalDateFinish())) {
           order.setUser(user);
           orderRepository.save(order);
           return "static/index";
       }
       model.addAttribute("message", "DateError!");
       return "static/index";
    }

    @GetMapping("/myprofile/orders")
    public String userOrders(Model model, @AuthenticationPrincipal User user) {
       List<OrderUser> orders = orderService.findOrderByUserId(user.getId());
       model.addAttribute("userOrder", orders);
        return "order/orders";
    }

    @PostMapping("/myprofile/orders")
    public String delete(@RequestParam (required = true, defaultValue = "") Long orderId,
                         @RequestParam (required = true, defaultValue = "") String action) {
       if (action.equals("delete")) {
           orderService.deleteOrderById(orderId);
       }
        return "redirect:order/orders";
    }

    @GetMapping("/myprofile/orders/{id}")
    public String orderDetail(@PathVariable ("id") long id, Model model) {
        OrderUser order = orderService.findOrderById(id);
        model.addAttribute("order", order);
        return "order/id";
    }

}
