package com.srh.BookMyShow.RestController;

import com.srh.BookMyShow.DTO.MovieDto;
import com.srh.BookMyShow.Service.MovieSevice;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieSevice movieSevice;

    public MovieController(MovieSevice movieSevice) {
        this.movieSevice = movieSevice;
    }

    @PostMapping
    public ResponseEntity<MovieDto> createMovie(@Valid @RequestBody MovieDto movieDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieSevice.createMovie(
                movieDto
        ));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieSevice.getMoviebyId(id));
    }

    @GetMapping("/lang/{lang}")
    public ResponseEntity<List<MovieDto>> getMovieByLang(@PathVariable String lang) {
        return ResponseEntity.ok(movieSevice.getMoviesByLang(lang));
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<MovieDto>> getMoviesByGenre(@PathVariable String genre) {
        return ResponseEntity.ok(movieSevice.getMoviesbyGenre(genre));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<MovieDto> getMovieByTitle(@PathVariable String title) {
        return ResponseEntity.ok(movieSevice.getMovieByTitle(title));
    }
    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovies()
    {
        return ResponseEntity.ok(movieSevice.getAllMovies());
    }
    @DeleteMapping("/id/{id}")
    public void deleteMovie(Long id)
    {
        movieSevice.deleteMovie(id);
    }
    @PutMapping
    public ResponseEntity<MovieDto> updateMovie(@RequestBody MovieDto movieDto)
    {
        return ResponseEntity.ok(movieSevice.updateMovie(movieDto));
    }
}
