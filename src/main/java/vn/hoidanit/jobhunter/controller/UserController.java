package vn.hoidanit.jobhunter.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.service.error.IdInvalidException;

@RestController
public class UserController {

    public final UserService userService;

    public final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createNewUser(@RequestBody User request) {
        String encodedPassword = this.passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);
        User userRes = userService.handleCreateUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(userRes);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") long userId) throws IdInvalidException {

        if (userId > 1000) {
            throw new IdInvalidException("Qua lon roi");
        }
        userService.handleDeleteUser(userId);

        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") long userId) {
        User user = userService.handleGetUser(userId);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> lUsers = userService.handleGetUsers();

        return ResponseEntity.status(HttpStatus.OK).body(lUsers);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User userUpdated = userService.handleUpdateUsers(user);

        return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
    }
}
