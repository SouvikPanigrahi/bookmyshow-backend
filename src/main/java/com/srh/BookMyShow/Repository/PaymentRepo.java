package com.srh.BookMyShow.Repository;

import com.srh.BookMyShow.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PaymentRepo extends JpaRepository<Payment,Long> {

}
