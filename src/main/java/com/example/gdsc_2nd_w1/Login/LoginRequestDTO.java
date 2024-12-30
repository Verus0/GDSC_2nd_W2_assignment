package com.example.gdsc_2nd_w1.Login;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequestDTO {
    private String email;
    private String password;
}
