package com.son.auth.service.impl;

import com.son.auth.domain.User;
import com.son.auth.dto.UserDto;
import com.son.auth.repository.UserRepository;
import com.son.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void addUser(UserDto req) {
        User user = User
                .builder()
                .userId(req.getUserId())
                .userPw(passwordEncoder.encode(req.getUserPw()))
                .build();

        userRepository.save(user);

    }
}
