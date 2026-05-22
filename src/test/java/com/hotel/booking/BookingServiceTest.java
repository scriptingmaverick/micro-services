package com.hotel.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

  @Mock
  private BookingRepo repo;

  @InjectMocks
  private BookingService service;

  @Test
  void shouldGetBookingsOfUser() {

    Booking booking1 =
            new Booking("hotel-1", 2);
    booking1.setUsername("jane");

    Booking booking2 =
            new Booking("hotel-2", 1);
    booking2.setUsername("jane");

    when(repo.findByUsername("jane"))
            .thenReturn(List.of(booking1, booking2));

    List<Booking> bookings =
            service.getHotelsOfUser("jane");

    assertEquals(2, bookings.size());

    verify(repo).findByUsername("jane");
  }

  @Test
  void shouldSaveBooking() {

    Booking booking =
            new Booking("hotel-1", 2);
    booking.setUsername("jane");

    when(repo.save(booking))
            .thenReturn(booking);

    Booking savedBooking =
            service.saveBooking(booking);

    assertEquals(
            "jane",
            savedBooking.getUsername()
    );

    assertEquals(
            2,
            savedBooking.getRooms()
    );

    assertEquals(
            "hotel-1",
            savedBooking.getHotelId()
    );

    verify(repo).save(booking);
  }
}