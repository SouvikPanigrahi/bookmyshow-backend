package com.srh.BookMyShow.Service;

import com.srh.BookMyShow.DTO.*;
import com.srh.BookMyShow.Entity.*;
import com.srh.BookMyShow.Exception.ResourceNotFoundException;
import com.srh.BookMyShow.Repository.*;

import  org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class BookingService {
    private final BookingRepo bookingRepo;
    private final ShowRepo showRepo;
    private final ShowService showService;

    private final UserRepo userRepo;
 private  final PaymentRepo paymentRepo;
 private final ShowSeatRepo showSeatRepo;
    public BookingService(BookingRepo bookingRepo, ShowRepo showRepo, ShowService showService,
                          UserRepo userRepo, PaymentRepo paymentRepo, ShowSeatRepo showSeatRepo) {
        this.bookingRepo = bookingRepo;
        this.showRepo = showRepo;
        this.showService = showService;


        this.userRepo = userRepo;

        this.paymentRepo = paymentRepo;
        this.showSeatRepo = showSeatRepo;
    }
    @Transactional
    public BookingDto createBooking(BookingRequestDto bookingRequestDto){
       Booking booking=new Booking();
       User user= userRepo.findById(bookingRequestDto.getUserid()).orElseThrow(
               ()->new ResourceNotFoundException("User Not Found")
       );
        Show show= showRepo.findByIdAndStatus(bookingRequestDto.getShowid(),"ACTIVE")
                .orElseThrow(
                ()-> new ResourceNotFoundException("Show Not Found")
        );


      List<ShowSeat> showSeats= showSeatRepo.findAllById(bookingRequestDto.getSeatIds());
      showService.lockShowSeats(showSeats);


      Payment payment=new Payment();
      payment.setAmount(
              showSeats.stream().mapToDouble(ShowSeat::getPrice).sum());
payment.setPaymentMethod(bookingRequestDto.getPaymentMethod());
payment.setStatus("SUCCESS");
payment.setPaymentTime(LocalDateTime.now());
payment.setTransactionid(UUID.randomUUID().toString());
    paymentRepo.save(payment);

        booking.setBookingDate(LocalDateTime.now());
        booking.setBookingNumber(UUID.randomUUID().toString());
        booking.setShow(show);
        booking.setStatus("CONFIRMED");
        booking.setPayment(payment);
        booking.setUser(user);

        booking.setTotalamount(showSeats.stream().mapToDouble(ShowSeat::getPrice).sum());
     Booking saveBooking=bookingRepo.save(booking);
     user.getBookings().add(booking);
        show.getBookings().add(saveBooking);
        payment.setBooking(saveBooking);
     showService.confirmShowSeats(showSeats,saveBooking);
     saveBooking.setShowSeats(showSeats);

     return mapToBookingDto(booking,showSeats);
    }

    public BookingDto getBookingDtobyId(Long id)
    {
        Booking booking=bookingRepo.findById(id).orElseThrow(
                ()->   new ResourceNotFoundException("Booking with id :"+id+" Not Found")
        );
        List<ShowSeat>bookedShowSeats=booking.getShowSeats();
        return mapToBookingDto(booking,bookedShowSeats);
    }
    public List < BookingDto> getBookingByUserId(Long userid)
    {
       List<Booking> bookingsOfUser=bookingRepo.findByUserId(userid);
       List<BookingDto> bookingDtos=bookingsOfUser.stream().map(
               booking -> {
                   return mapToBookingDto(booking,booking.getShowSeats());
               }
       ).toList();
       return bookingDtos;

    }
    public BookingDto getBookingByBookingNumber(String bookingNumber)
    {
        Booking booking= bookingRepo.findByBookingNumber(bookingNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Booking  with Booking Number "+
                        bookingNumber+" is not found")
        );
        List<ShowSeat> bookedShowSeats=booking.getShowSeats();
        return mapToBookingDto(booking,bookedShowSeats);
    }
