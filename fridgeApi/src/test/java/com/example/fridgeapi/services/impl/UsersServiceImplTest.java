package com.example.fridgeapi.services.impl;

import com.example.fridgeapi.models.Users;
import com.example.fridgeapi.dtos.LoginResponse;
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
        user2.setId(3L);

        List<Users> userList = Arrays.asList(user, user2);

        when(usersRepository.findAll()).thenReturn(userList);

        List<Users> foundUsers = usersService.getAllUsers();

        assertEquals(2, foundUsers.size());
        verify(usersRepository, times(1)).findAll();
    }

    @Test
    public void UsersServiceImpl_deleteUser_ReturnsSuccess() {/// /////
        when(usersRepository.existsById(2L)).thenReturn(true);
        doNothing().when(usersRepository).deleteById(2L);

        String result = usersService.deleteUser(2L);

        assertEquals("Success", result);
        verify(usersRepository, times(1)).deleteById(2L);
    }

    @Test
    public void UsersServiceImpl_loginUser_ReturnsTokenOnSuccess() {
        when(usersRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        LoginResponse response = usersService.loginUser(user.getEmail(), "password123");

        assertEquals(user.getToken(), response.token());


        verify(usersRepository, times(1)).findByEmail(user.getEmail());
    }

    @Test
    public void UsersServiceImpl_loginUser_ThrowsExceptionWhenUserNotFound() { /// //////
        when(usersRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        LoginResponse response = usersService.loginUser("x@x.com", "password");

        assertEquals("error", response.status());
        assertEquals("User not found", response.token());
    }

    @Test
    public void UsersServiceImpl_loginUser_ReturnsEmptyStringOnIncorrectPassword() {  /// //////
        when(usersRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        LoginResponse result = usersService.loginUser(user.getEmail(), "wrongPassword");

        assertEquals("error", result.status());
        assertEquals("password incorrect", result.token());


        verify(usersRepository, times(1)).findByEmail(user.getEmail());
    }
}