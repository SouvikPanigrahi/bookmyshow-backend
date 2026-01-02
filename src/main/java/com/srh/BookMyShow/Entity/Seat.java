package com.srh.BookMyShow.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String seatType;
    @Column(nullable = false)
    private String seatNumber;
    @Column(nullable = false)
    private Double basePrice;
    @ManyToOne
    @JoinColumn(name = "screen_id",nullable = false)
    @JsonIgnore
    private Screen screen;


}
