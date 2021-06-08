package com.shkubel.project.web;

import com.shkubel.project.models.entity.Hotel;
import com.shkubel.project.models.entity.OrderUser;
import com.shkubel.project.models.entity.Seller;
import com.shkubel.project.models.entity.User;
import com.shkubel.project.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/administrator")
public class AdminController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private OrderServiceImpl orderServiceImpl;
    @Autowired
    private SellerServiceImpl sellerServiceImpl;
    @Autowired
    private HotelServiceImpl hotelService;


    @GetMapping("/users")
    public String users(Model model) {
        List<User> users = userService.allUsers();
        model.addAttribute("users", users);
        return "users/users";
    }

    @GetMapping("users/profile/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
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
        return "redirect:users";
    }

    @GetMapping("/orders")
    public String showOrds(Model model) {
        Iterable <OrderUser> orders = orderServiceImpl.allOrders();
        model.addAttribute("userOrder", orders);
        return "order/orders";
    }

    @PostMapping("/orders")
    public String deleteOrder(@RequestParam(required = true, defaultValue = "") Long orderId,
                              @RequestParam(required = true, defaultValue = "") String action,
                              Model model) {
        if (action.equals("delete")) {
            orderServiceImpl.deleteOrderById(orderId);
        }
        return "redirect:orders";
    }

    @GetMapping("/orders/{id}")
    public String showOrder(@PathVariable ("id") Long id, Model model ) {
        OrderUser order= orderServiceImpl.findOrderById(id);
        List <Hotel> hotels = hotelService.findOffers(order);
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
        OrderUser order = orderServiceImpl.findOrderById(orderId);
        Hotel hotel = hotelService.findHotelById(offerId);
        List <Seller> sellers = sellerServiceImpl.findAllSeller();
        Integer bookingPeriod = orderServiceImpl.bookingPeriod(order);
        model.addAttribute("order", order);
        model.addAttribute("hotel", hotel);
        model.addAttribute("sellers", sellers);
        model.addAttribute("period",bookingPeriod);

        return "/invoice/new";
    }

}
