package com.shkubel.project.web;

import com.shkubel.project.exception.UserNotFoundException;
import com.shkubel.project.models.entity.Hotel;
import com.shkubel.project.models.entity.OrderUser;
import com.shkubel.project.models.entity.Seller;
import com.shkubel.project.models.entity.User;
import com.shkubel.project.service.HotelService;
import com.shkubel.project.service.OrderService;
import com.shkubel.project.service.SellerService;
import com.shkubel.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/administrator")
public class AdminController {

    @Autowired
    private UserService userService;
    private final OrderService orderService;
    private final SellerService sellerService;
    private final HotelService hotelService;

    public AdminController(HotelService hotelService, SellerService sellerService, OrderService orderService) {
        this.hotelService = hotelService;
        this.sellerService = sellerService;
        this.orderService = orderService;
    }


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
        } else if (action.equals("restore")) {
            try {
            userService.restoreUser(userId);
            } catch (UserNotFoundException e) {
                System.err.println(e);
                model.addAttribute("message", e);
                return "users/users";
            }

        }
        return "redirect:users";
    }

    @GetMapping("/orders")
    public String showOrds(Model model) {
        List<OrderUser> orders = orderService.findOrderUserByStatus(true);
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
        return "redirect:orders";
    }

    @PostMapping("/orders/all")
    public String delOrder(@RequestParam(required = true, defaultValue = "") Long orderId,
                              @RequestParam(required = true, defaultValue = "") String action,
                              Model model) {
        if (action.equals("delete")) {
            orderService.deleteOrderById(orderId);
        }
        return "redirect:orders";
    }

    @GetMapping("/orders/{id}")
    public String showOrder(@PathVariable("id") Long id, Model model) {
        OrderUser order = orderService.findOrderById(id);
        List<Hotel> hotels = hotelService.findOffers(order);
        if (hotels==null) {
            List<Hotel> hotel = new ArrayList<>();
            model.addAttribute("order", order);
            model.addAttribute("error", "Offer not found");
            model.addAttribute("offers", hotel);
            return "order/id";
        }
        model.addAttribute("order", order);
        model.addAttribute("offers", hotels);
        return "order/id";
    }

    @GetMapping("/users/active")
    public String usersActive(Model model) {
        model.addAttribute("users", userService.findUsersByStatus(true));
        return "users/users";
    }

    @GetMapping("/orders/all")
    public String ordersActive(Model model) {
        model.addAttribute("userOrder", orderService.allOrders());
        return "order/orders";
    }

    @GetMapping("/sellers")
    public String sellers(Model model) {
        List<Seller> sellers = sellerService.findAllSeller();
        model.addAttribute("sellers", sellers);
        return "seller/seller";
    }


    @PostMapping("/sellers")
    public String setSeller(@RequestParam(name = "check", defaultValue = "") String param, Model model) {
        Long id = Long.parseLong(param);
        Seller seller = sellerService.findSellerById(id);
        seller.setActive(true);
        sellerService.saveSeller(seller);
        List<Seller> sellers = sellerService.findAllSeller();
        model.addAttribute("sellers", sellers);
        return "seller/seller";
    }

    @GetMapping("/sellers/{id}")
    public String sellerShow(@PathVariable("id") Long id, Model model) {
        model.addAttribute("seller", sellerService.findSellerById(id));
        return "seller/id";
    }

    @GetMapping("/sellers/{id}/edit")
    public String sellerEdit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("seller", sellerService.findSellerById(id));
        return "seller/edit";
    }

    @PostMapping("/sellers/{id}/edit")
    public String sellerUpd(@ModelAttribute("seller") @Valid Seller seller,
                            BindingResult bindingResult, @PathVariable("id") Long id, Model model) {
        final String s = "seller/edit";
        if (bindingResult.hasErrors()) {
            return s;
        }
        sellerService.update(id, seller);
        return "redirect:/users/myprofile";
    }

    @GetMapping("/sellers/new")
    public String sellerNew(Model model) {
        Seller seller = new Seller();
        model.addAttribute("sellerNew", seller);
        return "seller/new";
    }

    @PostMapping("/sellers/new")
    public String saveSeller(@ModelAttribute("sellerNew") @Valid Seller seller, BindingResult bindingResult, Model model) {
        final String s = "/seller/new";
        if (bindingResult.hasErrors()) {
            return s;
        }
        if (sellerService.saveSeller(seller)) {
            return "redirect:/";
        }
        model.addAttribute("error", "The seller was not saved");
        return s;
    }
}
