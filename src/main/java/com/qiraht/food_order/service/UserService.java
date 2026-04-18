package com.qiraht.food_order.service;

import com.qiraht.food_order.constant.UserRole;
import com.qiraht.food_order.dto.request.UserRequest;
import com.qiraht.food_order.entity.User;
import com.qiraht.food_order.exception.NotFoundException;
import com.qiraht.food_order.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String registerNewUser(UserRequest request) {
        // E-mail Cheks
        if (userRepository.existsByEmail(request.email())) {
            throw  new NotFoundException("Email already exists");
        }

        // Password hashing
        String hashedPassword = passwordEncoder.encode(request.password());

        // create new user with builder
        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(hashedPassword)
                .role(UserRole.USER)
                .build();

        // save new user
        userRepository.save(user);

        return user.getId().toString();
    }
}
