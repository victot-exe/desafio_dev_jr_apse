package com.victot.desafio_dev_jr_apse.controller;

import com.victot.desafio_dev_jr_apse.model.User;
import com.victot.desafio_dev_jr_apse.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController() @RequestMapping("users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<User> addUser(@RequestBody User user) {

        User response = userService.save(user);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User response = userService.update(user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> findUserById(@RequestParam Long id) {
        User response = userService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<String>> findAllUsers() {
        List<String> response = userService.findAll();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
