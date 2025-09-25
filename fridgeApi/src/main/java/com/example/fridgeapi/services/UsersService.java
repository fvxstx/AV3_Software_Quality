package com.example.fridgeapi.services;

import com.example.fridgeapi.models.Users;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public interface UsersService {
    public String createUser(Users user);
    public String updateUser(Users user);
    public String deleteUser(BigInteger userId);
    public Users getUser(BigInteger userId);
    public List<Users> getAllUsers();
    public String loginUser(String email, String password);
}
