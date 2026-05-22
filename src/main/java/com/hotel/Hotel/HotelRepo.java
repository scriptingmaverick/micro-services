package com.hotel.Hotel;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepo extends MongoRepository<Hotel, String> {
  List<Hotel> findByCity(String city);

  Optional<Hotel> findById(String id);

  @Query("{'_id': ?0}")
  @Update("{ '$inc' : { 'rooms_available' : ?1 } }")
  void findByIdAndUpdateRoomCount(String id, int roomCount);
}
