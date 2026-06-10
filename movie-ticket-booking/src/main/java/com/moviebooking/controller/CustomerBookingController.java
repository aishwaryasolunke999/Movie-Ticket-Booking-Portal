  package com.moviebooking.controller;

import com.moviebooking.dto.request.BookingRequest;
import com.moviebooking.dto.response.BookingResponse;
import com.moviebooking.dto.response.ShowResponse;
import com.moviebooking.entity.User;
import com.moviebooking.repository.UserRepository;
import com.moviebooking.service.BookingService;
import com.moviebooking.service.ShowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@PreAuthorize("hasRole('CUSTOMER')")
@Tag(name = "Customer - Bookings",
     description = "Customer Booking & Show APIs")
@SecurityRequirement(name = "bearerAuth")
public class CustomerBookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ShowService showService;

    @Autowired
    private UserRepository userRepository;

   
    private Long getLoggedInUserId() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                    new RuntimeException("User not found")
                );
        return user.getId();
    }

    // ─── BROWSE AVAILABLE SHOWS ──────────────────────────
    @GetMapping("/shows/available")
    @Operation(summary = "Get all available shows")
    public ResponseEntity<List<ShowResponse>> getAvailableShows() {

        List<ShowResponse> response =
                showService.getAvailableShows();
        return ResponseEntity.ok(response);
    }

    // ─── GET SHOWS BY MOVIE ──────────────────────────────
    @GetMapping("/shows/movie/{movieId}")
    @Operation(summary = "Get available shows for a movie")
    public ResponseEntity<List<ShowResponse>> getShowsByMovie(
            @PathVariable Long movieId) {

        List<ShowResponse> response =
                showService.getAvailableShowsByMovie(movieId);
        return ResponseEntity.ok(response);
    }

    // ─── GET SHOW BY ID ──────────────────────────────────
    @GetMapping("/shows/{showId}")
    @Operation(summary = "Get show details by ID")
    public ResponseEntity<ShowResponse> getShowById(
            @PathVariable Long showId) {

        ShowResponse response = showService.getShowById(showId);
        return ResponseEntity.ok(response);
    }

    // ─── BOOK TICKETS ────────────────────────────────────
    @PostMapping("/bookings")
    @Operation(summary = "Book tickets for a show")
    public ResponseEntity<BookingResponse> bookTickets(
            @Valid @RequestBody BookingRequest request) {

        Long userId = getLoggedInUserId();
        BookingResponse response =
                bookingService.bookTickets(request, userId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // ─── CANCEL BOOKING ──────────────────────────────────
    @PutMapping("/bookings/{bookingId}/cancel")
    @Operation(summary = "Cancel a booking")
    public ResponseEntity<BookingResponse> cancelBooking(
            @PathVariable Long bookingId) {

        Long userId = getLoggedInUserId();
        BookingResponse response =
                bookingService.cancelBooking(bookingId, userId);
        return ResponseEntity.ok(response);
    }

    // ─── GET BOOKING BY ID ───────────────────────────────
    @GetMapping("/bookings/{bookingId}")
    @Operation(summary = "Get booking details by ID")
    public ResponseEntity<BookingResponse> getBookingById(
            @PathVariable Long bookingId) {

        Long userId = getLoggedInUserId();
        BookingResponse response =
                bookingService.getBookingById(bookingId, userId);
        return ResponseEntity.ok(response);
    }

    // ─── GET ALL MY BOOKINGS ─────────────────────────────
    @GetMapping("/bookings")
    @Operation(summary = "Get all my bookings")
    public ResponseEntity<List<BookingResponse>> getMyBookings() {

        Long userId = getLoggedInUserId();
        List<BookingResponse> response =
                bookingService.getBookingsByUser(userId);
        return ResponseEntity.ok(response);
    }

    // ─── GET MY BOOKING HISTORY ──────────────────────────
    @GetMapping("/bookings/history")
    @Operation(summary = "Get my full booking history")
    public ResponseEntity<List<BookingResponse>> getBookingHistory() {

        Long userId = getLoggedInUserId();
        List<BookingResponse> response =
                bookingService.getBookingHistoryByUser(userId);
        return ResponseEntity.ok(response);
    }

    // ─── GET MY CONFIRMED BOOKINGS ───────────────────────
    @GetMapping("/bookings/confirmed")
    @Operation(summary = "Get my confirmed bookings")
    public ResponseEntity<List<BookingResponse>> getConfirmedBookings() {

        Long userId = getLoggedInUserId();
        List<BookingResponse> response =
                bookingService.getConfirmedBookingsByUser(userId);
        return ResponseEntity.ok(response);
    }

    // ─── GET MY CANCELLED BOOKINGS ───────────────────────
    @GetMapping("/bookings/cancelled")
    @Operation(summary = "Get my cancelled bookings")
    public ResponseEntity<List<BookingResponse>> getCancelledBookings() {

        Long userId = getLoggedInUserId();
        List<BookingResponse> response =
                bookingService.getCancelledBookingsByUser(userId);
        return ResponseEntity.ok(response);
    }
}