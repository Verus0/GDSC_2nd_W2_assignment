package com.example.gdsc_2nd_w1.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByNickname(String nickname);
    Optional<User> findByEmail(String email);
}




