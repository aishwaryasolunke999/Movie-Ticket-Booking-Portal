package com.moviebooking.repository;

import com.moviebooking.entity.Booking;
import com.moviebooking.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Find all bookings by user
    List<Booking> findByUserId(Long userId);

    // Find all bookings by user and status
    List<Booking> findByUserIdAndStatus(Long userId, BookingStatus status);

    // Find all bookings for a show
    List<Booking> findByShowId(Long showId);

    // Find booking by id and user id (security check)
    Optional<Booking> findByIdAndUserId(Long id, Long userId);

    // Find all confirmed bookings for a show
    List<Booking> findByShowIdAndStatus(Long showId, BookingStatus status);

    // Get booking history with show and movie details
    @Query("SELECT b FROM Booking b " +
           "JOIN FETCH b.show s " +
           "JOIN FETCH s.movie m " +
           "WHERE b.user.id = :userId " +
           "ORDER BY b.bookedAt DESC")
    List<Booking> findBookingHistoryByUser(
            @Param("userId") Long userId
    );

    // Get total revenue for a show
    @Query("SELECT SUM(b.totalAmount) FROM Booking b " +
           "WHERE b.show.id = :showId " +
           "AND b.status = 'CONFIRMED'")
    Double getTotalRevenueByShow(
            @Param("showId") Long showId
    );

    // Get total bookings count for a movie
    @Query("SELECT COUNT(b) FROM Booking b " +
           "WHERE b.show.movie.id = :movieId " +
           "AND b.status = 'CONFIRMED'")
    Long getTotalBookingsByMovie(
            @Param("movieId") Long movieId
    );

    // Find recent bookings within last N hours
    @Query("SELECT b FROM Booking b " +
           "WHERE b.bookedAt >= :fromTime " +
           "ORDER BY b.bookedAt DESC")
    List<Booking> findRecentBookings(
            @Param("fromTime") LocalDateTime fromTime
    );

    // Check if user already booked same show
    Boolean existsByUserIdAndShowIdAndStatus(
            Long userId,
            Long showId,
            BookingStatus status
    );
}