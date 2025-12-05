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
                    null,
                    "Buy 2 Get 1 Free CookieGram",
                    "Send fresh cookies to someone you love. Includes a custom message card.",
                    "/images/cookies/choco-chip.jpg",
                    LocalDate.now().minusDays(1),
                    LocalDate.now().plusDays(7)
            ));

            service.save(new Promotion(
                    null,
                    "Hot Chocolate Celebration",
                    "Limited edition red velvet CookieGram for special moments.",
                    "/images/cookies/red-velvet.jpg",
                    LocalDate.now().minusDays(2),
                    LocalDate.now().plusDays(5)
            ));

            service.save(new Promotion(
                    null,
                    "Sprinkle Choco Offer",
                    "Colorful sprinkle cookies that make every day a party.",
                    "/images/cookies/sprinkle-delight.jpg",
                    LocalDate.now().minusDays(3),
                    LocalDate.now().plusDays(10)
            ));
        }
    }
}
