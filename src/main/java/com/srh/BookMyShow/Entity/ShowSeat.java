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
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    private Double price;
    @ManyToOne
    @JoinColumn(name = "show_id",nullable = false)
    @JsonIgnore
    private Show show;
    @ManyToOne
    @JoinColumn(name = "booking_id")
    @JsonIgnore
    private Booking booking;
    @ManyToOne
    @JoinColumn(name = "seat_id",nullable = false)
    @JsonIgnore
    private Seat seat;


}
