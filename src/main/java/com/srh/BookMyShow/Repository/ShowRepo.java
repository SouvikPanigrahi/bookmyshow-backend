package com.srh.BookMyShow.Repository;

import com.srh.BookMyShow.Entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShowRepo extends JpaRepository<Show,Long> {
    Optional<List<Show>> findByMovieId(Long movieid);
    Optional<List<Show>> findByScreenId(Long screenId);
    Optional<List<Show>> findByMovie_IdAndScreen_Theater_City(Long movieId, String cityName);
   Optional<Show> findByIdAndStatus( Long showId, String status);
    boolean existsByScreen_Theater_IdAndStatus(Long theaterId, String status);
}
