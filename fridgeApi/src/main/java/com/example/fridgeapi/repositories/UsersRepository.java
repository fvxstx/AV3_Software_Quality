package com.example.fridgeapi.repositories;

import com.example.fridgeapi.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

//import java.math.BigInteger;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
}
