package com.moviebooking.dto.response;

import com.moviebooking.enums.BookingStatus;
import java.time.LocalDateTime;
import java.util.List;

public class BookingResponse {

    private Long id;
    private Long userId;
    private String userName;
    private Long showId;
    private String movieTitle;
    private String hallName;
    private String showDate;
    private String showTime;
    private List<String> seatNumbers;
    private Integer totalSeats;
    private Double totalAmount;
    private BookingStatus status;
    private LocalDateTime bookedAt;
    private LocalDateTime cancelledAt;

    public BookingResponse() {}
    public BookingResponse(Long id, Long userId, String userName, Long showId,
                           String movieTitle, String hallName, String showDate,
                           String showTime, List<String> seatNumbers, Integer totalSeats,
                           Double totalAmount, BookingStatus status,
                           LocalDateTime bookedAt, LocalDateTime cancelledAt) {
        this.id = id; this.userId = userId; this.userName = userName;
        this.showId = showId; this.movieTitle = movieTitle; this.hallName = hallName;
        this.showDate = showDate; this.showTime = showTime;
        this.seatNumbers = seatNumbers; this.totalSeats = totalSeats;
        this.totalAmount = totalAmount; this.status = status;
        this.bookedAt = bookedAt; this.cancelledAt = cancelledAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public Long getShowId() { return showId; }
    public void setShowId(Long showId) { this.showId = showId; }
    public String getMovieTitle() { return movieTitle; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }
    public String getHallName() { return hallName; }
    public void setHallName(String hallName) { this.hallName = hallName; }
    public String getShowDate() { return showDate; }
    public void setShowDate(String showDate) { this.showDate = showDate; }
    public String getShowTime() { return showTime; }
    public void setShowTime(String showTime) { this.showTime = showTime; }
    public List<String> getSeatNumbers() { return seatNumbers; }
    public void setSeatNumbers(List<String> seatNumbers) { this.seatNumbers = seatNumbers; }
    public Integer getTotalSeats() { return totalSeats; }
    public void setTotalSeats(Integer totalSeats) { this.totalSeats = totalSeats; }
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
        private Long id; private Long userId; private String userName;
        private Long showId; private String movieTitle; private String hallName;
        private String showDate; private String showTime;
        private List<String> seatNumbers; private Integer totalSeats;
        private Double totalAmount; private BookingStatus status;
        private LocalDateTime bookedAt; private LocalDateTime cancelledAt;
        public Builder id(Long id) { this.id = id; return this; }
        public Builder userId(Long userId) { this.userId = userId; return this; }
        public Builder userName(String userName) { this.userName = userName; return this; }
        public Builder showId(Long showId) { this.showId = showId; return this; }
        public Builder movieTitle(String movieTitle) { this.movieTitle = movieTitle; return this; }
        public Builder hallName(String hallName) { this.hallName = hallName; return this; }
        public Builder showDate(String showDate) { this.showDate = showDate; return this; }
        public Builder showTime(String showTime) { this.showTime = showTime; return this; }
        public Builder seatNumbers(List<String> seatNumbers) { this.seatNumbers = seatNumbers; return this; }
        public Builder totalSeats(Integer totalSeats) { this.totalSeats = totalSeats; return this; }
        public Builder totalAmount(Double totalAmount) { this.totalAmount = totalAmount; return this; }
        public Builder status(BookingStatus status) { this.status = status; return this; }
        public Builder bookedAt(LocalDateTime bookedAt) { this.bookedAt = bookedAt; return this; }
        public Builder cancelledAt(LocalDateTime cancelledAt) { this.cancelledAt = cancelledAt; return this; }
        public BookingResponse build() {
            return new BookingResponse(id, userId, userName, showId, movieTitle, hallName,
                                       showDate, showTime, seatNumbers, totalSeats,
                                       totalAmount, status, bookedAt, cancelledAt);
        }
    }
}
