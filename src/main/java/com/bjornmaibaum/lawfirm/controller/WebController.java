package com.bjornmaibaum.lawfirm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import com.bjornmaibaum.lawfirm.model.ContactRequest;
import com.bjornmaibaum.lawfirm.service.EmailService;

@Controller
public class WebController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("contactRequest", new ContactRequest());
        return "index";
    }

    @PostMapping("/contact")
    public String submitContact(@Valid ContactRequest contactRequest, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        try {
            String emailBody = "Name: " + contactRequest.getName() + "\n" +
                            "Email: " + contactRequest.getEmail() + "\n\n" +
                            "Message:\n" + contactRequest.getMessage();
            emailService.sendContactFormEmail("BarristerLawFirm92@protonmail.com", "New Contact Request from " + contactRequest.getName(), emailBody);
            model.addAttribute("successMessage", "Thank you! We'll respond within 24 hours.");
            model.addAttribute("contactRequest", new ContactRequest());
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to send message. Please try again later.");
        }
        return "index";
    }
}