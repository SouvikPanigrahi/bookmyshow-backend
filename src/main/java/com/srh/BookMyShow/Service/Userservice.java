package com.srh.BookMyShow.Service;

import com.srh.BookMyShow.DTO.UserDto;
import com.srh.BookMyShow.Entity.User;
import com.srh.BookMyShow.Exception.ResourceNotFoundException;
import com.srh.BookMyShow.Repository.BookingRepo;
import com.srh.BookMyShow.Repository.UserRepo;
import org.springframework.stereotype.Service;



@Service
public class Userservice {
    private final UserRepo userRepo;
private final BookingRepo bookingRepo;
private final BookingService bookingService;
    public Userservice(UserRepo userRepo, BookingRepo bookingRepo, BookingService bookingService) {
        this.userRepo = userRepo;

        this.bookingRepo = bookingRepo;
        this.bookingService = bookingService;
    }
public UserDto createUser(UserDto userDto)
{
    User user=mapToUser(userDto);

    return mapToUserDto(user);

}

public  UserDto getUserbyId(Long id)
{
    User user=userRepo.findById(id).orElseThrow(
            ()->new ResourceNotFoundException("User Not Found")
    );
    return mapToUserDto(user);

}
public UserDto getUserByName(String name){
        User user=userRepo.findByName(name).orElseThrow(
                ()->new ResourceNotFoundException("User Not Found")
        );
        return mapToUserDto(user);
}
public UserDto getUserByEmail(String email)
{
    User user=userRepo.findByEmail(email).orElseThrow(
            ()->new ResourceNotFoundException("User Not Found")
    );
    return mapToUserDto(user);
}
public UserDto getUserByPhoneNumber(String phonenumber)
{
    User user=userRepo.findByPhonenumber(phonenumber).orElseThrow(
            ()->new ResourceNotFoundException("User Not Found")
    );
    return mapToUserDto(user);
}
public void deleteUser(Long id)
{
    User user=userRepo.findById(id).orElseThrow(
            ()->new ResourceNotFoundException("User Not Found")
    );
    if (user.getStatus().equalsIgnoreCase("DEACTIVATED"))
        return;

    user.getBookings().forEach(booking -> bookingService.cancelBooking(
            booking.getId()
    ));
    user.setStatus("DEACTIVATED");
    userRepo.save(user);

}
public UserDto updateUser(UserDto userDto)
{
    User user=userRepo.findById(userDto.getId()).orElseThrow(
            ()->new ResourceNotFoundException("User Not Found")
    );
    user.setPhonenumber(userDto.getPhonenumber());
    user.setName(userDto.getName());
    user.setEmail(userDto.getEmail());
    User savedUser = userRepo.save(user);
    savedUser.setBookings(bookingRepo.findByUserId(savedUser.getId()));

    return mapToUserDto(savedUser);
}
    private UserDto mapToUserDto(User user)
    {
        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setPhonenumber(user.getPhonenumber());
        userDto.setEmail(user.getEmail());

        return userDto;
    }
    private User mapToUser(UserDto userDto)
    {
        User user=new User();
        user.setPhonenumber(userDto.getPhonenumber());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setStatus("ACTIVE");
        User savedUser = userRepo.save(user);


        return savedUser;
    }

}
