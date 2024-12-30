package com.example.gdsc_2nd_w1.Login;

import com.example.gdsc_2nd_w1.Encryptor;
import com.example.gdsc_2nd_w1.User.User;
import com.example.gdsc_2nd_w1.User.UserRepository;
import com.example.gdsc_2nd_w1.User.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession;

@Service
public class LoginService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final Encryptor encryptor;


    @Autowired
    public LoginService(UserService userService, UserRepository userRepository, Encryptor encryptor) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.encryptor = encryptor;
    }

    @Transactional
    public LoginResponseDTO.DTO login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일의 사용자를 찾을 수 없습니다."));

        if (!encryptor.isMatch(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return new LoginResponseDTO.DTO(user);

    }

    @Transactional
    public ResponseEntity<String> logout(HttpSession session) {
        if (session != null) {
            session.invalidate(); // 세션 만료시키기
            return ResponseEntity.ok("로그아웃 되었습니다.");
        }
        else{
            return ResponseEntity.badRequest().body("로그아웃을 실패했습니다.");
        }

    }

}
