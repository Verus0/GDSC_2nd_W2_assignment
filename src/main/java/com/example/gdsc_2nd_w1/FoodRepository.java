package com.example.gdsc_2nd_w1;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Integer> {
    Optional<Food> findByFoodName(String foodName);
}
