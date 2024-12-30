package com.example.gdsc_2nd_w1.Order;

import lombok.Data;

public class OrderRequestDTO {
    @Data
    public static class DTO{
        private String foodName;
        private Integer quantity;
    }
}