package com.srh.BookMyShow.Service;

import com.srh.BookMyShow.DTO.*;
import com.srh.BookMyShow.Entity.*;
import com.srh.BookMyShow.Exception.ResourceNotFoundException;
import com.srh.BookMyShow.Exception.SeatUnavailableException;
import com.srh.BookMyShow.Repository.*;
import  org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ShowService {

    private  final MovieRepo movieRepo;
private final ScreenRepo screenRepo;
private final ShowSeatRepo showSeatRepo;
private final ShowRepo showRepo;
private final BookingRepo bookingRepo;

    public ShowService(MovieRepo movieRepo, ScreenRepo screenRepo,
                       ShowSeatRepo showSeatRepo, ShowRepo showRepo, BookingRepo bookingRepo) {
        this.movieRepo = movieRepo;

        this.screenRepo = screenRepo;
        this.showSeatRepo = showSeatRepo;
        this.showRepo = showRepo;
        this.bookingRepo = bookingRepo;

    }
  @Transactional
    public ShowDto createShow(ShowDto showDto)
    {
        Movie movie= movieRepo.findById(showDto.getMovie().getId()).orElseThrow(
                ()->new ResourceNotFoundException("Movie Not Found")
        );
        Screen screen=screenRepo.findById(showDto.getScreen().getId()).orElseThrow(
                ()->new ResourceNotFoundException("Screen  Not Found")
        );

        Show show=new Show();
        show.setMovie(movie);
        show.setScreen(screen);

        show.setStartTime(showDto.getStartTime());
        show.setEndTime(showDto.getEndTime());
        show.setStatus("ACTIVE");
                Show savedShow=showRepo.save(show);
                movie.getShows().add(savedShow);
                screen.getShows().add(savedShow);

        //Creating ShowSeats
        List<ShowSeat> availableSeats=screen.getSeats().stream().map(
                seat -> {
                    ShowSeat showSeat=new ShowSeat();
                    showSeat.setShow(savedShow);
                    showSeat.setPrice(seat.getBasePrice());
                    showSeat.setStatus("AVAILABLE");
                    showSeat.setSeat(seat);
                    return showSeat;
                }
        ).collect(Collectors.toList());

           showSeatRepo.saveAll(availableSeats);
           savedShow.setShowSeats(availableSeats);
        return mapToShowDto(savedShow,availableSeats);

    }
public ShowDto getShowById(Long id)
{
    Show show=showRepo.findById(id).orElseThrow(
            ()->new ResourceNotFoundException("Show  Not Found")
    );
    List<ShowSeat> availableSeats=show.getShowSeats();
    return mapToShowDto(show,availableSeats);
}
public List<ShowDto> getShowsByMovie(Long movieId)
{
   List <Show> showList=showRepo.findByMovieId(movieId).orElseThrow(
           ()->new ResourceNotFoundException("Show Not Found")
   );
   return showList.stream().map(show ->
          mapToShowDto(show,show.getShowSeats()) ).collect(Collectors.toList());

}
public  List<ShowDto> getShowsbyScreen(Long screenId)
{
   List<Show> shows=showRepo.findByScreenId(screenId).orElseThrow(
            ()->new ResourceNotFoundException("Show Not Found")
    );
   return shows.stream().map(show -> mapToShowDto(show,show.getShowSeats()))
           .collect(Collectors.toList());

}
    public  List<ShowDto> getShowsbyMovieIdandCityName(Long movieId,String cityname)
    {
        List<Show> shows=showRepo.findByMovie_IdAndScreen_Theater_City(movieId,cityname).orElseThrow(
                ()->new ResourceNotFoundException("Show Not Found")
        );
        return shows.stream().map(show -> mapToShowDto(show,show.getShowSeats()))
                .collect(Collectors.toList());


    }
    public  List<ShowDto> getAllShows()
    {
        List<Show> shows=showRepo.findAll();
       return shows.stream().map(show -> mapToShowDto(show,show.getShowSeats()))
               .collect(Collectors.toList());

    }
    public ShowDto updateShow(ShowDto showDto)
    {
        Show show=showRepo.findById(showDto.getId()).orElseThrow(
                ()->new ResourceNotFoundException("Show Not Found"));

        show.setMovie(movieRepo.findById(showDto.getMovie().getId()).orElseThrow(
                ()-> new ResourceNotFoundException("Movie Not Found")
        ));
        show.setScreen(screenRepo.findById(showDto.getScreen().getId()).orElseThrow(
                ()->new ResourceNotFoundException("Screen Not Found")
        ));
        show.setStartTime(showDto.getStartTime());
        show.setEndTime(showDto.getEndTime());
        Show savedShow=showRepo.save(show);
        savedShow.setShowSeats(showSeatRepo.findAllById(showDto.getAvailableseats().stream().map(
                showSeatDto -> showSeatDto.getId()
        ).collect(Collectors.toList())));
        return mapToShowDto(savedShow,show.getShowSeats());
    }
    public void deleteShow(Long id)
    {
     Show show=showRepo.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Show Not Found")
        );
     if (show.getStatus().equalsIgnoreCase("DEACTIVATED"))
         return;
     show.setStatus("DEACTIVATED");
     showRepo.save(show);
    }

    private ShowDto mapToShowDto(Show show,List<ShowSeat> availableSeats)
    {
        ShowDto showDto=new ShowDto();
        showDto.setId(show.getId());
        showDto.setStartTime(show.getStartTime());
        showDto.setEndTime(show.getEndTime());

        //MovieDto for ShowDto
        MovieDto movieDto=new MovieDto();
        movieDto.setTitle(show.getMovie().getTitle());
        movieDto.setDurationinmins(show.getMovie().getDurationinmins());
        movieDto.setLang(show.getMovie().getLang());
        movieDto.setGenre(show.getMovie().getGenre());
        movieDto.setReleaseDate(show.getMovie().getReleaseDate());
        movieDto.setId(show.getMovie().getId());
        showDto.setMovie(movieDto);//showDto

        //ScreenDto for ShowDto
        ScreenDto screenDto= new ScreenDto();
        screenDto.setName(show.getScreen().getName());
        screenDto.setTotalseats(show.getScreen().getSeats().size());
        screenDto.setId(show.getScreen().getId());

        //TheaterDto for ScreenDto
        TheaterDto theaterDto=new TheaterDto();
        theaterDto.setId(show.getScreen().getTheater().getId());
        theaterDto.setCity(show.getScreen().getTheater().getCity());
        theaterDto.setAddress(show.getScreen().getTheater().getAddress());
        theaterDto.setName(show.getScreen().getTheater().getName());
        theaterDto.setTotalScreens(show.getScreen().getTheater().getScreens().size());
        screenDto.setTheater(theaterDto);//screenDto
        showDto.setScreen(screenDto);//showDto


        //ShowSeatDto for ShowDto
        List<ShowSeatDto>showSeatDtos=availableSeats.stream().map(
                showSeat -> {
                    ShowSeatDto showSeatDto=new ShowSeatDto();
                    showSeatDto.setId(showSeat.getId());
                    showSeatDto.setPrice(showSeat.getPrice());
                    showSeatDto.setStatus(showSeat.getStatus());
                    //SeatDto for ShowSeatDto
                    SeatDto seatDto=new SeatDto();
                    seatDto.setSeatType(showSeat.getSeat().getSeatType());
                    seatDto.setSeatNumber(showSeat.getSeat().getSeatNumber());
                    seatDto.setBasePrice(showSeat.getSeat().getBasePrice());
                    seatDto.setId(showSeat.getShow().getId());
                    seatDto.setScreen(showSeat.getSeat().getScreen());
                    showSeatDto.setSeat(seatDto);//showseatDto
                    return showSeatDto;

                }
        ).collect(Collectors.toList());

        showDto.setAvailableseats(showSeatDtos);//showDto
        return showDto;
    }

    //showseat  Locking Logic
    public void lockShowSeats(List<ShowSeat> showSeats)
    {
       List<ShowSeat> showSeatList= showSeats.stream().map(
                showSeat -> {
                    if (!showSeat.getStatus().equalsIgnoreCase("AVAILABLE"))
                        throw new SeatUnavailableException("ShowSeat NOt Available" +
                                "Id: "+showSeat.getId());
                    showSeat.setStatus("LOCKED");
                    return showSeat;
                }
        ).collect(Collectors.toList());

       showSeatRepo.saveAll(showSeatList);
    }
    //showseat Confirm logic
    public void confirmShowSeats(List<ShowSeat> showSeats,Booking savebooking)
    {
        List<ShowSeat> showSeatList= showSeats.stream().map(
                showSeat -> {
                    showSeat.setStatus("BOOKED");
                    showSeat.setBooking(savebooking);
                    return showSeat;
                }
        ).collect(Collectors.toList());

        showSeatRepo.saveAll(showSeatList);
    }
    // Cancel ShowSeats

    public List<ShowSeat> cancelShowSeats(List<ShowSeat> showSeats,Booking savebooking)
    {
        List<ShowSeat> showSeatList= showSeats.stream().map(
                showSeat -> {
                    showSeat.setStatus("AVAILABLE");
                    showSeat.setBooking(null);
                    return showSeat;
                }
        ).collect(Collectors.toList());


      return   showSeatRepo.saveAll(showSeatList);
    }

}
