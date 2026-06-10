package com.moviebooking.service.impl;

import com.moviebooking.dto.request.BookingRequest;
import com.moviebooking.dto.response.BookingResponse;
import com.moviebooking.entity.Booking;
import com.moviebooking.entity.Seat;
import com.moviebooking.entity.Show;
import com.moviebooking.entity.User;
import com.moviebooking.enums.BookingStatus;
import com.moviebooking.enums.SeatStatus;
import com.moviebooking.exception.ResourceNotFoundException;
import com.moviebooking.exception.SeatNotAvailableException;
import com.moviebooking.repository.BookingRepository;
import com.moviebooking.repository.SeatRepository;
import com.moviebooking.repository.ShowRepository;
import com.moviebooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements
        com.moviebooking.service.BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public BookingResponse bookTickets(BookingRequest request,
                                        Long userId) {

        // Step 1: Validate user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "User not found with id: " + userId
                    )
                );

        // Step 2: Validate show exists
        Show show = showRepository.findById(request.getShowId())
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Show not found with id: " + request.getShowId()
                    )
                );

        // Step 3: Fetch requested seats
        List<Seat> seats = seatRepository
                .findByIdInAndShowId(
                    request.getSeatIds(),
                    request.getShowId()
                );

        // Step 4: Validate all seats found
        if (seats.size() != request.getSeatIds().size()) {
            throw new ResourceNotFoundException(
                "One or more seats not found for this show"
            );
        }

        // Step 5: Check all seats are AVAILABLE
        List<Seat> unavailableSeats = seats.stream()
                .filter(s -> s.getStatus() != SeatStatus.AVAILABLE)
                .collect(Collectors.toList());

        if (!unavailableSeats.isEmpty()) {
            String seatNumbers = unavailableSeats.stream()
                    .map(Seat::getSeatNumber)
                    .collect(Collectors.joining(", "));
            throw new SeatNotAvailableException(
                "Seats not available: " + seatNumbers +
                ". Please select other seats."
            );
        }

        // Step 6: Calculate total amount
        double totalAmount = seats.size() * show.getTicketPrice();

        // Step 7: Create booking
        Booking booking = Booking.builder()
                .user(user)
                .show(show)
                .seats(seats)
                .totalAmount(totalAmount)
                .status(BookingStatus.CONFIRMED)
                .build();

        // Step 8: Save booking
        Booking savedBooking = bookingRepository.save(booking);

        // Step 9: Mark all seats as BOOKED
        seats.forEach(seat -> {
            seat.setStatus(SeatStatus.BOOKED);
            seat.setLockedAt(null);
            seat.setLockedBy(null);
        });
        seatRepository.saveAll(seats);

        // Step 10: Update available seats count
        show.setAvailableSeats(
            show.getAvailableSeats() - seats.size()
        );
        showRepository.save(show);

        return mapToBookingResponse(savedBooking);
    }

    @Override
    @Transactional
    public BookingResponse cancelBooking(Long bookingId, Long userId) {

        // Step 1: Find booking and verify it belongs to user
        Booking booking = bookingRepository
                .findByIdAndUserId(bookingId, userId)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Booking not found with id: " + bookingId
                    )
                );

        // Step 2: Check booking is CONFIRMED
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new RuntimeException(
                "Booking is already cancelled"
            );
        }

        // Step 3: Release all booked seats back to AVAILABLE
        List<Seat> seats = booking.getSeats();
        seats.forEach(seat -> {
            seat.setStatus(SeatStatus.AVAILABLE);
            seat.setLockedAt(null);
            seat.setLockedBy(null);
        });
        seatRepository.saveAll(seats);

        // Step 4: Update show available seats
        Show show = booking.getShow();
        show.setAvailableSeats(
            show.getAvailableSeats() + seats.size()
        );
        showRepository.save(show);

        // Step 5: Update booking status
        booking.setStatus(BookingStatus.CANCELLED);
        booking.setCancelledAt(LocalDateTime.now());
        Booking cancelledBooking = bookingRepository.save(booking);

        return mapToBookingResponse(cancelledBooking);
    }

    @Override
    public BookingResponse getBookingById(Long bookingId,
                                           Long userId) {
        Booking booking = bookingRepository
                .findByIdAndUserId(bookingId, userId)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Booking not found with id: " + bookingId
                    )
                );
        return mapToBookingResponse(booking);
    }

    @Override
    public List<BookingResponse> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId)
                .stream()
                .map(this::mapToBookingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> getConfirmedBookingsByUser(
            Long userId) {
        return bookingRepository
                .findByUserIdAndStatus(userId, BookingStatus.CONFIRMED)
                .stream()
                .map(this::mapToBookingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> getCancelledBookingsByUser(
            Long userId) {
        return bookingRepository
                .findByUserIdAndStatus(userId, BookingStatus.CANCELLED)
                .stream()
                .map(this::mapToBookingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> getBookingHistoryByUser(
            Long userId) {
        return bookingRepository.findBookingHistoryByUser(userId)
                .stream()
                .map(this::mapToBookingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> getBookingsByShow(Long showId) {
        return bookingRepository.findByShowId(showId)
                .stream()
                .map(this::mapToBookingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Double getTotalRevenueByShow(Long showId) {
        Double revenue = bookingRepository
                .getTotalRevenueByShow(showId);
        return revenue != null ? revenue : 0.0;
    }

    // Helper — map Booking entity to BookingResponse
    private BookingResponse mapToBookingResponse(Booking booking) {

        List<String> seatNumbers = booking.getSeats()
                .stream()
                .map(Seat::getSeatNumber)
                .collect(Collectors.toList());

        return BookingResponse.builder()
                .id(booking.getId())
                .userId(booking.getUser().getId())
                .userName(booking.getUser().getName())
                .showId(booking.getShow().getId())
                .movieTitle(booking.getShow().getMovie().getTitle())
                .hallName(booking.getShow().getHallName())
                .showDate(booking.getShow().getShowDate().toString())
                .showTime(booking.getShow().getShowTime().toString())
                .seatNumbers(seatNumbers)
                .totalSeats(seatNumbers.size())
                .totalAmount(booking.getTotalAmount())
                .status(booking.getStatus())
                .bookedAt(booking.getBookedAt())
                .cancelledAt(booking.getCancelledAt())
                .build();
    }
}