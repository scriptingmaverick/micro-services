package com.hotel.user;

import com.hotel.security.JWTService;
import com.hotel.user.exception.InvalidCredentials;
import com.hotel.user.exception.UserNotFound;
import com.hotel.user.record.SignInRecord;
import com.hotel.user.record.SignUpRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService service;
  private final JWTService jwtService;

  public UserController(UserService service, JWTService jwtService) {
    this.service = service;
    this.jwtService = jwtService;
  }

  @PostMapping("/register")
  public ResponseEntity<SignUpRecord> registerUser(@RequestBody User user) {
    try {
      User savedUser = service.register(user);

      return ResponseEntity.ok(new SignUpRecord("%s is created successfully".formatted(savedUser.getUsername())));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new SignUpRecord(e.getMessage()));
    }
  }

  @PostMapping("/login")
  public ResponseEntity<SignInRecord> loginUser(@RequestBody User user) {
    try {
      if (!service.isUserPresent(user)) {
        throw new UserNotFound();
      }

      if (!service.isValidUser(user)) {
        throw new InvalidCredentials();
      }

      String token = jwtService.generateToken(user);
      return ResponseEntity.ok(new SignInRecord(token, "%s login successful".formatted(user.getUsername())));
    } catch (Exception e) {
      return ResponseEntity.badRequest()
              .body(new SignInRecord(null, e.getMessage()));
    }
  }
}
