package org.example.service;

import org.example.dto.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User updateUser(User user) {
        User before = userRepository.findByEmail(user.getEmail());
        if (before == null) throw new IllegalArgumentException("Usuário não encontrado");

        // Esse método não muda senha
        user.setPasswordHash(before.getPasswordHash());
        user = userRepository.update(user);
        user.setPasswordHash(null);
        return user;
    }

    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        user.setPasswordHash(null);
        return user;
    }
}
