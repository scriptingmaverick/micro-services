package com.hotel.booking;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
  BookingRepo repo;

  public BookingService(BookingRepo repo) {
    this.repo = repo;
  }

  List<Booking> getHotelsOfUser(String username) {
    return repo.findByUsername(username);
  }

  Booking saveBooking(Booking booking) {
    return repo.save(booking);
  }
}
