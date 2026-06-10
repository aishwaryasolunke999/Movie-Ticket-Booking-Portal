package com.moviebooking.service.impl;

import com.moviebooking.dto.response.SeatResponse;
import com.moviebooking.entity.Seat;
import com.moviebooking.enums.SeatStatus;
import com.moviebooking.exception.ResourceNotFoundException;
import com.moviebooking.exception.SeatNotAvailableException;
import com.moviebooking.repository.SeatRepository;
import com.moviebooking.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatRepository seatRepository;

    // Lock expiry time in minutes
    private static final int LOCK_EXPIRY_MINUTES = 10;

    @Override
    public List<SeatResponse> getSeatsByShow(Long showId) {
        return seatRepository.findByShowId(showId)
                .stream()
                .map(this::mapToSeatResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SeatResponse> getAvailableSeatsByShow(Long showId) {
        return seatRepository
                .findByShowIdAndStatus(showId, SeatStatus.AVAILABLE)
                .stream()
                .map(this::mapToSeatResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SeatResponse> getBookedSeatsByShow(Long showId) {
        return seatRepository
                .findByShowIdAndStatus(showId, SeatStatus.BOOKED)
                .stream()
                .map(this::mapToSeatResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SeatResponse> getLockedSeatsByShow(Long showId) {
        return seatRepository
                .findByShowIdAndStatus(showId, SeatStatus.LOCKED)
                .stream()
                .map(this::mapToSeatResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<SeatResponse> lockSeats(Long showId,
                                         List<Long> seatIds,
                                         Long userId) {

        // Step 1: Fetch seats by IDs and showId
        List<Seat> seats = seatRepository
                .findByIdInAndShowId(seatIds, showId);

        // Step 2: Validate all requested seats exist
        if (seats.size() != seatIds.size()) {
            throw new ResourceNotFoundException(
                "One or more seats not found for show: " + showId
            );
        }

        // Step 3: Check all seats are AVAILABLE
        List<Seat> unavailableSeats = seats.stream()
                .filter(s -> s.getStatus() != SeatStatus.AVAILABLE)
                .collect(Collectors.toList());

        if (!unavailableSeats.isEmpty()) {
            String seatNumbers = unavailableSeats.stream()
                    .map(Seat::getSeatNumber)
                    .collect(Collectors.joining(", "));
            throw new SeatNotAvailableException(
                "Seats not available: " + seatNumbers
            );
        }

        // Step 4: Lock all seats
        seats.forEach(seat -> {
            seat.setStatus(SeatStatus.LOCKED);
            seat.setLockedAt(LocalDateTime.now());
            seat.setLockedBy(userId);
        });

        // Step 5: Save locked seats
        List<Seat> lockedSeats = seatRepository.saveAll(seats);

        return lockedSeats.stream()
                .map(this::mapToSeatResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void releaseLockedSeats(Long showId, Long userId) {

        // Find all seats locked by this user for this show
        List<Seat> lockedSeats = seatRepository
                .findByShowIdAndLockedBy(showId, userId);

        // Release each seat back to AVAILABLE
        lockedSeats.forEach(seat -> {
            seat.setStatus(SeatStatus.AVAILABLE);
            seat.setLockedAt(null);
            seat.setLockedBy(null);
        });

        seatRepository.saveAll(lockedSeats);
    }

    @Override
    @Transactional
    // ⭐ Runs automatically every 5 minutes
    // Releases seats locked more than 10 mins ago
    @Scheduled(fixedRate = 300000) // 300000ms = 5 minutes
    public void releaseExpiredLockedSeats() {

        LocalDateTime expiryTime = LocalDateTime.now()
                .minusMinutes(LOCK_EXPIRY_MINUTES);

        int releasedCount = seatRepository
                .releaseExpiredLockedSeats(expiryTime);

        if (releasedCount > 0) {
            System.out.println(
                "Released " + releasedCount +
                " expired locked seats at " +
                LocalDateTime.now()
            );
        }
    }

    @Override
    public Long countAvailableSeats(Long showId) {
        return seatRepository.countByShowIdAndStatus(
                showId, SeatStatus.AVAILABLE
        );
    }

    // Helper — map Seat entity to SeatResponse
    public SeatResponse mapToSeatResponse(Seat seat) {
        return SeatResponse.builder()
                .id(seat.getId())
                .showId(seat.getShow().getId())
                .seatNumber(seat.getSeatNumber())
                .status(seat.getStatus())
                .lockedAt(seat.getLockedAt())
                .build();
    }
}