package com.srh.BookMyShow.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private Long id;

    private LocalDateTime bookingDate;

    private  String bookingNumber;

    private String status;


    private ShowDto  show;
    private UserDto user;


    private List<ShowSeatDto> bookSeats=new ArrayList<>();
    private PaymentDto payment;

}
