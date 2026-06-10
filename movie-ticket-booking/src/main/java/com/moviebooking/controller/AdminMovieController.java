package com.moviebooking.controller;

import com.moviebooking.dto.request.MovieRequest;
import com.moviebooking.dto.response.MovieResponse;
import com.moviebooking.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/movies")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin - Movies", description = "Admin Movie Management APIs")
@SecurityRequirement(name = "bearerAuth")
public class AdminMovieController {

    @Autowired
    private MovieService movieService;

    // ─── CREATE MOVIE ────────────────────────────────────
    @PostMapping
    @Operation(summary = "Create new movie")
    public ResponseEntity<MovieResponse> createMovie(
            @Valid @RequestBody MovieRequest request) {

        MovieResponse response = movieService.createMovie(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // ─── UPDATE MOVIE ────────────────────────────────────
    @PutMapping("/{id}")
    @Operation(summary = "Update existing movie")
    public ResponseEntity<MovieResponse> updateMovie(
            @PathVariable Long id,
            @Valid @RequestBody MovieRequest request) {

        MovieResponse response = movieService.updateMovie(id, request);
        return ResponseEntity.ok(response);
    }

    // ─── DELETE MOVIE ────────────────────────────────────
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete movie by ID")
    public ResponseEntity<String> deleteMovie(
            @PathVariable Long id) {

        movieService.deleteMovie(id);
        return ResponseEntity.ok(
            "Movie deleted successfully with id: " + id
        );
    }

    // ─── GET MOVIE BY ID ─────────────────────────────────
    @GetMapping("/{id}")
    @Operation(summary = "Get movie by ID")
    public ResponseEntity<MovieResponse> getMovieById(
            @PathVariable Long id) {

        MovieResponse response = movieService.getMovieById(id);
        return ResponseEntity.ok(response);
    }

    // ─── GET ALL MOVIES ──────────────────────────────────
    @GetMapping
    @Operation(summary = "Get all movies")
    public ResponseEntity<List<MovieResponse>> getAllMovies() {

        List<MovieResponse> response = movieService.getAllMovies();
        return ResponseEntity.ok(response);
    }

    // ─── GET MOVIES BY GENRE ─────────────────────────────
    @GetMapping("/genre/{genre}")
    @Operation(summary = "Get movies by genre")
    public ResponseEntity<List<MovieResponse>> getMoviesByGenre(
            @PathVariable String genre) {

        List<MovieResponse> response =
                movieService.getMoviesByGenre(genre);
        return ResponseEntity.ok(response);
    }

    // ─── GET MOVIES BY LANGUAGE ──────────────────────────
    @GetMapping("/language/{language}")
    @Operation(summary = "Get movies by language")
    public ResponseEntity<List<MovieResponse>> getMoviesByLanguage(
            @PathVariable String language) {

        List<MovieResponse> response =
                movieService.getMoviesByLanguage(language);
        return ResponseEntity.ok(response);
    }

    // ─── SEARCH MOVIES ───────────────────────────────────
    @GetMapping("/search")
    @Operation(summary = "Search movies by keyword")
    public ResponseEntity<List<MovieResponse>> searchMovies(
            @RequestParam String keyword) {

        List<MovieResponse> response =
                movieService.searchMovies(keyword);
        return ResponseEntity.ok(response);
    }

    // ─── GET TOP RATED MOVIES ────────────────────────────
    @GetMapping("/top-rated")
    @Operation(summary = "Get top rated movies")
    public ResponseEntity<List<MovieResponse>> getTopRatedMovies() {

        List<MovieResponse> response =
                movieService.getTopRatedMovies();
        return ResponseEntity.ok(response);
    }
}