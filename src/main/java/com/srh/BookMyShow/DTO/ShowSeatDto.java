package com.srh.BookMyShow.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowSeatDto {
    private Long id;
    private String status;
    private Double price;
    private SeatDto seat;
}
