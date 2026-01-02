package com.srh.BookMyShow.RestController;

import com.srh.BookMyShow.DTO.TheaterDto;
import com.srh.BookMyShow.DTO.UserDto;
import com.srh.BookMyShow.Service.Userservice;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/users")
public class UserController {
    private  final Userservice userservice;

    public UserController(Userservice userservice) {
        this.userservice = userservice;
    }
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(userservice.createUser(userDto));
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userservice.getUserbyId(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<UserDto> getUserByName(@PathVariable String name) {
        return ResponseEntity.ok(userservice.getUserByName(name));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userservice.getUserByEmail(email));
    }

    @GetMapping("/phonenumber/{phonenumber}")
    public ResponseEntity<UserDto> getUserByphonenumber(@PathVariable String phonenumber) {
        return ResponseEntity.ok(userservice.getUserByPhoneNumber(phonenumber));
    }


    @DeleteMapping("/id/{id}")
    public void deleteUser(Long id)
    {
        userservice.deleteUser(id);
    }
    @PutMapping
    public ResponseEntity<UserDto> updateTheater(@RequestBody UserDto userDto)
    {
        return ResponseEntity.ok(userservice.updateUser(userDto));
    }
}
