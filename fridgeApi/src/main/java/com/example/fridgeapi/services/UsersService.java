package com.example.fridgeapi.services;

import com.example.fridgeapi.models.Users;

import java.math.BigInteger;
import java.util.List;

public interface UsersService {
    public String createUser(Users user);
    public String updateUser(Users user);
    public String deleteUser(BigInteger userId);
    public Users getUser(BigInteger userId);
    public List<Users> getAllUsers();
}
