package com.moviebooking.service;

import com.moviebooking.dto.request.ShowRequest;
import com.moviebooking.dto.response.ShowResponse;

import java.time.LocalDate;
import java.util.List;

public interface ShowService {

    // Create new show — Admin only
    ShowResponse createShow(ShowRequest request);

    // Update existing show — Admin only
    ShowResponse updateShow(Long id, ShowRequest request);

    // Delete show — Admin only
    void deleteShow(Long id);

    // Get show by ID — Admin + Customer
    ShowResponse getShowById(Long id);

    // Get all shows — Admin only
    List<ShowResponse> getAllShows();

    // Get shows by movie ID — Admin + Customer
    List<ShowResponse> getShowsByMovie(Long movieId);

    // Get shows by date — Admin + Customer
    List<ShowResponse> getShowsByDate(LocalDate date);

    // Get shows by movie and date — Admin + Customer
    List<ShowResponse> getShowsByMovieAndDate(Long movieId, LocalDate date);

    // Get available shows — Customer
    List<ShowResponse> getAvailableShows();

    // Get available shows by movie — Customer
    List<ShowResponse> getAvailableShowsByMovie(Long movieId);
}