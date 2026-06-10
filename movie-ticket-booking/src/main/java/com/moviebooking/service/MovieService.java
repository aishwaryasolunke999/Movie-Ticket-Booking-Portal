package com.moviebooking.service;

import com.moviebooking.dto.request.MovieRequest;
import com.moviebooking.dto.response.MovieResponse;

import java.util.List;

public interface MovieService {

    // Create new movie — Admin only
    MovieResponse createMovie(MovieRequest request);

    // Update existing movie — Admin only
    MovieResponse updateMovie(Long id, MovieRequest request);

    // Delete movie — Admin only
    void deleteMovie(Long id);

    // Get movie by ID — Admin + Customer
    MovieResponse getMovieById(Long id);

    // Get all movies — Admin + Customer
    List<MovieResponse> getAllMovies();

    // Get movies by genre — Admin + Customer
    List<MovieResponse> getMoviesByGenre(String genre);

    // Get movies by language — Admin + Customer
    List<MovieResponse> getMoviesByLanguage(String language);

    // Search movies by keyword — Admin + Customer
    List<MovieResponse> searchMovies(String keyword);

    // Get top rated movies — Admin + Customer
    List<MovieResponse> getTopRatedMovies();
}