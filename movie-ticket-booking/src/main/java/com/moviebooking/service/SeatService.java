package com.moviebooking.service;

import com.moviebooking.dto.response.SeatResponse;

import java.util.List;

public interface SeatService {

    // Get all seats for a show
    List<SeatResponse> getSeatsByShow(Long showId);

    // Get available seats for a show
    List<SeatResponse> getAvailableSeatsByShow(Long showId);

    // Get booked seats for a show
    List<SeatResponse> getBookedSeatsByShow(Long showId);

    // Get locked seats for a show
    List<SeatResponse> getLockedSeatsByShow(Long showId);

    // Lock seats for a user
    List<SeatResponse> lockSeats(Long showId,
                                  List<Long> seatIds,
                                  Long userId);

    // Release locked seats for a user
    void releaseLockedSeats(Long showId, Long userId);

    // Release all expired locked seats
    // (called by scheduler every few minutes)
    void releaseExpiredLockedSeats();

    // Count available seats for a show
    Long countAvailableSeats(Long showId);
}