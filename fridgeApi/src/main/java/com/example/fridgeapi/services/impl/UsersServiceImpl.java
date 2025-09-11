package com.example.fridgeapi.services.impl;

import com.example.fridgeapi.models.Users;
import com.example.fridgeapi.repositories.UsersRepository;
import com.example.fridgeapi.services.UsersService;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public String createUser(Users user) {
        user.setCreatedAt(LocalDateTime.now());
        usersRepository.save(user);
        return "Success";
    }

    @Override
    public String updateUser(Users user) {
        usersRepository.save(user);
        return "Success";
    }

    @Override
    public String deleteUser(BigInteger userId) {
        usersRepository.deleteById(userId);
        return "Success";
    }

    @Override
    public Users getUser(BigInteger userId) {
        return usersRepository.findById(userId).get();
    }

    @Override
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public String loginUser(String email, String password) {
        var user = usersRepository.findByEmail(email);

        if (user.isEmpty()) throw new RuntimeException("User not found");
        if(user.get().getPassword().equals(password))
            return "token valid 123123123124123";

        else return "";
    }
}
