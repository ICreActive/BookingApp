package com.shkubel.project.web;

import com.shkubel.project.exception.OrderNotFoundException;
import com.shkubel.project.exception.UserNotFoundException;
import com.shkubel.project.models.entity.Hotel;
import com.shkubel.project.models.entity.Invoice;
import com.shkubel.project.models.entity.OrderUser;
import com.shkubel.project.models.entity.Seller;
import com.shkubel.project.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/administrator")
public class InvoiceController {


    private final UserService userService;
    private final OrderService orderService;
    private final SellerService sellerService;
    private final HotelService hotelService;
    private final InvoiceService invoiceService;


    public InvoiceController(HotelService hotelService, SellerService sellerService, OrderService orderService, InvoiceService invoiceService, UserService userService) {
        this.hotelService = hotelService;
        this.sellerService = sellerService;
        this.orderService = orderService;
        this.invoiceService = invoiceService;
        this.userService = userService;

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/invoices")
    public String invoiceAll(Principal principal, Model model) {
        try {
            List<Invoice> invoices = invoiceService.findAllByUser(userService.findUserByUserName(principal.getName()));
            model.addAttribute("invoices", invoices);
        } catch (UserNotFoundException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "invoice/invoices";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/invoice/new")
    public String newInvoice(@RequestParam(defaultValue = "") Long orderId,
                             @RequestParam(defaultValue = "") Long offerId,
                             Model model) {

        OrderUser order = orderService.findOrderById(orderId);
        Hotel hotel = hotelService.findHotelById(offerId);
        Seller sellers = sellerService.findSellerByIsActive(true);
        try {
            Invoice invoice = invoiceService.createInvoice(hotel, order, sellers);
            Integer bookingPeriod = invoice.getPeriod();
            model.addAttribute("period", bookingPeriod);
            model.addAttribute("invoice", invoice);
        } catch (OrderNotFoundException e) {
            System.err.println(e);
            model.addAttribute("message", e);
            return "order/orders";
        }
        return "/invoice/new";

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/invoice/{id}")
    public String showInvoice(@PathVariable("id") Long invoicceId, Model model) {
        Invoice invoice = invoiceService.findInvoiceById(invoicceId);
        model.addAttribute("invoice", invoice);
        model.addAttribute("period", invoice.getPeriod());
        return "/invoice/new";
    }

}
