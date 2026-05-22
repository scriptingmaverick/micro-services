package com.hotel.booking;

import com.hotel.booking.record.BookingServiceBookingRecord;
import com.hotel.booking.record.BookingsRecord;
import com.hotel.user.User;
import com.hotel.user.record.SignInRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureRestTestClient
class BookingControllerTest {

  @Autowired
  private RestTestClient client;

  @Autowired
  private BookingRepo repo;

  private String token;

  @BeforeEach
  void setup() {

    repo.deleteAll();

    User user = new User("jane", "doe");

    client.post()
            .uri("/api/users/register")
            .body(user)
            .header("content-type", "application/json")
            .exchange();

    SignInRecord loginResponse =
            client.post()
                    .uri("/api/users/login")
                    .body(user)
                    .header("content-type", "application/json")
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody(SignInRecord.class)
                    .returnResult()
                    .getResponseBody();

    token = loginResponse.token();
  }

  @Test
  void shouldCreateBooking() {

    Booking booking =
            new Booking("hotel-1", 2);

    BookingServiceBookingRecord response =
            client.post()
                    .uri("/api/bookings")
                    .body(booking)
                    .header("content-type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody(BookingServiceBookingRecord.class)
                    .returnResult()
                    .getResponseBody();

    assertEquals(
            true,
            response.status()
    );

    assertEquals(
            "2 rooms booked",
            response.message()
    );
  }

  @Test
  void shouldGetBookings() {

    Booking booking =
            new Booking("hotel-1", 2);

    client.post()
            .uri("/api/bookings")
            .body(booking)
            .header("content-type", "application/json")
            .header("Authorization", "Bearer " + token)
            .exchange()
            .expectStatus()
            .isOk();

    BookingsRecord response =
            client.get()
                    .uri("/api/bookings")
                    .header("Authorization", "Bearer " + token)
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody(BookingsRecord.class)
                    .returnResult()
                    .getResponseBody();

    assertEquals(
            "Recent bookings",
            response.message()
    );

    assertEquals(
            1,
            response.bookings().size()
    );
  }

  @Test
  void shouldReturnNoBookingsAvailable() {

    BookingsRecord response =
            client.get()
                    .uri("/api/bookings")
                    .header("Authorization", "Bearer " + token)
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody(BookingsRecord.class)
                    .returnResult()
                    .getResponseBody();

    assertEquals(
            "No bookings available",
            response.message()
    );

    assertEquals(
            0,
            response.bookings().size()
    );
  }
}