@Transactional
    public BookingDto cancelBooking(Long id)
    {
        Booking booking= bookingRepo.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Booking with id "+id+" Not found")
        );


        if (booking.getPayment()!=null)
        {
            booking.getPayment().setStatus("REFUNDED");

        }
        List<ShowSeat>showSeats= showService.cancelShowSeats(
                booking.getShowSeats(),booking);
        BookingDto bookingDto=mapToBookingDto(booking,showSeats);

        booking.setStatus("CANCELLED");
        bookingRepo.save(booking);
        return bookingDto;
    }
    private BookingDto mapToBookingDto(Booking booking, List<ShowSeat> showseats)
    {
        BookingDto bookingDto=new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setBookingDate(booking.getBookingDate());
        bookingDto.setBookingNumber(booking.getBookingNumber());
        bookingDto.setStatus(booking.getStatus());

        //showdto
        ShowDto showDto=new ShowDto();
        showDto.setId(booking.getShow().getId());
        showDto.setStartTime(booking.getShow().getStartTime());
        showDto.setEndTime(booking.getShow().getEndTime());

        //userdto
        UserDto userDto=new UserDto();
        userDto.setId(booking.getUser().getId());
        userDto.setName(booking.getUser().getName());
        userDto.setPhonenumber(booking.getUser().getPhonenumber());
        userDto.setEmail(booking.getUser().getEmail());
       bookingDto.setUser(userDto);
        //paymentdto
        if (booking.getPayment()!=null)
        {
            PaymentDto paymentDto=new PaymentDto();
            paymentDto.setId(booking.getPayment().getId());
            paymentDto.setPaymentMethod(booking.getPayment().getPaymentMethod());
            paymentDto.setPaymentTime(booking.getPayment().getPaymentTime());
            paymentDto.setStatus(booking.getPayment().getStatus());
            paymentDto.setTransactionid(booking.getPayment().getTransactionid());
            paymentDto.setAmount(booking.getPayment().getAmount());
            bookingDto.setPayment(paymentDto);//bookingDto
        }
        //moviedto for Showdto
        MovieDto movieDto=new MovieDto();
        movieDto.setId(booking.getShow().getMovie().getId());
        movieDto.setLang(booking.getShow().getMovie().getLang());
        movieDto.setTitle(booking.getShow().getMovie().getTitle());
        movieDto.setGenre(booking.getShow().getMovie().getGenre());
        movieDto.setDurationinmins(booking.getShow().getMovie().getDurationinmins());
        movieDto.setReleaseDate(booking.getShow().getMovie().getReleaseDate());
        showDto.setMovie(movieDto);//showDto

        //Screendto for showdto
        ScreenDto screenDto=new ScreenDto();
        screenDto.setId(booking.getShow().getScreen().getId());
        screenDto.setName(booking.getShow().getScreen().getName());
        screenDto.setTotalseats(booking.getShow().getScreen().getSeats().size());

        //Theaterdto for Screendto
        TheaterDto theaterDto=new TheaterDto();
        theaterDto.setId(booking.getShow().getScreen().getTheater().getId());
        theaterDto.setName(booking.getShow().getScreen().getTheater().getName());
        theaterDto.setAddress(booking.getShow().getScreen().getTheater().getAddress());
        theaterDto.setCity(booking.getShow().getScreen().getTheater().getCity());
        theaterDto.setTotalScreens(booking.getShow().getScreen().getTheater().getScreens().size());
        screenDto.setTheater(theaterDto);//screenDto
        showDto.setScreen(screenDto);//shoeDto

        //ShowSeatDto for ShowDto
        List<ShowSeatDto> showSeatDtos=showseats.stream()
                .map(showSeat -> {
                    ShowSeatDto showSeatDto=new ShowSeatDto();
                    showSeatDto.setId(showSeat.getId());
                    showSeatDto.setPrice(showSeat.getPrice());
                    showSeatDto.setStatus(showSeat.getStatus());

                    //SeatDto for ShowSeatDto
                    SeatDto seatDto=new SeatDto();
                    seatDto.setId(showSeat.getSeat().getId());
                    seatDto.setSeatNumber(showSeat.getSeat().getSeatNumber());
                    seatDto.setSeatType(showSeat.getSeat().getSeatType());
                    seatDto.setScreen(showSeat.getSeat().getScreen());
                    seatDto.setBasePrice(showSeat.getSeat().getBasePrice());
                    showSeatDto.setSeat(seatDto);//showSeatDto
                    return showSeatDto;
                }).collect(Collectors.toList());
        showDto.setAvailableseats(showSeatDtos);// showDto
        bookingDto.setShow(showDto);//bookingDto
        bookingDto.setBookSeats(showSeatDtos);//bookingDto
        return bookingDto;
    }


}
