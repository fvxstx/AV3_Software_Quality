package com.example.fridgeapi.controllers;

import com.example.fridgeapi.models.Users;
import com.example.fridgeapi.services.UsersService;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("{userId}")
    public Users getUserDetails(@PathVariable("userId") BigInteger userId){
        return usersService.getUser(userId);
    }

    @GetMapping()
    public List<Users> getAllUserDetails(){
        return usersService.getAllUsers();
    }

    @PostMapping
    public String createUserDetails(@RequestBody Users users){
        return usersService.createUser(users);
    }

    @PutMapping
    public String updateUserDetails(@RequestBody Users users){
        return usersService.updateUser(users);
    }

    @DeleteMapping("{userId}")
    public String deleteUserDetails(@PathVariable("userId") BigInteger userId){
        return usersService.deleteUser(userId);
    }
}
