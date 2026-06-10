package com.moviebooking.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private Double rating;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "release_date")
    private String releaseDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Show> shows;

    @PrePersist
    public void prePersist() { this.createdAt = LocalDateTime.now(); }

    public Movie() {}

    public Movie(Long id, String title, String genre, Integer duration,
                 String language, Double rating, String description,
                 String releaseDate, LocalDateTime createdAt, List<Show> shows) {
        this.id = id; this.title = title; this.genre = genre;
        this.duration = duration; this.language = language;
        this.rating = rating; this.description = description;
        this.releaseDate = releaseDate; this.createdAt = createdAt;
        this.shows = shows;
    }

    public Long getId() 
    { return id; }
    
    public void setId(Long id) 
    { this.id = id; }
    
    public String getTitle() 
    { return title; }
    
    public void setTitle(String title) 
    { this.title = title; }
    
    public String getGenre()
    { return genre; }
    
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
    public List<Show> getShows() { return shows; }
    public void setShows(List<Show> shows) { this.shows = shows; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id; private String title; private String genre;
        private Integer duration; private String language; private Double rating;
        private String description; private String releaseDate;
        private LocalDateTime createdAt; private List<Show> shows;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder genre(String genre) { this.genre = genre; return this; }
        public Builder duration(Integer duration) { this.duration = duration; return this; }
        public Builder language(String language) { this.language = language; return this; }
        public Builder rating(Double rating) { this.rating = rating; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder releaseDate(String releaseDate) { this.releaseDate = releaseDate; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder shows(List<Show> shows) { this.shows = shows; return this; }
        public Movie build() {
            return new Movie(id, title, genre, duration, language, rating,
                             description, releaseDate, createdAt, shows);
        }
    }
}
