package com.moviebooking.repository;

import com.moviebooking.entity.Seat;
import com.moviebooking.enums.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    // Find all seats for a specific show
    List<Seat> findByShowId(Long showId);

    // Find seats by show and status
    List<Seat> findByShowIdAndStatus(Long showId, SeatStatus status);

    // Find a specific seat by show and seat number
    Optional<Seat> findByShowIdAndSeatNumber(Long showId, String seatNumber);

    // Count available seats for a show
    Long countByShowIdAndStatus(Long showId, SeatStatus status);

    // Find all seats by list of IDs and show ID
    List<Seat> findByIdInAndShowId(List<Long> ids, Long showId);

    // Find locked seats that have expired (for auto-release)
    @Query("SELECT s FROM Seat s WHERE s.status = 'LOCKED' " +
           "AND s.lockedAt < :expiryTime")
    List<Seat> findExpiredLockedSeats(
            @Param("expiryTime") LocalDateTime expiryTime
    );

    // Release expired locked seats back to AVAILABLE
    @Modifying
    @Query("UPDATE Seat s SET s.status = 'AVAILABLE', " +
           "s.lockedAt = null, s.lockedBy = null " +
           "WHERE s.status = 'LOCKED' " +
           "AND s.lockedAt < :expiryTime")
    int releaseExpiredLockedSeats(
            @Param("expiryTime") LocalDateTime expiryTime
    );

    // Find seats locked by a specific user for a show
    List<Seat> findByShowIdAndLockedBy(Long showId, Long userId);

    // Check if seat numbers exist in a show
    @Query("SELECT s FROM Seat s WHERE s.show.id = :showId " +
           "AND s.seatNumber IN :seatNumbers")
    List<Seat> findByShowIdAndSeatNumbers(
            @Param("showId") Long showId,
            @Param("seatNumbers") List<String> seatNumbers
    );
}