package com.shkubel.project.web;

import com.lowagie.text.DocumentException;
import com.shkubel.project.models.entity.*;
import com.shkubel.project.service.PdfService;
import com.shkubel.project.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;


@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/administrator")
public class InvoiceController {

    private final UserServiceImpl userService;
    private final OrderServiceImpl orderServiceImpl;
    private final SellerServiceImpl sellerServiceImpl;
    private final HotelServiceImpl hotelService;
    private final InvoiceServiceImpl invoiceService;
    private final PdfService pdfService;


    public InvoiceController(HotelServiceImpl hotelService, SellerServiceImpl sellerServiceImpl, OrderServiceImpl orderServiceImpl, InvoiceServiceImpl invoiceService, UserServiceImpl userService, PdfService pdfService) {
        this.hotelService = hotelService;
        this.sellerServiceImpl = sellerServiceImpl;
        this.orderServiceImpl = orderServiceImpl;
        this.invoiceService = invoiceService;
        this.userService = userService;
        this.pdfService = pdfService;
    }

    @GetMapping("/invoices")
    public String invoiceAll(Principal principal, Model model) {
        List<Invoice> invoices = invoiceService.findAllByUser((User) userService.loadUserByUsername(principal.getName()));
        model.addAttribute("invoices", invoices);
        return "invoice/invoices";
    }

    @PostMapping("/invoice/new")
    public String newInvoice(@RequestParam(defaultValue = "", required = true) Long orderId,
                             @RequestParam(defaultValue = "", required = true) Long offerId,
                             Model model) {

        OrderUser order = orderServiceImpl.findOrderById(orderId);
        Hotel hotel = hotelService.findHotelById(offerId);
        Seller sellers = sellerServiceImpl.findSellerByIsActive(true);
        Integer bookingPeriod = orderServiceImpl.bookingPeriod(order);
        Invoice invoice = invoiceService.createInvoice(hotel, order, sellers);
        model.addAttribute("period", bookingPeriod);
        model.addAttribute("invoice", invoice);


        return "/invoice/new";
    }

    @GetMapping("/invoice/{id}")
    public String showInvoice(@PathVariable("id") Long invoicceId, Model model) {
        Invoice invoice = invoiceService.findInvoiceById(invoicceId);
        model.addAttribute("invoice", invoice);
        Integer bookingPeriod = orderServiceImpl.bookingPeriod(invoice.getOrderUser());
        model.addAttribute("period", bookingPeriod);
        return "/invoice/new";
    }

    @GetMapping("/download-pdf")
    public void downloadPDFResource(HttpServletResponse response) {
        try {
             Path file = Paths.get(pdfService.generatePdf().getAbsolutePath());
            if (Files.exists(file)) {
                response.setContentType("application/pdf");
                response.addHeader("Content-Disposition",
                        "attachment; filename=" + file.getFileName());
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            }
        } catch (IOException | DocumentException ex) {
            ex.printStackTrace();
        }
    }


}
