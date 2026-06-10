package com.moviebooking.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ShowResponse {

    private Long id;
    private Long movieId;
    private String movieTitle;
    private String movieLanguage;
    private String hallName;
    private LocalDate showDate;
    private LocalTime showTime;
    private Integer totalSeats;
    private Integer availableSeats;
    private Double ticketPrice;
    private LocalDateTime createdAt;

    public ShowResponse() {}
    public ShowResponse(Long id, Long movieId, String movieTitle, String movieLanguage,
                        String hallName, LocalDate showDate, LocalTime showTime,
                        Integer totalSeats, Integer availableSeats, Double ticketPrice,
                        LocalDateTime createdAt) {
        this.id = id; this.movieId = movieId; this.movieTitle = movieTitle;
        this.movieLanguage = movieLanguage; this.hallName = hallName;
        this.showDate = showDate; this.showTime = showTime;
        this.totalSeats = totalSeats; this.availableSeats = availableSeats;
        this.ticketPrice = ticketPrice; this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }
    public String getMovieTitle() { return movieTitle; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }
    public String getMovieLanguage() { return movieLanguage; }
    public void setMovieLanguage(String movieLanguage) { this.movieLanguage = movieLanguage; }
    public String getHallName() { return hallName; }
    public void setHallName(String hallName) { this.hallName = hallName; }
    public LocalDate getShowDate() { return showDate; }
    public void setShowDate(LocalDate showDate) { this.showDate = showDate; }
    public LocalTime getShowTime() { return showTime; }
    public void setShowTime(LocalTime showTime) { this.showTime = showTime; }
    public Integer getTotalSeats() { return totalSeats; }
    public void setTotalSeats(Integer totalSeats) { this.totalSeats = totalSeats; }
    public Integer getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(Integer availableSeats) { this.availableSeats = availableSeats; }
    public Double getTicketPrice() { return ticketPrice; }
    public void setTicketPrice(Double ticketPrice) { this.ticketPrice = ticketPrice; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private Long id; private Long movieId; private String movieTitle;
        private String movieLanguage; private String hallName;
        private LocalDate showDate; private LocalTime showTime;
        private Integer totalSeats; private Integer availableSeats;
        private Double ticketPrice; private LocalDateTime createdAt;
        public Builder id(Long id) { this.id = id; return this; }
        public Builder movieId(Long movieId) { this.movieId = movieId; return this; }
        public Builder movieTitle(String movieTitle) { this.movieTitle = movieTitle; return this; }
        public Builder movieLanguage(String movieLanguage) { this.movieLanguage = movieLanguage; return this; }
        public Builder hallName(String hallName) { this.hallName = hallName; return this; }
        public Builder showDate(LocalDate showDate) { this.showDate = showDate; return this; }
        public Builder showTime(LocalTime showTime) { this.showTime = showTime; return this; }
        public Builder totalSeats(Integer totalSeats) { this.totalSeats = totalSeats; return this; }
        public Builder availableSeats(Integer availableSeats) { this.availableSeats = availableSeats; return this; }
        public Builder ticketPrice(Double ticketPrice) { this.ticketPrice = ticketPrice; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public ShowResponse build() {
            return new ShowResponse(id, movieId, movieTitle, movieLanguage, hallName,
                                    showDate, showTime, totalSeats, availableSeats,
                                    ticketPrice, createdAt);
        }
    }
}
