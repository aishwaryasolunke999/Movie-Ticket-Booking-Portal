package com.moviebooking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SeatNotAvailableException extends RuntimeException {

    private String seatNumber;

    public SeatNotAvailableException(String message) {
        super(message);
    }

    public SeatNotAvailableException(String message,
                                      String seatNumber) {
        super(message);
        this.seatNumber = seatNumber;
    }

    public String getSeatNumber() { return seatNumber; }
}