package com.srh.BookMyShow.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionid;

    private Double amount;
    @Column(nullable = false)
    private String paymentMethod;

    private String status;

    private LocalDateTime paymentTime;

    @OneToOne(mappedBy ="payment")
    private Booking booking;
}
