package com.example.fridgeapi.repositories;

import com.example.fridgeapi.models.Fridges;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface FridgesRepository extends JpaRepository<Fridges, BigInteger> {

}

