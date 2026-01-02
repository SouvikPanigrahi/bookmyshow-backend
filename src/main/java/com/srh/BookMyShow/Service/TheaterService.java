package com.srh.BookMyShow.Service;

import com.srh.BookMyShow.DTO.TheaterDto;
import com.srh.BookMyShow.Entity.Show;
import com.srh.BookMyShow.Entity.Theater;
import com.srh.BookMyShow.Exception.ResourceNotFoundException;
import com.srh.BookMyShow.Repository.ScreenRepo;
import com.srh.BookMyShow.Repository.ShowRepo;
import com.srh.BookMyShow.Repository.TheaterRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class TheaterService {
    private final TheaterRepo theaterRepo;
 private final ScreenRepo screenRepo;
 public  final ShowRepo showRepo;
    public TheaterService(TheaterRepo theaterRepo, ScreenRepo screenRepo, ShowRepo showRepo) {
        this.theaterRepo = theaterRepo;
        this.screenRepo = screenRepo;
        this.showRepo = showRepo;
    }
    public TheaterDto createTheater(TheaterDto theaterDto)
    {
        Theater theater=new Theater();

        theater.setName(theaterDto.getName());
        theater.setCity(theaterDto.getCity());
        theater.setAddress(theaterDto.getAddress());

        Theater savedTheater=theaterRepo.save(theater);



        return mapToTheaterDto(savedTheater);

    }
    public TheaterDto getTheaterbyName(String name)
    {
        Theater theater=theaterRepo.findByName(name).orElseThrow(
                ()->new ResourceNotFoundException("Theater Not Found")
        );
        return mapToTheaterDto(theater);
    }
    public TheaterDto getTheaterById(Long id)
    {
        Theater theater=theaterRepo.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Theater Not Found")
        );
        return mapToTheaterDto(theater);
    }
    public List<TheaterDto> getTheatersByCity(String city)
    {
        List<Theater> theaters=theaterRepo.findByCity(city);
        return theaters.stream().map(this::mapToTheaterDto).toList();
    }
    public TheaterDto getTheaterByAddress(String address)
    {
        Theater theater=theaterRepo.findByAddress(address).orElseThrow(
                ()->new ResourceNotFoundException("Theater Not Found")
        );
        return mapToTheaterDto(theater);
    }
    public List<TheaterDto> getAllTheaters()
    {
        List<TheaterDto> theaterDtos=theaterRepo.findAll().stream().map(this::mapToTheaterDto)
                .collect(Collectors.toList());

        return theaterDtos;
    }
@Transactional
    public  void deleteTheater(Long id)
    {
        Theater theater=theaterRepo.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Theater Not Found")
        );

     boolean hasActiveShows=showRepo.existsByScreen_Theater_IdAndStatus(id,"ACTIVE");
     if (hasActiveShows)
         throw new IllegalStateException("Active shows Exists, Deletion Not allowed");
        theaterRepo.delete(theater);

    }
    public TheaterDto updateTheater(TheaterDto theaterDto)
    {
        Theater theater=theaterRepo.findById(theaterDto.getId()).orElseThrow(
                ()->new ResourceNotFoundException("Theater Not Found")
        );

        theater.setName(theaterDto.getName());
        theater.setCity(theaterDto.getCity());
        theater.setAddress(theaterDto.getAddress());
        Theater savedTheater=theaterRepo.save(theater);
        return mapToTheaterDto(savedTheater);
    }
    private TheaterDto mapToTheaterDto(Theater theater)
    {
        TheaterDto theaterDto=new TheaterDto();
        theaterDto.setId(theater.getId());
        theaterDto.setName(theater.getName());
        theaterDto.setCity(theater.getCity());
        theaterDto.setAddress(theater.getAddress());
        theaterDto.setTotalScreens(theater.getScreens().size()) ;
        return theaterDto;
    }
}
