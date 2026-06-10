package com.moviebooking.repository;

import com.moviebooking.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    // Find all shows for a specific movie
    List<Show> findByMovieId(Long movieId);

    // Find all shows on a specific date
    List<Show> findByShowDate(LocalDate showDate);

    // Find shows by movie and date
    List<Show> findByMovieIdAndShowDate(Long movieId, LocalDate showDate);

    // Find shows by hall name
    List<Show> findByHallName(String hallName);

    // Find shows with available seats
    @Query("SELECT s FROM Show s WHERE s.availableSeats > 0")
    List<Show> findAvailableShows();

    // Find available shows for a specific movie
    @Query("SELECT s FROM Show s WHERE s.movie.id = :movieId " +
           "AND s.availableSeats > 0 " +
           "AND s.showDate >= :today")
    List<Show> findAvailableShowsByMovie(
            @Param("movieId") Long movieId,
            @Param("today") LocalDate today
    );

    // Find shows by movie and date with available seats
    @Query("SELECT s FROM Show s WHERE s.movie.id = :movieId " +
           "AND s.showDate = :showDate " +
           "AND s.availableSeats > 0")
    List<Show> findAvailableShowsByMovieAndDate(
            @Param("movieId") Long movieId,
            @Param("showDate") LocalDate showDate
    );

    // Check if show exists for same hall, date and time
    Boolean existsByHallNameAndShowDateAndShowTime(
            String hallName,
            LocalDate showDate,
            java.time.LocalTime showTime
    );
}