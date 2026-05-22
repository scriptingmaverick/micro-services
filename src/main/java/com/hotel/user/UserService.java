package com.hotel.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepo repo;

  public UserService(UserRepo repo) {
    this.repo = repo;
  }

  User loadByUsername(String username) {
    return repo.findByUsername(username);
  }

  SignUpRecord register(User user) {
    User savedUser = repo.save(user);

    return new SignUpRecord("%s is created successfully".formatted(savedUser.getUsername()));
  }

  Boolean isUserPresent(User user) {
    return repo.findByUsername(user.getUsername()) != null;
  }
}
