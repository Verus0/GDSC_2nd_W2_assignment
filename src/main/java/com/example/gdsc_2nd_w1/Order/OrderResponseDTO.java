package com.example.gdsc_2nd_w1.Order;

import lombok.Data;

@Data
public class OrderResponseDTO {
    private Integer id;
    private String email;
    private String nickname;
    private String foodName;
    private Integer quantity;
    private Integer total;

    public OrderResponseDTO(r_Order rOrder) {
        this.id = rOrder.getId();
        this.email = rOrder.getUser().getEmail();
        this.nickname = rOrder.getUser().getNickname();
        this.foodName = rOrder.getFood().getFoodName();
        this.quantity = rOrder.getQuantity();
        this.total = rOrder.getTotal();
    }
}
