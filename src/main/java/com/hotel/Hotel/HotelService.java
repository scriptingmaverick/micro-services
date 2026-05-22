package com.hotel.Hotel;

import com.hotel.Hotel.exception.RoomsNotAvailable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService {
  HotelRepo repo;

  public HotelService(HotelRepo repo) {
    this.repo = repo;
  }

  List<Hotel> findHotelsByCity(String city) {
    return repo.findByCity(city);
  }

  Optional<Hotel> getHotel(String id) {
    return repo.findById(id);
  }

  boolean areRoomsAvailable(String id, int roomCount) {
    Optional<Hotel> hotel = getHotel(id);

    return hotel.get().getRoomsAvailable() >= roomCount;
  }

  void bookRooms(String id, int roomCount) {
    if (!areRoomsAvailable(id, roomCount)) {
      throw new RoomsNotAvailable();
    }

    repo.findByIdAndUpdateRoomCount(id, -roomCount);
  }
}
