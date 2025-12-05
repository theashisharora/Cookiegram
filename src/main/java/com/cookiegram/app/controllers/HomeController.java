package com.cookiegram.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cookiegram.app.services.PromotionService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class HomeController {

	public PromotionService promotionService;
	
	@GetMapping("/")
	public String landing(Model model) {
	    model.addAttribute("promotions", promotionService.getActivePromotions());
	    return "landing";
	}

}
