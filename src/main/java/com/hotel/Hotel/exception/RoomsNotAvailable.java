package com.hotel.Hotel.exception;

public class RoomsNotAvailable extends RuntimeException {
  public RoomsNotAvailable() {
    super("Rooms not available");
  }
}
