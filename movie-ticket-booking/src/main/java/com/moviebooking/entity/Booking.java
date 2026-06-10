package com.moviebooking.entity;

import com.moviebooking.enums.BookingStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "booking_seats",
        joinColumns = @JoinColumn(name = "booking_id"),
        inverseJoinColumns = @JoinColumn(name = "seat_id")
    )
    private List<Seat> seats;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    @Column(name = "booked_at")
    private LocalDateTime bookedAt;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @PrePersist
    public void prePersist() {
        this.bookedAt = LocalDateTime.now();
        this.status = BookingStatus.CONFIRMED;
    }

    public Booking() {}

    public Booking(Long id, User user, Show show, List<Seat> seats,
                   Double totalAmount, BookingStatus status,
                   LocalDateTime bookedAt, LocalDateTime cancelledAt) {
        this.id = id; this.user = user; this.show = show;
        this.seats = seats; this.totalAmount = totalAmount;
        this.status = status; this.bookedAt = bookedAt;
        this.cancelledAt = cancelledAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Show getShow() { return show; }
    public void setShow(Show show) { this.show = show; }
    public List<Seat> getSeats() { return seats; }
    public void setSeats(List<Seat> seats) { this.seats = seats; }
    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }
    public LocalDateTime getBookedAt() { return bookedAt; }
    public void setBookedAt(LocalDateTime bookedAt) { this.bookedAt = bookedAt; }
    public LocalDateTime getCancelledAt() { return cancelledAt; }
    public void setCancelledAt(LocalDateTime cancelledAt) { this.cancelledAt = cancelledAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id; private User user; private Show show;
        private List<Seat> seats; private Double totalAmount;
        private BookingStatus status; private LocalDateTime bookedAt;
        private LocalDateTime cancelledAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder user(User user) { this.user = user; return this; }
        public Builder show(Show show) { this.show = show; return this; }
        public Builder seats(List<Seat> seats) { this.seats = seats; return this; }
        public Builder totalAmount(Double totalAmount) { this.totalAmount = totalAmount; return this; }
        public Builder status(BookingStatus status) { this.status = status; return this; }
        public Builder bookedAt(LocalDateTime bookedAt) { this.bookedAt = bookedAt; return this; }
        public Builder cancelledAt(LocalDateTime cancelledAt) { this.cancelledAt = cancelledAt; return this; }
        public Booking build() {
            return new Booking(id, user, show, seats, totalAmount,
                               status, bookedAt, cancelledAt);
        }
    }
}
