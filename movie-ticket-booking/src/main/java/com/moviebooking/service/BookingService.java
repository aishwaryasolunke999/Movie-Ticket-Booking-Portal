package com.moviebooking.service;

import com.moviebooking.dto.request.BookingRequest;
import com.moviebooking.dto.response.BookingResponse;

import java.util.List;

public interface BookingService {

    // Book tickets — Customer only
    BookingResponse bookTickets(BookingRequest request, Long userId);

    // Cancel booking — Customer only
    BookingResponse cancelBooking(Long bookingId, Long userId);

    // Get booking by ID — Customer only
    BookingResponse getBookingById(Long bookingId, Long userId);

    // Get all bookings for a user — Customer only
    List<BookingResponse> getBookingsByUser(Long userId);

    // Get confirmed bookings for a user — Customer only
    List<BookingResponse> getConfirmedBookingsByUser(Long userId);

    // Get cancelled bookings for a user — Customer only
    List<BookingResponse> getCancelledBookingsByUser(Long userId);

    // Get full booking history for a user — Customer only
    List<BookingResponse> getBookingHistoryByUser(Long userId);

    // Get all bookings for a show — Admin only
    List<BookingResponse> getBookingsByShow(Long showId);

    // Get total revenue for a show — Admin only
    Double getTotalRevenueByShow(Long showId);
}