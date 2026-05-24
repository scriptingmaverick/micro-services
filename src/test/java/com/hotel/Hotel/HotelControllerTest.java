package com.hotel.Hotel;

import com.hotel.Hotel.record.AvailabilityRecord;
import com.hotel.Hotel.record.BookingRecord;
import com.hotel.Hotel.record.HotelsSearchRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureRestTestClient
class HotelControllerTest {

  Hotel hotel =
          new Hotel("Taj", "Hyderabad", 10);
  @Autowired
  private RestTestClient client;
  @Autowired
  private HotelRepo repo;

  @Test
  void shouldFindHotelsByCity() {

    repo.deleteAll();
    repo.save(hotel);

    HotelsSearchRecord response =
            client.get()
                    .uri("/api/search/hotels?city=Hyderabad")
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody(HotelsSearchRecord.class)
                    .returnResult()
                    .getResponseBody();

    assertEquals(
            "fetched data",
            response.message()
    );

    assertEquals(
            1,
            response.hotels().size()
    );
  }

  @Test
  void shouldSaySomeInfoWhenNoHotelsFound() {

    repo.deleteAll();

    HotelsSearchRecord response =
            client.get()
                    .uri("/api/search/hotels?city=Hyderabad")
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody(HotelsSearchRecord.class)
                    .returnResult()
                    .getResponseBody();

    assertEquals(
            "No hotels found in Hyderabad",
            response.message()
    );
  }

  @Test
  void shouldCheckAvailability() {

    repo.deleteAll();

    Hotel savedHotel = repo.save(hotel);

    AvailabilityRecord response =
            client.get()
                    .uri("/api/hotel/check-availability/"
                            + savedHotel.getId()
                            + "?roomsNeeded=2")
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody(AvailabilityRecord.class)
                    .returnResult()
                    .getResponseBody();

    assertEquals(
            true,
            response.status()
    );
  }

  @Test
  void shouldBookRooms() {

    repo.deleteAll();

    Hotel savedHotel = repo.save(hotel);

    BookingRecord response =
            client.patch()
                    .uri("/api/hotel/"
                            + savedHotel.getId()
                            + "/book?roomsNeeded=2")
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody(BookingRecord.class)
                    .returnResult()
                    .getResponseBody();

    assertEquals(
            true,
            response.status()
    );

    Hotel updatedHotel =
            repo.findById(savedHotel.getId()).get();

    assertEquals(
            8,
            updatedHotel.getRoomsAvailable()
    );
  }

  @Test
  void shouldFailBookingWhenRoomsUnavailable() {

    repo.deleteAll();

    Hotel savedHotel = repo.save(hotel);

    BookingRecord response =
            client.patch()
                    .uri("/api/hotel/"
                            + savedHotel.getId()
                            + "/book?roomsNeeded=20")
                    .exchange()
                    .expectStatus()
                    .is4xxClientError()
                    .expectBody(BookingRecord.class)
                    .returnResult()
                    .getResponseBody();

    assertEquals(
            false,
            response.status()
    );
  }
}