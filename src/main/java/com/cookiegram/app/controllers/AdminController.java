package com.cookiegram.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import com.cookiegram.app.services.OrderService;
import com.cookiegram.app.services.PromotionService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class AdminController {

    private final OrderService orderService;
    private final PromotionService promotionService;

    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        model.addAttribute("orders", orderService.findAll());
        model.addAttribute("promotions", promotionService.findAll());
        return "admin";
    }
}
