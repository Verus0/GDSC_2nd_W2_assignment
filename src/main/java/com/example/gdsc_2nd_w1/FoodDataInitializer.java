package com.example.gdsc_2nd_w1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FoodDataInitializer implements CommandLineRunner {

    private final FoodRepository foodRepository;

    public FoodDataInitializer(FoodRepository foodRepository) { // 의존성 주입 (FoodRepository)에
        this.foodRepository = foodRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        insertFood();
    }

    public void insertFood() {
        foodRepository.save(new Food("초밥", 4000));
        foodRepository.save(new Food("텐동", 9000));
        foodRepository.save(new Food("라멘", 8000));
        foodRepository.save(new Food("소바", 7000));
        foodRepository.save(new Food("와규", 10000));
    }
}

