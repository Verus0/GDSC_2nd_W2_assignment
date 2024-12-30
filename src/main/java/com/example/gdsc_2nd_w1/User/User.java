package com.example.gdsc_2nd_w1.User;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Builder
    public User(Integer id, String email, String nickname, String Encrypted_password) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = Encrypted_password;
    }
}

