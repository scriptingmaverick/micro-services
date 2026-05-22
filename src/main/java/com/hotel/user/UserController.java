package com.hotel.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @PostMapping("/register")
  public SignUpRecord registerUser(@RequestBody User user) {
    try {
      User savedUser = service.register(user);

      return new SignUpRecord("%s is created successfully".formatted(savedUser.getUsername()));
    } catch (Exception e) {
      return new SignUpRecord(e.getMessage());
    }
  }

  @PostMapping("/login")
  public ResponseEntity<SignInRecord> loginUser(@RequestBody User user) {
    try {
      if (!service.isUserPresent(user)) {
        throw new Exception("User not found");
      }

      if (!service.isValidUser(user)) {
        throw new Exception("Invalid credentials");
      }

      String token = "some";
      return ResponseEntity.ok(new SignInRecord(token, "%s login successful".formatted(user.getUsername())));
    } catch (Exception e) {
      return ResponseEntity.badRequest()
              .body(new SignInRecord(null, e.getMessage()));
    }
  }
}
