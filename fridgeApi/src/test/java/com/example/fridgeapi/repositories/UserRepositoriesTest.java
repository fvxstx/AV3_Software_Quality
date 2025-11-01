package com.example.fridgeapi.repositories;

import com.example.fridgeapi.models.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoriesTest {
    @Autowired
    private UsersRepository usersRepository;

    @Test
    void testSaveAndFindUserByEmail() {
        Users user = new Users();
        user.setName("Vini");
        user.setEmail("vini@email.com");
        user.setPassword("123");

        usersRepository.save(user);

        Optional<Users> found = usersRepository.findByEmail("vini@email.com");
        assertTrue(found.isPresent());
        assertEquals("Vini", found.get().getName());
    }
}

