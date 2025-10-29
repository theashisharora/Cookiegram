package com.cookiegram.app.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cookiegram.app.beans.Promotion;
import com.cookiegram.app.repository.PromotionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PromotionService {

	private final PromotionRepository promotionRepository;

	public List<Promotion> getActivePromotions() {
		LocalDate today = LocalDate.now();
		return promotionRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(today, today);
	}
	
	public void save(Promotion promotion) {
		promotionRepository.save(promotion);
	}
	
}
