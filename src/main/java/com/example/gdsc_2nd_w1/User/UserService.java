package com.example.gdsc_2nd_w1.User;

import com.example.gdsc_2nd_w1.Encryptor;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final Encryptor encryptor;
    private final HttpSession session;


    @Autowired
    public UserService(UserRepository userRepository, Encryptor encryptor, HttpSession session) {
        this.userRepository = userRepository;
        this.encryptor = encryptor;
        this.session = session;
    }

    @Transactional
    public User createUser(UserRequestDTO userRequestDTO) {
        userRepository.findByEmail(userRequestDTO.getEmail()).ifPresent(u -> {              // 이메일 중복 검사
                    throw new RuntimeException("동일한 이메일이 이미 존재합니다.");
                });

        userRepository.findByNickname(userRequestDTO.getNickname()).ifPresent(u -> {       // 닉네임 중복 검사
                    throw new RuntimeException("동일한 닉네임이 이미 존재합니다.");
                });

        User user = User.builder()
                .email(userRequestDTO.getEmail())
                .nickname(userRequestDTO.getNickname())
                .Encrypted_password(encryptor.encrypt(userRequestDTO.getPassword()))
                .build();

        return userRepository.save(user);
    }



    public Optional<User> findUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    public Optional<User> findUserById(Integer id) {
        return userRepository.findById(id);
    }
}
