package com.srh.BookMyShow.Service;

import com.srh.BookMyShow.DTO.MovieDto;
import com.srh.BookMyShow.Entity.Movie;
import com.srh.BookMyShow.Entity.Show;
import com.srh.BookMyShow.Exception.ResourceNotFoundException;
import com.srh.BookMyShow.Repository.MovieRepo;
import com.srh.BookMyShow.Repository.ShowRepo;

import  org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class MovieSevice {
    private final MovieRepo movieRepo;
private final ShowRepo showRepo;
    public MovieSevice(MovieRepo movieRepo, ShowRepo showRepo) {
        this.movieRepo = movieRepo;
        this.showRepo = showRepo;
    }
public  MovieDto createMovie(MovieDto movieDto)
{
    Movie movie=new Movie();

    movie.setDurationinmins(movieDto.getDurationinmins());
    movie.setLang(movieDto.getLang());
    movie.setGenre(movieDto.getGenre());
    movie.setTitle(movieDto.getTitle());
    movie.setReleaseDate(movieDto.getReleaseDate());
    Movie saveMovie=movieRepo.save(movie);
    return mapToMovieDto(saveMovie);
}
public MovieDto getMoviebyId(Long id)
{
    Movie movie=movieRepo.findById(id).orElseThrow(
            ()->new ResourceNotFoundException("Movie with id "+id+" not found")
    );
    return mapToMovieDto(movie);
}
public MovieDto getMovieByTitle(String title)
{
    Movie movie=movieRepo.findByTitle(title).orElseThrow(
            ()->new ResourceNotFoundException("Movie Not Found")
    );
    return mapToMovieDto(movie);
}
public List<MovieDto> getMoviesbyGenre(String genre){
        List<Movie>movieList=movieRepo.findByGenre(genre);
        return movieList.stream().map(this::mapToMovieDto).collect(Collectors.toList());

}
public List<MovieDto> getMoviesByLang(String lang)
{
    List<Movie> movieList=movieRepo.findByLang(lang);
    return movieList.stream().map(this::mapToMovieDto).collect(Collectors.toList());

}
public List<MovieDto> getAllMovies()
{
 List<Movie>movieList=movieRepo.findAll();
 return movieList.stream().map(this::mapToMovieDto).collect(Collectors.toList());

}
@Transactional
public void deleteMovie(Long id)
{
    List<Show> shows=showRepo.findByMovieId(id).orElseThrow(
            ()->new ResourceNotFoundException("Show Not Found")
    );
 showRepo.deleteAll(shows);
    movieRepo.delete(movieRepo.findById(id).orElseThrow(
            ()->new ResourceNotFoundException("Movie eith id "+id+" Not Found")
    ));
}
public MovieDto updateMovie(MovieDto movieDto)
{
    Movie movie=movieRepo.findById(movieDto.getId()).orElseThrow(
            ()->new ResourceNotFoundException("Movie Not Found")
    );

    movie.setDurationinmins(movieDto.getDurationinmins());
    movie.setLang(movieDto.getLang());
    movie.setGenre(movieDto.getGenre());
    movie.setTitle(movieDto.getTitle());
    movie.setReleaseDate(movieDto.getReleaseDate());
    Movie updatedMovie =movieRepo.save(movie);
    return mapToMovieDto(updatedMovie);
}
    private MovieDto mapToMovieDto(Movie movie)
    {
        MovieDto movieDto=new MovieDto();
        movieDto.setId(movieDto.getId());
        movieDto.setLang(movie.getLang());
        movieDto.setTitle(movie.getTitle());
        movieDto.setGenre(movie.getGenre());
        movieDto.setDurationinmins(movie.getDurationinmins());
        movieDto.setReleaseDate(movie.getReleaseDate());
        return movieDto;
    }
    public Movie getMovieEntity(Long movieId)
    {
        return movieRepo.findById(movieId).orElseThrow(()->new
                ResourceNotFoundException("Movie Not Found"));
    }
}
