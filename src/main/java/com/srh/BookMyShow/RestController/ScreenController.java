package com.srh.BookMyShow.RestController;

import com.srh.BookMyShow.DTO.ScreenDto;
import com.srh.BookMyShow.DTO.SeatDto;
import com.srh.BookMyShow.Service.ScreenService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/screens")
public class ScreenController {

    private final ScreenService screenService;

    public ScreenController(ScreenService screenService) {
        this.screenService = screenService;
    }
    @PostMapping
    public ResponseEntity<ScreenDto> createScreen(@Valid @RequestBody ScreenDto screenDto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(screenService.createScreen(screenDto));

    }
    @PostMapping("/seats")
    public ResponseEntity<SeatDto> createSeat(@Valid @RequestBody SeatDto seatDto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(screenService.createSeat(seatDto));
    }
}
