package com.example.gdsc_2nd_w1.Order;

import com.example.gdsc_2nd_w1.Food.Food;
import com.example.gdsc_2nd_w1.User.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class r_Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer total;

    @Builder
    public r_Order(Integer id, User user, Food food, Integer quantity, Integer total) {
        this.id = id;
        this.user = user;
        this.food = food;
        this.quantity = quantity;
        this.total = total;
    }
}

