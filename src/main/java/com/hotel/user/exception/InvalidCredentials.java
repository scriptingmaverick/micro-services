package com.hotel.user.exception;

public class InvalidCredentials extends RuntimeException {
  public InvalidCredentials() {
    super("Invalid credentials");
  }
}
