package com.moviebooking.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "shows")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(name = "hall_name", nullable = false)
    private String hallName;

    @Column(name = "show_date", nullable = false)
    private LocalDate showDate;

    @Column(name = "show_time", nullable = false)
    private LocalTime showTime;

    @Column(name = "total_seats", nullable = false)
    private Integer totalSeats;

    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats;

    @Column(name = "ticket_price", nullable = false)
    private Double ticketPrice;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Seat> seats;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.availableSeats = this.totalSeats;
    }

    public Show() {}

    public Show(Long id, Movie movie, String hallName, LocalDate showDate,
                LocalTime showTime, Integer totalSeats, Integer availableSeats,
                Double ticketPrice, LocalDateTime createdAt,
                List<Seat> seats, List<Booking> bookings) {
        this.id = id; this.movie = movie; this.hallName = hallName;
        this.showDate = showDate; this.showTime = showTime;
        this.totalSeats = totalSeats; this.availableSeats = availableSeats;
        this.ticketPrice = ticketPrice; this.createdAt = createdAt;
        this.seats = seats; this.bookings = bookings;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }
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
    public List<Seat> getSeats() { return seats; }
    public void setSeats(List<Seat> seats) { this.seats = seats; }
    public List<Booking> getBookings() { return bookings; }
    public void setBookings(List<Booking> bookings) { this.bookings = bookings; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id; private Movie movie; private String hallName;
        private LocalDate showDate; private LocalTime showTime;
        private Integer totalSeats; private Integer availableSeats;
        private Double ticketPrice; private LocalDateTime createdAt;
        private List<Seat> seats; private List<Booking> bookings;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder movie(Movie movie) { this.movie = movie; return this; }
        public Builder hallName(String hallName) { this.hallName = hallName; return this; }
        public Builder showDate(LocalDate showDate) { this.showDate = showDate; return this; }
        public Builder showTime(LocalTime showTime) { this.showTime = showTime; return this; }
        public Builder totalSeats(Integer totalSeats) { this.totalSeats = totalSeats; return this; }
        public Builder availableSeats(Integer availableSeats) { this.availableSeats = availableSeats; return this; }
        public Builder ticketPrice(Double ticketPrice) { this.ticketPrice = ticketPrice; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder seats(List<Seat> seats) { this.seats = seats; return this; }
        public Builder bookings(List<Booking> bookings) { this.bookings = bookings; return this; }
        public Show build() {
            return new Show(id, movie, hallName, showDate, showTime,
                            totalSeats, availableSeats, ticketPrice,
                            createdAt, seats, bookings);
        }
    }
}
