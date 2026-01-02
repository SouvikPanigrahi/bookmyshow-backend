package com.srh.BookMyShow.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private Long id;

    private String transactionid;

    private Double amount;

    private String paymentMethod;

    private String status;

    private LocalDateTime paymentTime;

}
