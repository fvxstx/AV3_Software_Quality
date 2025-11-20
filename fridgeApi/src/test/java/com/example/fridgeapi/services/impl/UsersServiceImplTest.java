package com.example.fridgeapi.services.impl;

import com.example.fridgeapi.models.Users;
import com.example.fridgeapi.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.lang.Long;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersServiceImplTest {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UsersServiceImpl usersService;

    private Users user;

    @BeforeEach
    public void setup() {
        user = new Users();
        user.setId(2L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        user.setToken("token123");
    }

    @Test
    public void UsersServiceImpl_createUser_ReturnsSuccess() {
        when(usersRepository.save(any(Users.class))).thenReturn(user);

        Users result = usersService.createUser(user);

        assertEquals(2, result.getId());
        verify(usersRepository, times(1)).save(user);
    }

    @Test
    public void UsersServiceImpl_getUser_ReturnsUser() {
        when(usersRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

        Users foundUser = usersService.getUser(Long.valueOf(1));

        assertEquals(user.getName(), foundUser.getName());
        verify(usersRepository, times(1)).findById(Long.valueOf(1));
    }

    @Test
    public void UsersServiceImpl_getUser_ThrowsExceptionWhenUserNotFound() {
        when(usersRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(java.util.NoSuchElementException.class, () -> usersService.getUser(Long.valueOf(1)));
        verify(usersRepository, times(1)).findById(Long.valueOf(1));
    }

    @Test
    public void UsersServiceImpl_getAllUsers_ReturnsListOfUsers() {
        Users user2 = new Users();
        user2.setId(2L);

        List<Users> userList = Arrays.asList(user, user2);

        when(usersRepository.findAll()).thenReturn(userList);

        List<Users> foundUsers = usersService.getAllUsers();

        assertEquals(2, foundUsers.size());
        verify(usersRepository, times(1)).findAll();
    }

    @Test
    public void UsersServiceImpl_deleteUser_ReturnsSuccess() {
        doNothing().when(usersRepository).deleteById(any(Long.class));

        String result = usersService.deleteUser(Long.valueOf(1));

        assertEquals("Success", result);
        verify(usersRepository, times(1)).deleteById(Long.valueOf(1));
    }

    @Test
    public void UsersServiceImpl_loginUser_ReturnsTokenOnSuccess() {
        when(usersRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        String result = usersService.loginUser(user.getEmail(), "password123");

        assertEquals("Token: " + user.getToken(), result);
        verify(usersRepository, times(1)).findByEmail(user.getEmail());
    }

    @Test
    public void UsersServiceImpl_loginUser_ThrowsExceptionWhenUserNotFound() {
        when(usersRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> usersService.loginUser("nonexistent@example.com", "password123"));
        verify(usersRepository, times(1)).findByEmail("nonexistent@example.com");
    }

    @Test
    public void UsersServiceImpl_loginUser_ReturnsEmptyStringOnIncorrectPassword() {
        when(usersRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        String result = usersService.loginUser(user.getEmail(), "wrongPassword");

        assertEquals("", result);
        verify(usersRepository, times(1)).findByEmail(user.getEmail());
    }
}