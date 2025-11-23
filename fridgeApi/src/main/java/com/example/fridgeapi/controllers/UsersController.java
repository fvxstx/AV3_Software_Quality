package com.example.fridgeapi.controllers;

import com.example.fridgeapi.dtos.LoginDto;
import com.example.fridgeapi.dtos.LoginResponse;
import com.example.fridgeapi.models.Users;
import com.example.fridgeapi.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

@GetMapping("{userId}")
    public ResponseEntity<Users> getUserDetails(@PathVariable("userId") Long userId){
        Users user = usersService.getUser(userId);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping()
    public ResponseEntity<List<Users>> getAllUserDetails(){
        List<Users> listUsers= usersService.getAllUsers();
        return ResponseEntity.ok().body(listUsers);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginDto loginDto){
        LoginResponse response = usersService.loginUser(loginDto.email(), loginDto.password());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Users> createUserDetails(@RequestBody Users users){
        Users createdUser = usersService.createUser(users);

        return ResponseEntity.ok((createdUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUserDetails(@PathVariable Long id, @RequestBody Users users){
        Users updateUser = usersService.updateUser(id, users);
        return ResponseEntity.ok((updateUser));
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<String> deleteUserDetails(@PathVariable("userId") Long userId){
        usersService.deleteUser(userId);
        String message = "Success";
        return ResponseEntity.ok((message));
    }
}