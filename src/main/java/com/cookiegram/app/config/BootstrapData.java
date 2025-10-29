package com.cookiegram.app.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.cookiegram.app.beans.Promotion;
import com.cookiegram.app.services.PromotionService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor	
public class BootstrapData implements CommandLineRunner {

    private final PromotionService service;

    @Override
    public void run(String... args) throws Exception {
        var active = service.getActivePromotions();
        if (active == null || active.isEmpty()) {
            service.save(new Promotion(
                    null, "Buy 2 Get 1 Free CookieGram",
                    "Send fresh cookies to someone you love. Includes a custom message card.",
                    "https://via.placeholder.com/300x200.png?text=CookieGram+Promo",
                    LocalDate.now().minusDays(1),
                    LocalDate.now().plusDays(7)
            ));
        }
    }

}
