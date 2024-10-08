package com.flightDB.DBApp.controller;

import com.flightDB.DBApp.model.ERole;
import com.flightDB.DBApp.model.User;
import com.flightDB.DBApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    UserService userService;

    @DeleteMapping(path = "/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping(path = "/updateUsername/{id}")
    public User updateUser(@PathVariable Long id, @RequestParam String username) {
        return userService.updateUsername(username, id);
    }

    @GetMapping
    public ResponseEntity<?> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);

        if (user != null) {
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/getUsername")
    public User getUsername(@RequestParam String username) {
        return userService.getUserByUsername(username);
    }

    @PutMapping(path = "/updatePassword/{id}")
    public User updatePassword(@RequestParam String oldPassword,@PathVariable Long id, @RequestParam String password) {
        return userService.updatePassword(oldPassword,password, id);
    }

    @GetMapping(path = "/get/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping(path = "/updateRole/{id}")
    public User updateRole(@PathVariable Long id, @RequestParam ERole role) {
        return userService.updateRole(role, id);
    }
}
