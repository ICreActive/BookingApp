package com.shkubel.project.web;

import com.shkubel.project.dto.ProfileUserDto;
import com.shkubel.project.exception.UserNotFoundException;
import com.shkubel.project.mapper.ProfileUserMapper;
import com.shkubel.project.models.entity.Invoice;
import com.shkubel.project.models.entity.User;
import com.shkubel.project.service.InvoiceService;
import com.shkubel.project.service.PdfService;
import com.shkubel.project.service.UserService;
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
    private final PdfService pdfService;

    private final ProfileUserMapper profileUserMapper;


    public UserController(UserService userService, InvoiceService invoiceService, PdfService pdfService, ProfileUserMapper profileUserMapper) {
        this.userService = userService;
        this.invoiceService = invoiceService;
        this.pdfService = pdfService;
        this.profileUserMapper = profileUserMapper;
    }

    @GetMapping("/myprofile")
    public String getProfile(Principal principal, Model model) {
        try {
            String username = principal.getName();
            User user = userService.findUserByUserName(username);
            model.addAttribute("user", profileUserMapper.toDTO(user));
            return "users/profile-id";
        } catch (UserNotFoundException e) {
            model.addAttribute("message", e.getMessage());
            return "users/profile-id";
        }
    }

    @GetMapping("/myprofile/{id}/edit")
    public String userEdit(@PathVariable("id") Long id, Model model) {

        try {
            ProfileUserDto userDto = profileUserMapper.toDTO(userService.findUserById(id));
            model.addAttribute("user", userDto);
            return "users/edit";
        } catch (UserNotFoundException e) {
            model.addAttribute("message", e.getMessage());
            return "message";
        }
    }

    @PostMapping("/myprofile/{id}/edit")
    public String userUpd(@ModelAttribute("user") @Valid ProfileUserDto user,
                          BindingResult bindingResult, @PathVariable("id") Long id, Model model) {
        final String s = "users/edit";
        if (bindingResult.hasErrors()) {
            return s;
        }
        try {
            userService.updateUser(id, profileUserMapper.toEntity(user));
            return "redirect:/users/myprofile";

        } catch (UserNotFoundException e) {
            model.addAttribute("usernameError", e.getMessage());
            return s;
        }
    }

    @GetMapping("/myprofile/invoices")
    public String showUserInvoice(Principal principal, Model model) {

        try {
            String email = principal.getName();
            User user = userService.findUserByUserName(email);
            List<Invoice> invoiceList = invoiceService.findAllByUser(user);
            model.addAttribute("invoices", invoiceList);
        } catch (UserNotFoundException e) {
            model.addAttribute("message", e.getMessage());
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
