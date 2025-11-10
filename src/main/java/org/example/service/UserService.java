package org.example.service;

import org.example.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.example.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User updateUser(User user) {
        User before = userRepository.findByEmail(user.getEmail());
        if (before == null) throw new IllegalArgumentException("Usuário não encontrado");

        // Esse método não muda senha
        user.setPasswordHash(before.getPasswordHash());
        user = userRepository.update(user);
        user.setPasswordHash(null);
        return user;
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void addBookToUser(String email, String bookId) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        user.getBookIDs().add(bookId);
        userRepository.update(user);
    }
}
