package com.tomas.messenger.service.implement;

import com.tomas.messenger.repositories.Entity.UserEntity;
import com.tomas.messenger.repositories.UserRepository;
import com.tomas.messenger.shared.UserDTO;
import com.tomas.messenger.shared.Utilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    Utilities utilities;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    UserEntity userEntity;
    UserDTO userDTO;

    String email = "aaa@test.com";
    String encryptedPassword = "aaa111bbb333";
    String firstName = "Tom";
    String lastName = "Kadlcek";


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userEntity = new UserEntity();
        userEntity.setEncryptedPassword(encryptedPassword);
        userEntity.setEmail(email);
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);

        userDTO = new UserDTO();
        userDTO.setEncryptedPassword(encryptedPassword);
        userDTO.setLastName(lastName);
        userDTO.setFirstName(firstName);
        userDTO.setEmail(email);
    }

    @Test
    void testCreateUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(utilities.generateUserId(anyInt())).thenReturn(encryptedPassword);
        when(userRepository.save(any())).thenReturn(userEntity);
        when(bCryptPasswordEncoder.encode(any())).thenReturn(encryptedPassword);

        UserDTO test = userService.createUser(userDTO);

        assertNotNull(test);
        assertEquals(email, test.getEmail());
        assertEquals(firstName, test.getFirstName());
        assertEquals(encryptedPassword, test.getEncryptedPassword());
    }

    @Test
    void testGetUserById() {
        when(userRepository.findByUserId(anyString())).thenReturn(userEntity);

        UserDTO test = userService.getUserById("aaaa");

        assertNotNull(test);
        assertEquals(email, test.getEmail());
        assertEquals(firstName, test.getFirstName());


    }

    @Test
    void getAllUsers() {
    }

    @Test
    void getUser() {
    }

    @Test
    void getSearchUsers() {
    }

}