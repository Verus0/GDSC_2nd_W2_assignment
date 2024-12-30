package com.example.gdsc_2nd_w1;

import lombok.Data;

@Data
public class OrderResponseDTO {
    private Integer id;
    private String userName;
    private String phoneNum;
    private String foodName;
    private Integer quantity;
    private Integer total;

    public OrderResponseDTO(r_Order rOrder) {
        this.id = rOrder.getId();
        this.userName = rOrder.getUser().getUserName();
        this.phoneNum = rOrder.getUser().getPhoneNum();
        this.foodName = rOrder.getFood().getFoodName();
        this.quantity = rOrder.getQuantity();
        this.total = rOrder.getTotal();
    }
}
