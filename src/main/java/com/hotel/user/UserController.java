package com.hotel.user;

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
}
