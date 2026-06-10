package com.moviebooking.dto.response;

import com.moviebooking.enums.SeatStatus;
import java.time.LocalDateTime;

public class SeatResponse {

    private Long id;
    private Long showId;
    private String seatNumber;
    private SeatStatus status;
    private LocalDateTime lockedAt;

    public SeatResponse() {}
    public SeatResponse(Long id, Long showId, String seatNumber,
                        SeatStatus status, LocalDateTime lockedAt) {
        this.id = id; this.showId = showId; this.seatNumber = seatNumber;
        this.status = status; this.lockedAt = lockedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getShowId() { return showId; }
    public void setShowId(Long showId) { this.showId = showId; }
    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }
    public SeatStatus getStatus() { return status; }
    public void setStatus(SeatStatus status) { this.status = status; }
    public LocalDateTime getLockedAt() { return lockedAt; }
    public void setLockedAt(LocalDateTime lockedAt) { this.lockedAt = lockedAt; }

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private Long id; private Long showId; private String seatNumber;
        private SeatStatus status; private LocalDateTime lockedAt;
        public Builder id(Long id) { this.id = id; return this; }
        public Builder showId(Long showId) { this.showId = showId; return this; }
        public Builder seatNumber(String seatNumber) { this.seatNumber = seatNumber; return this; }
        public Builder status(SeatStatus status) { this.status = status; return this; }
        public Builder lockedAt(LocalDateTime lockedAt) { this.lockedAt = lockedAt; return this; }
        public SeatResponse build() {
            return new SeatResponse(id, showId, seatNumber, status, lockedAt);
        }
    }
}
