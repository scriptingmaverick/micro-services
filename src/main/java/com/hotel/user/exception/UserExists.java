package com.hotel.user.exception;

public class UserExists extends RuntimeException {
  public UserExists() {
    super("User already exists");
  }
}
