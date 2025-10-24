package com.crudactivity.projectsync.controllers;

import com.crudactivity.projectsync.entity.User;
import com.crudactivity.projectsync.exception.NotFoundException;
import com.crudactivity.projectsync.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User u = userService.getById(id).orElseThrow(() -> new NotFoundException("User " + id + " not found"));
        return ResponseEntity.ok(u);
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@Valid @RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user, @PathVariable Long id) {
        return ResponseEntity.ok(userService.update(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
