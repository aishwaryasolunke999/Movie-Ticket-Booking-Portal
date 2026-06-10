package com.moviebooking.controller;

import com.moviebooking.dto.response.SeatResponse;
import com.moviebooking.service.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@Tag(name = "Seats", description = "Seat Availability APIs")
@SecurityRequirement(name = "bearerAuth")
public class SeatController {

    @Autowired
    private SeatService seatService;

    // ─── GET ALL SEATS FOR SHOW ──────────────────────────
    @GetMapping("/show/{showId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @Operation(summary = "Get all seats for a show")
    public ResponseEntity<List<SeatResponse>> getSeatsByShow(
            @PathVariable Long showId) {

        List<SeatResponse> response =
                seatService.getSeatsByShow(showId);
        return ResponseEntity.ok(response);
    }

    // ─── GET AVAILABLE SEATS ─────────────────────────────
    @GetMapping("/available/{showId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @Operation(summary = "Get available seats for a show")
    public ResponseEntity<List<SeatResponse>> getAvailableSeats(
            @PathVariable Long showId) {

        List<SeatResponse> response =
                seatService.getAvailableSeatsByShow(showId);
        return ResponseEntity.ok(response);
    }

    // ─── GET BOOKED SEATS ────────────────────────────────
    @GetMapping("/booked/{showId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @Operation(summary = "Get booked seats for a show")
    public ResponseEntity<List<SeatResponse>> getBookedSeats(
            @PathVariable Long showId) {

        List<SeatResponse> response =
                seatService.getBookedSeatsByShow(showId);
        return ResponseEntity.ok(response);
    }

    // ─── GET LOCKED SEATS ────────────────────────────────
    @GetMapping("/locked/{showId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @Operation(summary = "Get locked seats for a show")
    public ResponseEntity<List<SeatResponse>> getLockedSeats(
            @PathVariable Long showId) {

        List<SeatResponse> response =
                seatService.getLockedSeatsByShow(showId);
        return ResponseEntity.ok(response);
    }

    // ─── COUNT AVAILABLE SEATS ───────────────────────────
    @GetMapping("/count/{showId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @Operation(summary = "Count available seats for a show")
    public ResponseEntity<Long> countAvailableSeats(
            @PathVariable Long showId) {

        Long count = seatService.countAvailableSeats(showId);
        return ResponseEntity.ok(count);
    }

    // ─── RELEASE MY LOCKED SEATS ─────────────────────────
    @DeleteMapping("/release/{showId}/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @Operation(summary = "Release locked seats for a user")
    public ResponseEntity<String> releaseLockedSeats(
            @PathVariable Long showId,
            @PathVariable Long userId) {

        seatService.releaseLockedSeats(showId, userId);
        return ResponseEntity.ok(
            "Locked seats released successfully"
        );
    }
}