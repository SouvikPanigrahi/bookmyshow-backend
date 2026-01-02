package com.srh.BookMyShow.Repository;

import com.srh.BookMyShow.Entity.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ShowSeatRepo extends JpaRepository<ShowSeat,Long> {

}
