package com.hotel.Hotel;

import com.hotel.Hotel.exception.NoHotelFound;
import com.hotel.Hotel.record.AvailabilityRecord;
import com.hotel.Hotel.record.BookingRecord;
import com.hotel.Hotel.record.HotelsSearchRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HotelController {
  HotelService service;

  public HotelController(HotelService service) {
    this.service = service;
  }

  @GetMapping("/search/hotels")
  ResponseEntity<HotelsSearchRecord> findHotelsInCity(@RequestParam String city) {
    try {
      List<Hotel> hotelsByCity = service.findHotelsByCity(city);
      if (hotelsByCity.isEmpty()) throw new NoHotelFound(city);

      return ResponseEntity.ok(new HotelsSearchRecord(hotelsByCity, "fetched data"));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new HotelsSearchRecord(List.of(), e.getMessage()));
    }
  }

  @GetMapping("/hotel/check-availability/{id}")
  ResponseEntity<AvailabilityRecord> checkAvailability(@PathVariable String id, @RequestParam int roomsNeeded) {
    try {
      boolean flag = service.areRoomsAvailable(id, roomsNeeded);

      return ResponseEntity.ok(new AvailabilityRecord(flag, "checked rooms"));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new AvailabilityRecord(false, e.getMessage()));
    }
  }

  @PatchMapping("/hotel/{id}/book")
  ResponseEntity<BookingRecord> bookRooms(@PathVariable String id, @RequestParam int roomsNeeded) {
    try {
      service.bookRooms(id, roomsNeeded);

      return ResponseEntity.ok(new BookingRecord(true, "%d rooms booked".formatted(roomsNeeded)));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new BookingRecord(false, e.getMessage()));
    }
  }
}
