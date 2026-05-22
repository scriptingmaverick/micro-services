package com.hotel.booking;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "bookings")
public class Booking {
  String hotelId;
  String username;
  int rooms;

  public Booking(String hotelId, int rooms) {
    this.hotelId = hotelId;
    this.rooms = rooms;
  }

  public String getHotelId() {
    return hotelId;
  }

  public void setHotelId(String hotelId) {
    this.hotelId = hotelId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getRooms() {
    return rooms;
  }

  public void setRooms(int rooms) {
    this.rooms = rooms;
  }
}
