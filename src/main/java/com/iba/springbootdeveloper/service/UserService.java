package com.iba.springbootdeveloper.service;

import com.iba.springbootdeveloper.domain.Role;
import com.iba.springbootdeveloper.domain.User;
import com.iba.springbootdeveloper.dto.AddUserRequest;
import com.iba.springbootdeveloper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public Long save(AddUserRequest dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .build()).getId();
    }

    public User findById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with email " + email + " not found"));
    }

    public String getNicknameByEmail(String email) {
        User user = findByEmail(email);
        return user.getNickname(); // 사용자의 닉네임 반환
    }

    public void promoteToManager(String userEmail) {
        // 승격할 사용자 찾기
        User user = findByEmail(userEmail);
        if(user.getRole() != Role.USER){
            throw new IllegalArgumentException("Current role is not User");
        }
        // 사용자 역할을 MANAGER로 업데이트
        user.updateRole(Role.MANAGER);
        // 업데이트된 사용자 저장
        userRepository.save(user);
    }
}
