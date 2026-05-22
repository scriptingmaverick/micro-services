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

  User register(User user) {
    return repo.save(user);
  }

  Boolean isUserPresent(User user) {
    return repo.findByUsername(user.getUsername()) != null;
  }
}
