package com.srh.BookMyShow.Repository;

import com.srh.BookMyShow.Entity.Theater;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TheaterRepo extends JpaRepository<Theater,Long> {
    Optional<Theater> findByName(String name);
    List<Theater> findByCity(String city);
    Optional<Theater>findByAddress(String address);

}
