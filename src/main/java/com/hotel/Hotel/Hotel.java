package com.hotel.Hotel;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class Hotel {
  private int rooms;
  @Id
  private String id;
  private String name;
  private String city;
  @Field(value = "rooms_available")
  private int roomsAvailable;

  public Hotel(String name, String city, int rooms) {
    this.name = name;
    this.city = city;
    this.rooms = rooms;
    this.roomsAvailable = rooms;
  }

  public int getRooms() {
    return rooms;
  }

  public void setRooms(int rooms) {
    this.rooms = rooms;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public int getRoomsAvailable() {
    return roomsAvailable;
  }

  public void setRoomsAvailable(int roomsAvailable) {
    this.roomsAvailable = roomsAvailable;
  }
}
