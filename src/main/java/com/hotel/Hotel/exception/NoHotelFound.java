package com.hotel.Hotel.exception;

public class NoHotelFound extends RuntimeException {
  public NoHotelFound(String city) {
    super("no hotels found in " + city);
  }
}
