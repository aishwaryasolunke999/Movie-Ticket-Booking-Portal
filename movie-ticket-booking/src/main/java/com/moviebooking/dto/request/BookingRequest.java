package com.moviebooking.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public class BookingRequest {

    @NotNull(message = "Show ID is required")
    private Long showId;

    @NotNull(message = "Seat IDs are required")
    @Size(min = 1, message = "At least one seat must be selected")
    @Size(max = 10, message = "Cannot book more than 10 seats at once")
    private List<Long> seatIds;

    public BookingRequest() {}
    public BookingRequest(Long showId, List<Long> seatIds) {
        this.showId = showId; this.seatIds = seatIds;
    }
    public Long getShowId() { return showId; }
    public void setShowId(Long showId) { this.showId = showId; }
    public List<Long> getSeatIds() { return seatIds; }
    public void setSeatIds(List<Long> seatIds) { this.seatIds = seatIds; }
}
