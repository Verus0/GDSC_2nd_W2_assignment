package com.example.gdsc_2nd_w1;

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
    private String userName;

    @Column(nullable = false)
    private String phoneNum;

    @Builder
    public User(Integer id, String userName, String phoneNum) {
        this.id = id;
        this.userName = userName;
        this.phoneNum = phoneNum;
    }
}

