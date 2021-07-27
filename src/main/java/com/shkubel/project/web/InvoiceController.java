package com.shkubel.project.web;

import com.shkubel.project.exception.OrderNotFoundException;
import com.shkubel.project.models.entity.Room;
import com.shkubel.project.models.entity.Invoice;
import com.shkubel.project.models.entity.OrderUser;
import com.shkubel.project.models.entity.Seller;
import com.shkubel.project.service.RoomService;
import com.shkubel.project.service.InvoiceService;
import com.shkubel.project.service.OrderService;
import com.shkubel.project.service.SellerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/administrator")
public class InvoiceController {


    private final OrderService orderService;
    private final SellerService sellerService;
    private final RoomService roomService;
    private final InvoiceService invoiceService;


    public InvoiceController(RoomService roomService, SellerService sellerService, OrderService orderService, InvoiceService invoiceService) {
        this.roomService = roomService;
        this.sellerService = sellerService;
        this.orderService = orderService;
        this.invoiceService = invoiceService;


    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/invoices")
    public String invoiceAll(Model model) {

        List<Invoice> invoices = invoiceService.findAll();
        if (invoices != null) {
            model.addAttribute("invoices", invoices);
        } else {
            model.addAttribute("message", "No invoices");
        }
        return "invoice/invoices";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/invoice/new")
    public String newInvoice(@RequestParam(defaultValue = "") Long orderId,
                             @RequestParam(defaultValue = "") Long offerId,
                             Model model) {

        OrderUser order = orderService.findOrderById(orderId);
        Room room = roomService.findHotelById(offerId);
        Seller sellers = sellerService.findSellerByActiveStatus();
        try {
            Invoice invoice = invoiceService.createInvoice(room, order, sellers);
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
