package com.example.gdsc_2nd_w1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public void insertFood() {
        foodRepository.save(new Food("초밥", 4000));
        foodRepository.save(new Food("텐동", 9000));
        foodRepository.save(new Food("라멘", 8000));
        foodRepository.save(new Food("소바", 7000));
        foodRepository.save(new Food("와규", 10000));
    }

    @Autowired
    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public Optional<Food> findFoodByFoodName(String foodName) {
        return foodRepository.findByFoodName(foodName);
    }

}
