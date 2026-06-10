package com.moviebooking.service.impl;

import com.moviebooking.dto.request.ShowRequest;
import com.moviebooking.dto.response.ShowResponse;
import com.moviebooking.entity.Movie;
import com.moviebooking.entity.Seat;
import com.moviebooking.entity.Show;
import com.moviebooking.exception.ResourceAlreadyExistsException;
import com.moviebooking.exception.ResourceNotFoundException;
import com.moviebooking.repository.MovieRepository;
import com.moviebooking.repository.SeatRepository;
import com.moviebooking.repository.ShowRepository;
import com.moviebooking.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowServiceImpl implements ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Override
    @Transactional
    public ShowResponse createShow(ShowRequest request) {

        // Step 1: Check movie exists
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Movie not found with id: " + request.getMovieId()
                    )
                );

        // Step 2: Check hall not already booked
        // at same date and time
        if (showRepository.existsByHallNameAndShowDateAndShowTime(
                request.getHallName(),
                request.getShowDate(),
                request.getShowTime())) {
            throw new ResourceAlreadyExistsException(
                "Hall " + request.getHallName() +
                " is already booked on " + request.getShowDate() +
                " at " + request.getShowTime()
            );
        }

        // Step 3: Build Show entity
        Show show = Show.builder()
                .movie(movie)
                .hallName(request.getHallName())
                .showDate(request.getShowDate())
                .showTime(request.getShowTime())
                .totalSeats(request.getTotalSeats())
                .availableSeats(request.getTotalSeats())
                .ticketPrice(request.getTicketPrice())
                .build();

        // Step 4: Save show to DB
        Show savedShow = showRepository.save(show);

        // Step 5: Auto generate seats
        List<Seat> seats = generateSeats(savedShow,
                                         request.getTotalSeats());
        seatRepository.saveAll(seats);

        return mapToShowResponse(savedShow);
    }

    @Override
    @Transactional
    public ShowResponse updateShow(Long id, ShowRequest request) {

        // Find existing show
        Show show = showRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Show not found with id: " + id
                    )
                );

        // Find movie
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Movie not found with id: " + request.getMovieId()
                    )
                );

        // Update show fields
        show.setMovie(movie);
        show.setHallName(request.getHallName());
        show.setShowDate(request.getShowDate());
        show.setShowTime(request.getShowTime());
        show.setTicketPrice(request.getTicketPrice());

        Show updatedShow = showRepository.save(show);

        return mapToShowResponse(updatedShow);
    }

    @Override
    @Transactional
    public void deleteShow(Long id) {

        Show show = showRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Show not found with id: " + id
                    )
                );

        showRepository.delete(show);
    }

    @Override
    public ShowResponse getShowById(Long id) {

        Show show = showRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Show not found with id: " + id
                    )
                );

        return mapToShowResponse(show);
    }

    @Override
    public List<ShowResponse> getAllShows() {
        return showRepository.findAll()
                .stream()
                .map(this::mapToShowResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowResponse> getShowsByMovie(Long movieId) {
        return showRepository.findByMovieId(movieId)
                .stream()
                .map(this::mapToShowResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowResponse> getShowsByDate(LocalDate date) {
        return showRepository.findByShowDate(date)
                .stream()
                .map(this::mapToShowResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowResponse> getShowsByMovieAndDate(
            Long movieId, LocalDate date) {
        return showRepository.findByMovieIdAndShowDate(movieId, date)
                .stream()
                .map(this::mapToShowResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowResponse> getAvailableShows() {
        return showRepository.findAvailableShows()
                .stream()
                .map(this::mapToShowResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowResponse> getAvailableShowsByMovie(Long movieId) {
        return showRepository.findAvailableShowsByMovie(
                        movieId, LocalDate.now())
                .stream()
                .map(this::mapToShowResponse)
                .collect(Collectors.toList());
    }

    // ⭐ Auto generate seats for a show
    // totalSeats=30 → A1-A10, B1-B10, C1-C10
    private List<Seat> generateSeats(Show show, int totalSeats) {

        List<Seat> seats    = new ArrayList<>();
        int seatsPerRow     = 10;
        int totalRows       = (int) Math.ceil(
                                (double) totalSeats / seatsPerRow);
        int seatCount       = 0;

        for (int row = 0; row < totalRows; row++) {
            char rowLabel = (char) ('A' + row);

            for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {
                if (seatCount >= totalSeats) break;

                Seat seat = Seat.builder()
                        .show(show)
                        .seatNumber(rowLabel + String.valueOf(seatNum))
                        .build();

                seats.add(seat);
                seatCount++;
            }
        }
        return seats;
    }

    // Helper — map Show entity to ShowResponse
    public ShowResponse mapToShowResponse(Show show) {
        return ShowResponse.builder()
                .id(show.getId())
                .movieId(show.getMovie().getId())
                .movieTitle(show.getMovie().getTitle())
                .movieLanguage(show.getMovie().getLanguage())
                .hallName(show.getHallName())
                .showDate(show.getShowDate())
                .showTime(show.getShowTime())
                .totalSeats(show.getTotalSeats())
                .availableSeats(show.getAvailableSeats())
                .ticketPrice(show.getTicketPrice())
                .createdAt(show.getCreatedAt())
                .build();
    }
}