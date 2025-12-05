package com.cookiegram.app.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cookiegram.app.beans.Order;
import com.cookiegram.app.beans.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // For admin dashboard
    List<Order> findTop10ByOrderByCreatedAtDesc();
    
    List<Order> findByStatus(OrderStatus status);

    List<Order> findByDeliveryDateBetween(LocalDate from, LocalDate to);

    List<Order> findByStatusAndDeliveryDateBetween(
            OrderStatus status,
            LocalDate from,
            LocalDate to
    );

}
