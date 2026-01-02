package com.srh.BookMyShow.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Column(nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name="theater_id",nullable = false)
    @JsonIgnore
    private Theater theater;
    @OneToMany(mappedBy = "screen",cascade = CascadeType.ALL)
    private List<Seat> seats= new ArrayList<>();

    @OneToMany(mappedBy = "screen",cascade = CascadeType.ALL)
    private List<Show>shows=new ArrayList<>();

}
