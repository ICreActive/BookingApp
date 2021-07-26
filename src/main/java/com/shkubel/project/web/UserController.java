package com.shkubel.project.web;

import com.shkubel.project.exception.UserNotFoundException;
import com.shkubel.project.models.entity.Invoice;
import com.shkubel.project.models.entity.User;
import com.shkubel.project.service.InvoiceService;
import com.shkubel.project.service.PdfService;
import com.shkubel.project.service.impl.PdfServiceImpl;
import com.shkubel.project.service.UserService;
import com.shkubel.project.util.MailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final InvoiceService invoiceService;
    private final MailSender mailSender;
    private final PdfService pdfService;


    public UserController(UserService userService, InvoiceService invoiceService, MailSender mailSender, PdfService pdfService) {
        this.userService = userService;
        this.invoiceService = invoiceService;
        this.mailSender = mailSender;
        this.pdfService = pdfService;
    }

    @GetMapping("/myprofile")
    public String getProfile(Model model, Principal principal) {
        try {
            User user = userService.findUserByUserName(principal.getName());
            model.addAttribute("user", user);
            return "users/profile-id";
        } catch (UserNotFoundException e) {
            model.addAttribute("message", e);
            return "users/profile-id";
        }
    }

    @GetMapping("/myprofile/{id}/edit")
    public String userEdit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "users/edit";
    }

    @PostMapping("/myprofile/{id}/edit")
    public String userUpd(@ModelAttribute("user") @Valid User user,
                          BindingResult bindingResult, @PathVariable("id") Long id, Model model) {
        final String s = "users/edit";
        if (bindingResult.hasErrors()) {
            return s;
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            model.addAttribute("passwordError", "The passwords don't match");
            return s;
        }
        if (!userService.updateUser(id, user)) {
            model.addAttribute("usernameError", "This username or e-mail already exists");
            return s;
        }
        userService.updateUser(id, user);
        return "redirect:/users/myprofile";
    }

    @GetMapping("/myprofile/invoices")
    public String showUserInvoice(Principal principal, Model model) {

        try{
        User user = userService.findUserByUserName(principal.getName());
        List<Invoice> invoiceList = invoiceService.findAllByUser(user);
        model.addAttribute("invoices", invoiceList);
        } catch (UserNotFoundException e) {
            model.addAttribute("message", e);
        }

        return "invoice/invoices";
    }

    @GetMapping("/myprofile/invoices/{id}")
    public String showSingleInvoice(@PathVariable("id") Long id, Model model) {
        model.addAttribute("invoice", invoiceService.findInvoiceById(id));
        model.addAttribute("period", invoiceService.findInvoiceById(id).getPeriod());
        return "invoice/new";
    }

    @PostMapping("/download-pdf")
    public void exportToPDF(@RequestParam(defaultValue = "") Long invoiceId,
                            HttpServletResponse response) throws Exception {
        Invoice invoice = invoiceService.findInvoiceById(invoiceId);
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=inv-%s.pdf",
                invoice.getId());

        response.setHeader(headerKey, headerValue);
        pdfService.setInvoice(invoice);
        pdfService.exportToPdf(response);

    }

}
