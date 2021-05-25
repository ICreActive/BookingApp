package com.shkubel.project.web;

import com.shkubel.project.models.*;
import com.shkubel.project.service.BookingService;
import com.shkubel.project.service.OrderService;
import com.shkubel.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Controller
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private BookingService bookingService;


    @GetMapping("/users")
    public String users(Model model) {
        List<User> users = userService.allUsers();
        model.addAttribute("users", users);
        return "users/users";
    }

    @GetMapping("users/administrator")
    public String adminPage() {
        return "/users/administrator";
    }

    @GetMapping("users/profile/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "users/profile-id";
    }

    @PostMapping("/users")
    public String deleteUser(@RequestParam(required = true, defaultValue = "") Long userId,
                             @RequestParam(required = true, defaultValue = "") String action,
                             Model model) {
        if (action.equals("delete")) {
            userService.deleteUser(userId);
        }
        return "redirect:/users";
    }

    @GetMapping("/orders")
    public String showOrds(Model model) {
        Iterable <OrderUser> orders = orderService.allOrders();
        model.addAttribute("userOrder", orders);
        return "order/orders";
    }

    @PostMapping("/orders")
    public String deleteOrder(@RequestParam(required = true, defaultValue = "") Long orderId,
                              @RequestParam(required = true, defaultValue = "") String action,
                              Model model) {
        if (action.equals("delete")) {
            orderService.deleteOrderById(orderId);
        }
        return "redirect:order/orders";
    }

    @GetMapping("/orders/{id}")
    public String showOrder(@PathVariable ("id") Long id, Model model ) {
        OrderUser order= orderService.findOrderById(id);
        List <Hotel> hotels = bookingService.findOffers(order);
        model.addAttribute("order", order);
        model.addAttribute("offers", hotels);

        return "order/id";
    }


    @PostMapping("/invoice")
    public String invoiceAll(Model model) {

              return "/invoice/invoice";
    }

    @PostMapping("/invoice/new")
    public String newInvoice(@RequestParam (defaultValue = "", required = true) Long orderId,
                             @RequestParam (defaultValue = "", required = true) Long offerId,
                             Model model) {
        OrderUser order = orderService.findOrderById(orderId);
        Hotel hotel = bookingService.findHotelById(offerId);
        List <Seller> sellers = bookingService.findAllSeller();
        model.addAttribute("order", order);
        model.addAttribute("hotel", hotel);
        model.addAttribute("sellers", sellers);

        return "/invoice/new";
    }

}
