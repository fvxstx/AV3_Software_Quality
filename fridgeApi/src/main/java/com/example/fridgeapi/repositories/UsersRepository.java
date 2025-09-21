package com.example.fridgeapi.repositories;

import com.example.fridgeapi.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, BigInteger> {
<<<<<<< .merge_file_xuwMKo
    Optional<Users> findByEmail(String email);
=======
    
>>>>>>> .merge_file_3L5Xnu
}
