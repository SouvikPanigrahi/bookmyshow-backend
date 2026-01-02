package com.srh.BookMyShow.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {
    private Long id;

    private String title;
    private String genre;

    private String lang;

    private Integer durationinmins;
    private String releaseDate;
}
