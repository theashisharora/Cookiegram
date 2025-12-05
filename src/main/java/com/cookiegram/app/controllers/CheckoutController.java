package com.cookiegram.app.controllers;

import com.cookiegram.app.beans.Order;
import com.cookiegram.app.beans.PaymentStatus;
import com.cookiegram.app.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class CheckoutController {

    private final OrderService orderService;

    // STEP 1: Show payment page
    @GetMapping("/pay/{id}")
    public String showPaymentPage(@PathVariable Long id, Model model) {
        Order order = orderService.findById(id);
        model.addAttribute("order", order);
        return "pay";    // --> templates/pay.html
    }

    // STEP 2: Process "payment" (fake for demo)
    @PostMapping("/pay/{id}")
    public String processPayment(@PathVariable Long id, RedirectAttributes ra) {
        Order order = orderService.findById(id);

        // mark as paid
        order.setPaymentStatus(PaymentStatus.PAID);
        order.setPaid(true);
        order.setPaymentReference("FAKE-" + System.currentTimeMillis());

        orderService.save(order);

        ra.addFlashAttribute("message", "Payment successful for order #" + order.getId());
        return "redirect:/order-confirmation/" + order.getId();
    }

    // STEP 3: Show confirmation page
    @GetMapping("/order-confirmation/{id}")
    public String showConfirmation(@PathVariable Long id, Model model) {
        Order order = orderService.findById(id);
        model.addAttribute("order", order);
        return "order-confirmation";   // --> templates/order-confirmation.html
    }
}
