package org.example.service;

import org.example.dto.LoginRequest;
import org.example.dto.RegisterRequest;
import org.example.dto.User;
import org.example.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }
        User u = new User();
        u.setEmail(req.getEmail());
        u.setName(req.getName());
        u.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        u = userRepository.save(u);
        u.setPasswordHash(null);
        return u;
    }

    public User login(LoginRequest req) {
        User u = userRepository.findByEmail(req.getEmail());
        if(u != null && passwordEncoder.matches(req.getPassword(), u.getPasswordHash())) {
            u.setPasswordHash(null);
            return u;
        }
        return null;
    }
}
