package com.example.gdsc_2nd_w1.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequestDTO {
    private String email;
    private String nickname;
    private String password;
}
