package com.moviebooking.controller;

import com.moviebooking.dto.request.ShowRequest;
import com.moviebooking.dto.response.BookingResponse;
import com.moviebooking.dto.response.ShowResponse;
import com.moviebooking.service.BookingService;
import com.moviebooking.service.ShowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/admin/shows")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin - Shows", description = "Admin Show Management APIs")
@SecurityRequirement(name = "bearerAuth")
public class AdminShowController {

    @Autowired
    private ShowService showService;

    @Autowired
    private BookingService bookingService;

    // ─── CREATE SHOW ─────────────────────────────────────
    @PostMapping
    @Operation(summary = "Create new show with auto seat generation")
    public ResponseEntity<ShowResponse> createShow(
            @Valid @RequestBody ShowRequest request) {

        ShowResponse response = showService.createShow(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // ─── UPDATE SHOW ─────────────────────────────────────
    @PutMapping("/{id}")
    @Operation(summary = "Update existing show")
    public ResponseEntity<ShowResponse> updateShow(
            @PathVariable Long id,
            @Valid @RequestBody ShowRequest request) {

        ShowResponse response = showService.updateShow(id, request);
        return ResponseEntity.ok(response);
    }

    // ─── DELETE SHOW ─────────────────────────────────────
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete show by ID")
    public ResponseEntity<String> deleteShow(
            @PathVariable Long id) {

        showService.deleteShow(id);
        return ResponseEntity.ok(
            "Show deleted successfully with id: " + id
        );
    }

    // ─── GET SHOW BY ID ──────────────────────────────────
    @GetMapping("/{id}")
    @Operation(summary = "Get show by ID")
    public ResponseEntity<ShowResponse> getShowById(
            @PathVariable Long id) {

        ShowResponse response = showService.getShowById(id);
        return ResponseEntity.ok(response);
    }

    // ─── GET ALL SHOWS ───────────────────────────────────
    @GetMapping
    @Operation(summary = "Get all shows")
    public ResponseEntity<List<ShowResponse>> getAllShows() {

        List<ShowResponse> response = showService.getAllShows();
        return ResponseEntity.ok(response);
    }

    // ─── GET SHOWS BY MOVIE ──────────────────────────────
    @GetMapping("/movie/{movieId}")
    @Operation(summary = "Get all shows for a movie")
    public ResponseEntity<List<ShowResponse>> getShowsByMovie(
            @PathVariable Long movieId) {

        List<ShowResponse> response =
                showService.getShowsByMovie(movieId);
        return ResponseEntity.ok(response);
    }

    // ─── GET SHOWS BY DATE ───────────────────────────────
    @GetMapping("/date/{date}")
    @Operation(summary = "Get shows by date (yyyy-MM-dd)")
    public ResponseEntity<List<ShowResponse>> getShowsByDate(
            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        List<ShowResponse> response =
                showService.getShowsByDate(date);
        return ResponseEntity.ok(response);
    }

    // ─── GET SHOWS BY MOVIE AND DATE ─────────────────────
    @GetMapping("/movie/{movieId}/date/{date}")
    @Operation(summary = "Get shows by movie and date")
    public ResponseEntity<List<ShowResponse>> getShowsByMovieAndDate(
            @PathVariable Long movieId,
            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        List<ShowResponse> response =
                showService.getShowsByMovieAndDate(movieId, date);
        return ResponseEntity.ok(response);
    }

    // ─── GET BOOKINGS FOR SHOW ───────────────────────────
    @GetMapping("/{id}/bookings")
    @Operation(summary = "Get all bookings for a show")
    public ResponseEntity<List<BookingResponse>> getBookingsByShow(
            @PathVariable Long id) {

        List<BookingResponse> response =
                bookingService.getBookingsByShow(id);
        return ResponseEntity.ok(response);
    }

    // ─── GET REVENUE FOR SHOW ────────────────────────────
    @GetMapping("/{id}/revenue")
    @Operation(summary = "Get total revenue for a show")
    public ResponseEntity<Double> getRevenueByShow(
            @PathVariable Long id) {

        Double revenue = bookingService.getTotalRevenueByShow(id);
        return ResponseEntity.ok(revenue);
    }
}