package com.srh.BookMyShow.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScreenDto {
    private  Long id;

    private String name;
    private TheaterDto theater;


    private Integer totalseats;



}
