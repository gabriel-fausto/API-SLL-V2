package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.*;
import org.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    @CrossOrigin(origins = "*")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequest req) {
        User user = authService.register(req);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/login", produces = "application/json")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Boolean> login(@Valid @RequestBody LoginRequest req) {
        return new ResponseEntity<>(authService.login(req), HttpStatus.OK);
    }
}