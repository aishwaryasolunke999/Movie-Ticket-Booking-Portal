package com.moviebooking.dto.response;

import java.time.LocalDateTime;

public class MovieResponse {

    private Long id;
    private String title;
    private String genre;
    private Integer duration;
    private String language;
    private Double rating;
    private String description;
    private String releaseDate;
    private LocalDateTime createdAt;

    public MovieResponse() {}
    public MovieResponse(Long id, String title, String genre, Integer duration,
                         String language, Double rating, String description,
                         String releaseDate, LocalDateTime createdAt) {
        this.id = id; this.title = title; this.genre = genre;
        this.duration = duration; this.language = language;
        this.rating = rating; this.description = description;
        this.releaseDate = releaseDate; this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private Long id; private String title; private String genre;
        private Integer duration; private String language; private Double rating;
        private String description; private String releaseDate; private LocalDateTime createdAt;
        public Builder id(Long id) { this.id = id; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder genre(String genre) { this.genre = genre; return this; }
        public Builder duration(Integer duration) { this.duration = duration; return this; }
        public Builder language(String language) { this.language = language; return this; }
        public Builder rating(Double rating) { this.rating = rating; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder releaseDate(String releaseDate) { this.releaseDate = releaseDate; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public MovieResponse build() {
            return new MovieResponse(id, title, genre, duration, language,
                                     rating, description, releaseDate, createdAt);
        }
    }
}
