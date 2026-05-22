package com.hotel.booking.record;

import com.hotel.booking.Booking;

import java.util.List;

public record BookingsRecord(List<Booking> bookings, String message) {
}
