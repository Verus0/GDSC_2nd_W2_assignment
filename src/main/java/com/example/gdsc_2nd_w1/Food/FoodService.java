package com.example.gdsc_2nd_w1.Food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    @Autowired
    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public Optional<Food> findFoodByFoodName(String foodName) {
        return foodRepository.findByFoodName(foodName);
    }

}
