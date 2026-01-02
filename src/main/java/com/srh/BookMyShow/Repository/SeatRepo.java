package com.srh.BookMyShow.Repository;

import com.srh.BookMyShow.Entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepo extends JpaRepository<Seat,Long> {
 List<Seat> findByScreenId(Long ScreenId);
}
