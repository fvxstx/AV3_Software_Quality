package com.example.fridgeapi.services.impl;

//import com.example.fridgeapi.models.FridgeItems;
import com.example.fridgeapi.models.Users;
import com.example.fridgeapi.dtos.LoginResponse;
import com.example.fridgeapi.repositories.UsersRepository;
import com.example.fridgeapi.services.UsersService;
import org.springframework.stereotype.Service;
//import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {

    UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Users createUser(Users user) {
        user.setCreatedAt(LocalDateTime.now());
        return usersRepository.save(user);
        
    }

    @Override
    public Users updateUser(Long id, Users user) {
        Users existingUser = usersRepository.findById(id).get();
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setType(user.getType());
        return usersRepository.save(existingUser);
//        return "Success";
    }

    @Override
    public String deleteUser(Long userId) {

        if (usersRepository.existsById(userId)) {

            usersRepository.deleteById(userId);
            return "Success";

        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public Users getUser(Long userId) {
        return usersRepository.findById(userId).get();
    }

    @Override
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public LoginResponse loginUser(String email, String password) {
        var user = usersRepository.findByEmail(email);

        if (user.isEmpty()) {
            return new LoginResponse("error", "User not found");
        }

        if (user.get().getPassword().equals(password)) {
            String token = UUID.randomUUID().toString();
            user.get().setToken(token);
            usersRepository.save(user.get());

            return new LoginResponse("success", token);
        } else {
            return new LoginResponse("error", "password incorrect");
        }
    }
}
