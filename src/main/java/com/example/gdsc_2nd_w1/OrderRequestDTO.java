package com.example.gdsc_2nd_w1;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderRequestDTO {
    private String userName;
    private String phoneNum;
    private String foodName;
    private Integer quantity;
}

