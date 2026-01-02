package com.srh.BookMyShow.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shows")
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "movie_id",nullable = false)
    @JsonIgnore
    private Movie movie;
   @ManyToOne
   @JoinColumn(name = "screen_id",nullable = false)
   @JsonIgnore
    private Screen screen;
private String status;
   public LocalDateTime startTime;
   public LocalDateTime endTime;
   @OneToMany(mappedBy = "show",cascade = CascadeType.ALL)
   private List<ShowSeat> showSeats=new ArrayList<>();
   @OneToMany(mappedBy = "show")
    private List<Booking> bookings=new ArrayList<>();
}
