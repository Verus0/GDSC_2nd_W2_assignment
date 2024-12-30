package com.example.gdsc_2nd_w1.Login;

import com.example.gdsc_2nd_w1.User.User;
import lombok.Data;

public class LoginResponseDTO {

    @Data
    public static class DTO {
        private Integer userId;
        private String email;
        private String nickname;

        public DTO( User user) {
            this.userId = user.getId();
            this.email = user.getEmail();
            this.nickname = user.getNickname();
        }

    }

}