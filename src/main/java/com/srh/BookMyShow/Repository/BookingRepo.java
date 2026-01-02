package com.srh.BookMyShow.Repository;

import com.srh.BookMyShow.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepo extends JpaRepository<Booking,Long> {
    Optional<Booking> findByBookingNumber(String bookingNumber);
    List<Booking> findByUserId(Long userId);

}
