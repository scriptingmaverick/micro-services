package com.hotel.user;

import com.hotel.user.exception.UserExists;
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
    if (isUserPresent(user)) {
      throw new UserExists();
    }

    return repo.save(user);
  }

  Boolean isUserPresent(User user) {
    return repo.findByUsername(user.getUsername()) != null;
  }

  public boolean isValidUser(User user) {
    User dbUser = this.loadByUsername(user.getUsername());

    return dbUser.getPassword().equals(user.getPassword());
  }
}
