package com.cookiegram.app.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import com.cookiegram.app.beans.Promotion;
import com.cookiegram.app.repository.PromotionRepository;

public class PromotionServiceTest {

    private PromotionRepository promotionRepository;
    private PromotionService promotionService;

    @BeforeEach
    void setUp() {
        promotionRepository = mock(PromotionRepository.class);
        promotionService = new PromotionService(promotionRepository);
    }

    @Test
    void getActivePromotions_returnsPromotionsFromRepoForToday() {
   
        LocalDate today = LocalDate.now();

        Promotion promo = new Promotion(
                null, "Test Promo",
                "Deal",
                "img",
                today.minusDays(1),
                today.plusDays(2)
        );

        when(promotionRepository
                .findByStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        ArgumentMatchers.any(LocalDate.class),
                        ArgumentMatchers.any(LocalDate.class)
                )
        ).thenReturn(List.of(promo));

        List<Promotion> result = promotionService.getActivePromotions();

        assertThat(result)
                .isNotNull()
                .hasSize(1);

        assertThat(result.get(0).getTitle())
                .isEqualTo("Test Promo");

        verify(promotionRepository, times(1))
                .findByStartDateLessThanEqualAndEndDateGreaterThanEqual(today, today);
    }
}
