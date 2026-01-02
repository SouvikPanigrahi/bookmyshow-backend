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
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime bookingDate;

    private  String bookingNumber;

    private Double totalamount;
     private String status;

    @ManyToOne
    @JoinColumn(name = "show_id",nullable = false)
    @JsonIgnore
    private Show show;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    @JsonIgnore
    private User  user;

    @OneToMany(mappedBy = "booking")
    private List<ShowSeat> showSeats=new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "payment_id",nullable = false)
    @JsonIgnore
    private Payment payment;

}
