package com.cookiegram.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/customer")
	public String customerDashboard() {
		return "customer";
	}
	
	@GetMapping("/admin")
	public String adminDashboard() {
		return "admin";
	}
	
	@GetMapping("/employee")
	public String employeeDashboard() {
		return "employee";
	}
	
	@GetMapping("/403")
	public String accessDenied() {
		return "403";
	}
	
}
