package com.srh.BookMyShow.RestController;

import com.srh.BookMyShow.DTO.BookingDto;
import com.srh.BookMyShow.DTO.BookingRequestDto;
import com.srh.BookMyShow.Service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
@PostMapping
    public ResponseEntity<BookingDto> createBooking(@Valid @RequestBody BookingRequestDto bookingRequestDto)
{
    return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.createBooking(
            bookingRequestDto
    ));
}
@GetMapping("/id/{id}")
public ResponseEntity<BookingDto> getBookingById(@PathVariable Long id)
{
    return ResponseEntity.ok(bookingService.getBookingDtobyId(id));
}
@GetMapping("/user/{userid}")
public  ResponseEntity<List<BookingDto>> getBookingByUserId(@PathVariable Long userid)
{
    return ResponseEntity.ok(bookingService.getBookingByUserId(userid));
}
@GetMapping("/bookingnumber/{bookingnumber}")
public ResponseEntity<BookingDto> getBookingByBookingNumber(@PathVariable String bookingnumber)
{
    return ResponseEntity.ok(bookingService.getBookingByBookingNumber(bookingnumber));
}
@DeleteMapping("/id/{id}")
public ResponseEntity<BookingDto> deleteBooking(@PathVariable Long id)
{
    return ResponseEntity.ok(bookingService.cancelBooking(id));
}
}
