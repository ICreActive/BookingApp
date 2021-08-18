package com.shkubel.project.util;

import com.shkubel.project.models.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class MailSender {

    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    public MailSender(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void sendActivationMessage(User user, HttpServletRequest request) {
        if (!user.isUserActive()) {
            String activationUrl = getSiteURL(request) + "/users/activate/" + user.getActivationCode();
            String message = String.format(
                    "Hello, %s! \n Welcome to BookingService." +
                            "Please, visit next link for activate your account: %s",
                    user.getUserFirstname(),
                    activationUrl);
            send(user.getEmail(), "Activation code", message);
        }
    }

    public void sendEmailForPasswordReset(HttpServletRequest request, String email, String token) {

        String resetPasswordLink = getSiteURL(request) + "/reset_password?token=" + token;

        String message =
                "Hello,"
                        + "\n You have requested to reset your password."
                        + "\nClick the link below to change your password:"
                        + "\n" + resetPasswordLink + " Change my password"
                        + "\n"
                        + "\nIgnore this email if you do remember your password, "
                        + "or you have not made the request.";

        send(email, "Reset Password", message);
    }

    public void send(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        emailSender.send(mailMessage);
    }

    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
