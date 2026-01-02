package com.srh.BookMyShow.RestController;


import com.srh.BookMyShow.DTO.TheaterDto;

import com.srh.BookMyShow.Service.TheaterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theaters")
public class TheaterController {
    private final TheaterService theaterService;

    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }
    @PostMapping
    public ResponseEntity<TheaterDto> createTheater(@Valid @RequestBody TheaterDto theaterDto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(theaterService.createTheater(theaterDto));
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<TheaterDto> getTheaterById(@PathVariable Long id) {
        return ResponseEntity.ok(theaterService.getTheaterById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<TheaterDto> getTheaterByName(@PathVariable String name) {
        return ResponseEntity.ok(theaterService.getTheaterbyName(name));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<TheaterDto>> getTheaterbyCity(@PathVariable String city) {
        return ResponseEntity.ok(theaterService.getTheatersByCity(city));
    }

    @GetMapping("/address/{address}")
    public ResponseEntity<TheaterDto> getTheaterByAddress(@PathVariable String address) {
        return ResponseEntity.ok(theaterService.getTheaterByAddress(address));
    }

    @GetMapping
    public ResponseEntity<List <TheaterDto>> getAllTheaters() {
        return ResponseEntity.ok(theaterService.getAllTheaters());
    }
    @DeleteMapping("/id/{id}")
    public void deleteShow(Long id)
    {
        theaterService.deleteTheater(id);
    }
    @PutMapping
    public ResponseEntity<TheaterDto> updateTheater(@RequestBody TheaterDto theaterDto)
    {
        return ResponseEntity.ok(theaterService.updateTheater(theaterDto));
    }
}
