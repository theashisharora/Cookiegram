package com.cookiegram.app.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cookiegram.app.beans.Order;
import com.cookiegram.app.beans.OrderStatus;
import com.cookiegram.app.services.OrderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final OrderService orderService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/customer")
    public String customerDashboard() {
        return "customer";
    }

    // @GetMapping("/admin")
   // public String adminDashboard(Model model) {
     //   model.addAttribute("recentOrders", orderService.getRecentOrders());
       // return "admin";
    //}

 // 🔹 Employee dashboard with filters
    @GetMapping("/employee")
    public String employeeDashboard(
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fromDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate toDate,
            Model model
    ) {
        List<Order> orders = orderService.filterOrders(status, fromDate, toDate);

        model.addAttribute("orders", orders);
        model.addAttribute("allStatuses", OrderStatus.values());

        // for keeping selected values in the form
        model.addAttribute("selectedStatus", status);
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);

        return "employee";
    }

    // 🔹 Change status of a single order
    @PostMapping("/employee/order/{id}/status")
    public String updateOrderStatus(
            @PathVariable("id") Long id,
            @RequestParam("status") OrderStatus status
    ) {
        orderService.updateStatus(id, status);
        return "redirect:/employee";
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }
    
    @GetMapping("/employee/order/{id}")
    public String viewOrderDetails(@PathVariable("id") Long id, Model model) {
        Order order = orderService.findById(id);
        model.addAttribute("order", order);
        return "order-details";
    }

    @PostMapping("/employee/order/{id}/cancel")
    public String cancelOrder(@PathVariable("id") Long id) {
        orderService.updateStatus(id, OrderStatus.CANCELLED);
        return "redirect:/employee/order/" + id;
    }

}
