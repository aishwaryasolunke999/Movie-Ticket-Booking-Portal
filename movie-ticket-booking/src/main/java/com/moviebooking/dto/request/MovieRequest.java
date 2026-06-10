package com.moviebooking.dto.request;

import jakarta.validation.constraints.*;

public class MovieRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    private String title;

    @NotBlank(message = "Genre is required")
    private String genre;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Max(value = 600, message = "Duration cannot exceed 600 minutes")
    private Integer duration;

    @NotBlank(message = "Language is required")
    private String language;

    @NotNull(message = "Rating is required")
    @DecimalMin(value = "0.0", message = "Rating must be at least 0.0")
    @DecimalMax(value = "10.0", message = "Rating cannot exceed 10.0")
    private Double rating;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotBlank(message = "Release date is required")
    private String releaseDate;

    public MovieRequest() {}
    public MovieRequest(String title, String genre, Integer duration,
                        String language, Double rating, String description, String releaseDate) {
        this.title = title; this.genre = genre; this.duration = duration;
        this.language = language; this.rating = rating;
        this.description = description; this.releaseDate = releaseDate;
    }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getReleaseDate() { return releaseDate; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }
}
