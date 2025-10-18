package com.example.fridgeapi.services;

import com.example.fridgeapi.models.Users;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsersService {
    public String createUser(Users user);
    public Users updateUser(Long id, Users user);
    public String deleteUser(Long userId);
    public Users getUser(Long userId);
    public List<Users> getAllUsers();
    public String loginUser(String email, String password);
}
