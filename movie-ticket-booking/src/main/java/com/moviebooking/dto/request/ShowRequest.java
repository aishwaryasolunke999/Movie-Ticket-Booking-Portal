package com.moviebooking.dto.request;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ShowRequest {

    @NotNull(message = "Movie ID is required")
    private Long movieId;

    @NotBlank(message = "Hall name is required")
    @Size(min = 2, max = 50, message = "Hall name must be between 2 and 50 characters")
    private String hallName;

    @NotNull(message = "Show date is required")
    @FutureOrPresent(message = "Show date must be today or in the future")
    private LocalDate showDate;

    @NotNull(message = "Show time is required")
    private LocalTime showTime;

    @NotNull(message = "Total seats is required")
    @Min(value = 1, message = "Total seats must be at least 1")
    @Max(value = 500, message = "Total seats cannot exceed 500")
    private Integer totalSeats;

    @NotNull(message = "Ticket price is required")
    @DecimalMin(value = "1.0", message = "Ticket price must be at least 1.0")
    private Double ticketPrice;

    public ShowRequest() {}
    public ShowRequest(Long movieId, String hallName, LocalDate showDate,
                       LocalTime showTime, Integer totalSeats, Double ticketPrice) {
        this.movieId = movieId; this.hallName = hallName;
        this.showDate = showDate; this.showTime = showTime;
        this.totalSeats = totalSeats; this.ticketPrice = ticketPrice;
    }
    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }
    public String getHallName() { return hallName; }
    public void setHallName(String hallName) { this.hallName = hallName; }
    public LocalDate getShowDate() { return showDate; }
    public void setShowDate(LocalDate showDate) { this.showDate = showDate; }
    public LocalTime getShowTime() { return showTime; }
    public void setShowTime(LocalTime showTime) { this.showTime = showTime; }
    public Integer getTotalSeats() { return totalSeats; }
    public void setTotalSeats(Integer totalSeats) { this.totalSeats = totalSeats; }
    public Double getTicketPrice() { return ticketPrice; }
    public void setTicketPrice(Double ticketPrice) { this.ticketPrice = ticketPrice; }
}
