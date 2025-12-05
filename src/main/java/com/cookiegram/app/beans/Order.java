package com.cookiegram.app.beans;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Who is sending the CookieGram
    @NotBlank
    private String senderName;

    @NotBlank
    @Email
    private String senderEmail;

    // Who is receiving it
    @NotBlank
    private String recipientName;

    @NotBlank
    @Email
    private String recipientEmail;

    @Column(length = 255)
    private String address;

    // Pricing fields
    private BigDecimal unitPrice;     // price per cookie
    private BigDecimal subtotal;      // quantity × unitPrice
    private BigDecimal tax;           // subtotal × taxRate
    private BigDecimal total;         // subtotal + tax

    // Simple choice for now (could later be linked to Promotion)
    @NotBlank
    private String cookieType;     // e.g. "CHOCO_CHIP"

    @NotNull
    @Min(1)
    @Max(24)
    private Integer quantity;

    // Delivery date chosen by customer
    @NotNull
    private LocalDate deliveryDate;

    @Column(length = 1000)
    private String message;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        // default payment status if not set
        if (paymentStatus == null) {
            paymentStatus = PaymentStatus.PENDING;
        }
        if (status == null) {
            status = OrderStatus.NEW;
        }
    }

    // Order lifecycle (NEW, IN_PROGRESS, SHIPPED, CANCELLED, etc.)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.NEW;

    // 🔹 You can keep this as a convenience flag for UI,
    //     but it's basically derived from paymentStatus == PAID
    private boolean paid;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;   // PENDING / PAID / FAILED

    // optional: to store a fake transaction id for demo
    private String paymentReference;
}
