package com.cookiegram.app.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cookiegram.app.beans.Promotion;
import com.cookiegram.app.services.PromotionService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/promotions")
@AllArgsConstructor
public class PromotionApiController {

	private final PromotionService service;

	@GetMapping("/api/promotions/active")
	public List<Promotion> getActivePromotions() {
		return service.getActivePromotions();
	}
}
