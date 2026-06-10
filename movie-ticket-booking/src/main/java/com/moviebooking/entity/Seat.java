package com.moviebooking.entity;

import com.moviebooking.enums.SeatStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus status;

    @Column(name = "locked_at")
    private LocalDateTime lockedAt;

    @Column(name = "locked_by")
    private Long lockedBy;

    @Version
    private Long version;

    @PrePersist
    public void prePersist() { this.status = SeatStatus.AVAILABLE; }

    public Seat() {}

    public Seat(Long id, Show show, String seatNumber, SeatStatus status,
                LocalDateTime lockedAt, Long lockedBy, Long version) {
        this.id = id; this.show = show; this.seatNumber = seatNumber;
        this.status = status; this.lockedAt = lockedAt;
        this.lockedBy = lockedBy; this.version = version;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Show getShow() { return show; }
    public void setShow(Show show) { this.show = show; }
    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }
    public SeatStatus getStatus() { return status; }
    public void setStatus(SeatStatus status) { this.status = status; }
    public LocalDateTime getLockedAt() { return lockedAt; }
    public void setLockedAt(LocalDateTime lockedAt) { this.lockedAt = lockedAt; }
    public Long getLockedBy() { return lockedBy; }
    public void setLockedBy(Long lockedBy) { this.lockedBy = lockedBy; }
    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id; private Show show; private String seatNumber;
        private SeatStatus status; private LocalDateTime lockedAt;
        private Long lockedBy; private Long version;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder show(Show show) { this.show = show; return this; }
        public Builder seatNumber(String seatNumber) { this.seatNumber = seatNumber; return this; }
        public Builder status(SeatStatus status) { this.status = status; return this; }
        public Builder lockedAt(LocalDateTime lockedAt) { this.lockedAt = lockedAt; return this; }
        public Builder lockedBy(Long lockedBy) { this.lockedBy = lockedBy; return this; }
        public Builder version(Long version) { this.version = version; return this; }
        public Seat build() {
            return new Seat(id, show, seatNumber, status, lockedAt, lockedBy, version);
        }
    }
}
