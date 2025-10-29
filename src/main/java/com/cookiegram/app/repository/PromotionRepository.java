package com.cookiegram.app.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cookiegram.app.beans.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

	List<Promotion> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(
			LocalDate start, LocalDate end
			);
	
}
