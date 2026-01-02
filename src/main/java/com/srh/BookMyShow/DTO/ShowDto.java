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
public class ShowDto {
    private Long id;
    private MovieDto movie;

    private ScreenDto screen;

    public LocalDateTime startTime;
    public LocalDateTime endTime;

    private List<ShowSeatDto>  availableseats=new ArrayList<>();
}
