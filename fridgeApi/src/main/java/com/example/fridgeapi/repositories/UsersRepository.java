package com.example.fridgeapi.repositories;

import com.example.fridgeapi.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface UsersRepository extends JpaRepository<Users, BigInteger> {

}
