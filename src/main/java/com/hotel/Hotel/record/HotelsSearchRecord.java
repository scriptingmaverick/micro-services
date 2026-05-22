package com.hotel.Hotel.record;

import com.hotel.Hotel.Hotel;

import java.util.List;

public record HotelsSearchRecord(List<Hotel> hotels, String message) {
}
