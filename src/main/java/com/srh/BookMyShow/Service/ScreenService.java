package com.srh.BookMyShow.Service;

import com.srh.BookMyShow.DTO.ScreenDto;
import com.srh.BookMyShow.DTO.SeatDto;
import com.srh.BookMyShow.DTO.TheaterDto;
import com.srh.BookMyShow.Entity.Screen;
import com.srh.BookMyShow.Entity.Seat;
import com.srh.BookMyShow.Entity.Theater;
import com.srh.BookMyShow.Exception.ResourceNotFoundException;
import com.srh.BookMyShow.Repository.ScreenRepo;
import com.srh.BookMyShow.Repository.SeatRepo;
import com.srh.BookMyShow.Repository.ShowRepo;
import com.srh.BookMyShow.Repository.TheaterRepo;

import  org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ScreenService {
    private final TheaterRepo theaterRepo;
    private final ShowRepo showRepo;
    private final SeatRepo seatRepo;
   private final ScreenRepo screenRepo;


    public ScreenService(TheaterRepo theaterRepo, ShowRepo showRepo, SeatRepo seatRepo, ScreenRepo screenRepo) {
        this.theaterRepo = theaterRepo;
        this.showRepo = showRepo;
        this.seatRepo = seatRepo;
        this.screenRepo = screenRepo;

    }
    @Transactional
    public ScreenDto createScreen(ScreenDto screenDto)
    {
        Screen screen=new Screen();
        screen.setTheater(theaterRepo.findById(screenDto.getTheater().getId()).orElseThrow(
                ()->  new ResourceNotFoundException("Theater Not Found")
        ));
        screen.setName(screenDto.getName());


        Screen savedscreen=screenRepo.save(screen);
       List<Seat> savedSeats=seatRepo.findByScreenId(savedscreen.getId());
        savedscreen.setSeats(savedSeats);
        Theater theater=savedscreen.getTheater();
        theater.getScreens().add(savedscreen);
        return mapToScreenDto(savedscreen);
    }
    public SeatDto createSeat(SeatDto seatDto)
    {
        // Seat creating logic
        Seat seat=new Seat();
        seat.setSeatType(seatDto.getSeatType());
        seat.setSeatNumber(seatDto.getSeatNumber());
        seat.setBasePrice(seatDto.getBasePrice());
        seat.setScreen(seatDto.getScreen());
        Seat savedSeat=seatRepo.save(seat);
        savedSeat.getScreen().getSeats().add(savedSeat);
        return mapToSeatDto(savedSeat);
    }
    private ScreenDto mapToScreenDto(Screen screen)
    {
        ScreenDto screenDto =new ScreenDto();
        screenDto.setId(screen.getId());
        screenDto.setName(screen.getName());
        screenDto.setTheater(theaterRepo.findById(screen.getTheater().getId()).map(
                theater -> {
                    TheaterDto theaterDto=new TheaterDto();
                    theaterDto.setId(theater.getId());
                    theaterDto.setName(theater.getName());
                    theaterDto.setCity(theater.getCity());
                    theaterDto.setAddress(theater.getAddress());
                    theaterDto.setTotalScreens(theater.getScreens().size());
                    return theaterDto;
                }
                ).orElseThrow( ()->new ResourceNotFoundException("Theater not found")));

        screenDto.setTotalseats(screen.getSeats().size());

        return screenDto;
    }
    private SeatDto mapToSeatDto(Seat seat)
    {
        SeatDto seatDto =new SeatDto();
        seatDto.setId(seat.getId());
        seatDto.setSeatType(seat.getSeatType());
        seatDto.setSeatNumber(seat.getSeatNumber());
        seatDto.setScreen(seat.getScreen());
        seatDto.setBasePrice(seat.getBasePrice());
        return seatDto;
    }
}
