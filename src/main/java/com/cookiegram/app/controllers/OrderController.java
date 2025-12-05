package com.cookiegram.app.controllers;

import java.math.BigDecimal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cookiegram.app.beans.Order;
import com.cookiegram.app.beans.OrderStatus;
import com.cookiegram.app.beans.PaymentStatus;
import com.cookiegram.app.services.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public String showOrderForm(
            @RequestParam(value = "cookieType", required = false) String cookieType,
            Model model
    ) {
        Order order = new Order();

        if (cookieType != null && !cookieType.isBlank()) {
            order.setCookieType(cookieType);
            order.setQuantity(1); // default
        }

        model.addAttribute("order", order);
        return "order";
    }


    @PostMapping
    public String submitOrder(@Valid @ModelAttribute("order") Order order,
            BindingResult bindingResult,
            Model model) {
if (bindingResult.hasErrors()) {
return "order";
}

// compute prices
BigDecimal unitPrice = BigDecimal.valueOf(10.00); // example
BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(order.getQuantity()));
BigDecimal tax = subtotal.multiply(BigDecimal.valueOf(0.13));
BigDecimal total = subtotal.add(tax);

order.setUnitPrice(unitPrice);
order.setSubtotal(subtotal);
order.setTax(tax);
order.setTotal(total);

// NEW: set payment status to PENDING
order.setPaymentStatus(PaymentStatus.PENDING);

Order saved = orderService.save(order);

// ⬇️ instead of going directly to confirmation, go to payment page
return "redirect:/pay/" + saved.getId();
}


    @GetMapping("/thank-you")
    public String thankYou() {
        return "order-thank-you";
    }
}
