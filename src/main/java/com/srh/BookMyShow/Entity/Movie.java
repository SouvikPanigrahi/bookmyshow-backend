package com.srh.BookMyShow.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String genre;
    @Column(nullable = false)
    private String lang;
    @Column(nullable = false)
    private Integer durationinmins;
    private String releaseDate;
    @OneToMany(mappedBy = "movie")
    private List<Show>shows=new ArrayList<>();
}
