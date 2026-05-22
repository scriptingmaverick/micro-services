package com.hotel.Hotel;

import com.hotel.Hotel.exception.RoomsNotAvailable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

  @Mock
  private HotelRepo repo;

  @InjectMocks
  private HotelService service;

  @Test
  void shouldFindHotelsByCity() {

    Hotel hotel1 = new Hotel("Taj", "Hyderabad", 10);
    Hotel hotel2 = new Hotel("Novotel", "Hyderabad", 20);

    when(repo.findByCity("Hyderabad"))
            .thenReturn(List.of(hotel1, hotel2));

    List<Hotel> hotels =
            service.findHotelsByCity("Hyderabad");

    assertEquals(2, hotels.size());

    verify(repo).findByCity("Hyderabad");
  }

  @Test
  void shouldGetHotelById() {

    Hotel hotel = new Hotel("Taj", "Hyderabad", 10);

    when(repo.findById("1"))
            .thenReturn(Optional.of(hotel));

    Optional<Hotel> result = service.getHotel("1");

    assertTrue(result.isPresent());

    verify(repo).findById("1");
  }

  @Test
  void shouldReturnTrueWhenRoomsAvailable() {

    Hotel hotel = new Hotel("Taj", "Hyderabad", 10);

    when(repo.findById("1"))
            .thenReturn(Optional.of(hotel));

    boolean result =
            service.areRoomsAvailable("1", 5);

    assertTrue(result);
  }

  @Test
  void shouldReturnFalseWhenRoomsNotAvailable() {

    Hotel hotel = new Hotel("Taj", "Hyderabad", 2);

    when(repo.findById("1"))
            .thenReturn(Optional.of(hotel));

    boolean result =
            service.areRoomsAvailable("1", 5);

    assertFalse(result);
  }

  @Test
  void shouldBookRoomsSuccessfully() {

    Hotel hotel = new Hotel("Taj", "Hyderabad", 10);

    when(repo.findById("1"))
            .thenReturn(Optional.of(hotel));

    service.bookRooms("1", 3);

    verify(repo).findByIdAndUpdateRoomCount("1", -3);
  }

  @Test
  void shouldThrowExceptionWhenRoomsUnavailable() {

    Hotel hotel = new Hotel("Taj", "Hyderabad", 2);

    when(repo.findById("1"))
            .thenReturn(Optional.of(hotel));

    assertThrows(
            RoomsNotAvailable.class,
            () -> service.bookRooms("1", 5)
    );

    verify(repo, never())
            .findByIdAndUpdateRoomCount(any(), anyInt());
  }

}