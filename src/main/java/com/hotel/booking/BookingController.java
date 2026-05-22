package com.hotel.booking;

import com.hotel.booking.record.BookingServiceBookingRecord;
import com.hotel.booking.record.BookingsRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
  BookingService service;

  public BookingController(BookingService service) {
    this.service = service;
  }

  @PostMapping
  ResponseEntity<BookingServiceBookingRecord> createBooking(@RequestBody Booking booking, Authentication authentication) {
    try {
      booking.setUsername(authentication.getName());
      Booking dbBooking = service.saveBooking(booking);

      return ResponseEntity.ok(new BookingServiceBookingRecord(true, "%d rooms booked".formatted(dbBooking.getRooms())));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new BookingServiceBookingRecord(false, e.getMessage()));
    }
  }

  @GetMapping
  ResponseEntity<BookingsRecord> getBookings(Authentication authentication) {
    try {
      String message = "Recent bookings";
      String username = authentication.getName();
      List<Booking> bookings = service.getHotelsOfUser(username);
      if (bookings.isEmpty()) message = "No bookings available";

      return ResponseEntity.ok(new BookingsRecord(bookings, message));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new BookingsRecord(List.of(), e.getMessage()));
    }
  }
}
