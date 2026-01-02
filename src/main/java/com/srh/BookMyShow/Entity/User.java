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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String  name;
    @Column(nullable = false)
    private String phonenumber;
    @Column(nullable = false)
    private String email;
    private String status;
    @OneToMany(mappedBy = "user")
    private List<Booking> bookings=new ArrayList<>();

}
