package com.cookiegram.app.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cookiegram.app.beans.Order;
import com.cookiegram.app.beans.OrderStatus;
import com.cookiegram.app.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    // you can tweak these later or load from config / DB
    private static final BigDecimal DEFAULT_UNIT_PRICE = BigDecimal.valueOf(20.00);
    private static final BigDecimal TAX_RATE = BigDecimal.valueOf(0.13); // 13% HST

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getRecentOrders() {
        return orderRepository.findTop10ByOrderByCreatedAtDesc();
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order updateStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    // 🔹 filter orders by optional status and date range
    public List<Order> filterOrders(OrderStatus status, LocalDate from, LocalDate to) {
        boolean hasStatus = status != null;
        boolean hasFrom = from != null;
        boolean hasTo = to != null;

        if (hasStatus && hasFrom && hasTo) {
            return orderRepository.findByStatusAndDeliveryDateBetween(status, from, to);
        } else if (hasStatus && !hasFrom && !hasTo) {
            return orderRepository.findByStatus(status);
        } else if (!hasStatus && hasFrom && hasTo) {
            return orderRepository.findByDeliveryDateBetween(from, to);
        } else {
            return orderRepository.findAll();
        }
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
    }

    /**
     * Calculate price fields for the order based on quantity, unit price and tax.
     * This should be called BEFORE save() when an order is first created.
     */
    public void calculatePrices(Order order) {
        if (order.getQuantity() == null) {
            throw new IllegalArgumentException("Quantity is required to calculate prices");
        }

        BigDecimal unitPrice = DEFAULT_UNIT_PRICE;
        BigDecimal qty = BigDecimal.valueOf(order.getQuantity());

        BigDecimal subtotal = unitPrice.multiply(qty);           // quantity × unit
        BigDecimal tax = subtotal.multiply(TAX_RATE);            // subtotal × tax
        BigDecimal total = subtotal.add(tax);                    // subtotal + tax

        order.setUnitPrice(unitPrice);
        order.setSubtotal(subtotal);
        order.setTax(tax);
        order.setTotal(total);
    }
}
