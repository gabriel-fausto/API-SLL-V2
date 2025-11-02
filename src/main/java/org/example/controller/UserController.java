package org.example.controller;

import org.example.dto.Book;
import org.example.dto.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{email}", produces = "application/json")
    @CrossOrigin(origins = "*")
    public ResponseEntity<User> getBook(@PathVariable String email) {
        return new ResponseEntity<>(userService.findByEmail(email), HttpStatus.OK);
    }

    @PutMapping(produces = "application/json", consumes = "application/json")
    @CrossOrigin(origins = "*")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }
}
