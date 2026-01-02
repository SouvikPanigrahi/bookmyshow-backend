package com.srh.BookMyShow.RestController;

import com.srh.BookMyShow.DTO.ShowDto;
import com.srh.BookMyShow.Service.ShowService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shows")
public class ShowController {
    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }
    @PostMapping
    public ResponseEntity<ShowDto> createShow(@Valid @RequestBody ShowDto showDto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(showService.createShow(showDto));
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<ShowDto> getShowById(@PathVariable Long id) {
        return ResponseEntity.ok(showService.getShowById(id));
    }

    @GetMapping("/movie/{movieid}")
    public ResponseEntity<List<ShowDto>> getShowByMovieId(@PathVariable Long movieid ) {
        return ResponseEntity.ok(showService.getShowsByMovie(movieid));
    }

    @GetMapping("/screen/{screenid}")
    public ResponseEntity<List<ShowDto>> getShowByScreenId(@PathVariable Long screenid) {
        return ResponseEntity.ok(showService.getShowsbyScreen(screenid));
    }

    @GetMapping
    public ResponseEntity<List<ShowDto>> getShowByMovieIdndCityName(
            @RequestParam Long movieId,@RequestParam String  cityname) {
        return ResponseEntity.ok(showService.getShowsbyMovieIdandCityName(movieId, cityname));
    }

    @GetMapping("/allshows")
    public ResponseEntity<List<ShowDto>> getAllShows()
    {
        return ResponseEntity.ok(showService.getAllShows());
    }
    @DeleteMapping("/id/{id}")
    public void deleteShow(Long id)
    {
        showService.deleteShow(id);
    }
    @PutMapping
    public ResponseEntity<ShowDto> updateShow(@RequestBody ShowDto showDto)
    {
        return ResponseEntity.ok(showService.updateShow(showDto));
    }
}
