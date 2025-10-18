package com.example.fridgeapi.controllers;

import com.example.fridgeapi.dtos.LoginDto;
import com.example.fridgeapi.models.Users;
import com.example.fridgeapi.services.UsersService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String loginUser(@RequestBody LoginDto loginDto){
        return usersService.loginUser(loginDto.email(), loginDto.password());}

    @PostMapping
    public String createUserDetails(@RequestBody Users users){
        return usersService.createUser(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUserDetails(@PathVariable Long id, @RequestBody Users users){
        Users updateUser = usersService.updateUser(id, users);
        return ResponseEntity.ok((updateUser));
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<String> deleteUserDetails(@PathVariable("userId") Long userId){
        usersService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}