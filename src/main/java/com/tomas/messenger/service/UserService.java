package com.tomas.messenger.service;

import com.tomas.messenger.shared.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDTO createUser(UserDTO userDTO);
    UserDTO getUserById(String userId);
    List<UserDTO> getAllUsers();
    UserDTO getUser(String email);

    List<UserDTO> getSearchUsers(String id);
}
