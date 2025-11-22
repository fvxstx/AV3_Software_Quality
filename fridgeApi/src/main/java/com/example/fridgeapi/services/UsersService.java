package com.example.fridgeapi.services;

import com.example.fridgeapi.models.Users;
import com.example.fridgeapi.dtos.LoginResponse;
import org.springframework.stereotype.Service;

//import java.math.BigInteger;
import java.util.List;
import com.example.fridgeapi.dtos.LoginResponse;

public interface UsersService {
    public Users createUser(Users user);
    public Users updateUser(Long id, Users user);
    public String deleteUser(Long userId);
    public Users getUser(Long userId);
    public List<Users> getAllUsers();
    public LoginResponse loginUser(String email, String password);
}
