package com.example.gdsc_2nd_w1;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String foodName;

    @Column(nullable = false)
    private Integer foodPrice;

    @Builder
    public Food(Integer id, String foodName, Integer foodPrice) {
        this.id = id;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
    }

    public Food(String foodName, Integer foodPrice) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
    }
}

