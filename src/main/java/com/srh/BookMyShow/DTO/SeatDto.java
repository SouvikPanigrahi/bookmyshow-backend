package com.srh.BookMyShow.DTO;

import com.srh.BookMyShow.Entity.Screen;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatDto {
    private Long id;
    private String seatType;

    private String seatNumber;

    private Double  basePrice;

    private Screen screen;
}
