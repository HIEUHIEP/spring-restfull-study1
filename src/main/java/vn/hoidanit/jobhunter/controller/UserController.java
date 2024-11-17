package vn.hoidanit.jobhunter.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;

@RestController
public class UserController {

    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public User createNewUser(@RequestBody User request) {

        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(request.getPassword());

        User userRes = userService.handleCreateUser(user);
        return userRes;
    }

    @DeleteMapping("/user/{userId}")
    public String deleteUser(@PathVariable("userId") long userId) {
        userService.handleDeleteUser(userId);
        return "ok";
    }

    @GetMapping("/user/{userId}")
    public User getUser(@PathVariable("userId") long userId) {
        return userService.handleGetUser(userId);
    }

    @GetMapping("/user")
    public List<User> getUsers() {
        return userService.handleGetUsers();
    }

    @PutMapping("/user")
    public User updateUser(@RequestBody User user) {
        // return userService.handleUpdateUsers(user);

        User userCurrent = getUser(user.getId());
        userCurrent.setEmail(user.getEmail());
        userCurrent.setName(user.getName());
        userCurrent.setPassword(user.getPassword());
        return userService.handleUpdateUsers(userCurrent);
    }
}
