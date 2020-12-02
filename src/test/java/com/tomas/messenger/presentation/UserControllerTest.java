package com.tomas.messenger.presentation;

import com.tomas.messenger.presentation.model.request.FriendDetailsRequestModel;
import com.tomas.messenger.presentation.model.request.UserDetailsRequestModel;
import com.tomas.messenger.presentation.model.response.FriendResponse;
import com.tomas.messenger.presentation.model.response.UserResponse;
import com.tomas.messenger.service.FriendService;
import com.tomas.messenger.service.UserService;
import com.tomas.messenger.shared.FriendDTO;
import com.tomas.messenger.shared.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Mock
    FriendService friendService;

    UserDTO userDTO;
    UserDTO userDTO2;
    List<UserDTO> list;
    FriendDTO friendDTO;
    FriendDTO friendDTO2;
    List<FriendDTO> list2;
    FriendDetailsRequestModel friendModel;

    UserDetailsRequestModel userDetailsRequestModel;

    String email = "aaa@test.com";
    String encryptedPassword = "aaa111bbb333";
    String firstName = "Tom";
    String lastName = "Kadlcek";
    long id = 1234L;

    String email2 = "bbb@test.com";
    String encryptedPassword2 = "ccc222111aaa";
    String firstName2 = "Franta";
    String lastName2 = "Karlicek";
    long id2 = 12345L;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        userDTO = new UserDTO();
        userDTO.setEmail(email);
        userDTO.setEncryptedPassword(encryptedPassword);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setId(id);

        userDTO2 = new UserDTO();
        userDTO2.setEmail(email2);
        userDTO2.setEncryptedPassword(encryptedPassword2);
        userDTO2.setFirstName(firstName2);
        userDTO2.setLastName(lastName2);
        userDTO2.setId(id2);

        userDetailsRequestModel = new UserDetailsRequestModel();
        userDetailsRequestModel.setEmail(email);
        userDetailsRequestModel.setFirstName(firstName);
        userDetailsRequestModel.setLastName(lastName);

        list = new ArrayList<>();
        list.add(userDTO);
        list.add(userDTO2);

        friendDTO = new FriendDTO();
        friendDTO.setLastName(lastName);
        friendDTO.setFirstName(firstName);
        friendDTO.setFriendEmail(email);
        friendDTO.setId(id);

        friendDTO2 = new FriendDTO();
        friendDTO2.setLastName(lastName2);
        friendDTO2.setFirstName(firstName2);
        friendDTO2.setFriendEmail(email2);
        friendDTO2.setId(id2);
        friendModel = new FriendDetailsRequestModel();
        friendModel.setFriendEmail(email);

        list2 = new ArrayList<>();
        list2.add(friendDTO);
        list2.add(friendDTO2);
    }

    @Test
    void testCreateUser() {
        when(userService.createUser(any())).thenReturn(userDTO);

        UserResponse userResponse = userController.createUser(userDetailsRequestModel);

        assertNotNull(userResponse);
        assertEquals(email, userResponse.getEmail());

    }

    @Test
    void testGetUser() {
        when(userService.getUserById(anyString())).thenReturn(userDTO);

        UserResponse userResponse = userController.getUser("aaa");

        assertNotNull(userResponse);
        assertEquals(email, userResponse.getEmail());
        assertEquals(firstName, userResponse.getFirstName());

    }


    @Test
    void testGetAllUsers() {
        when(userService.getAllUsers()).thenReturn(list);

        List<UserResponse> test = userController.getAllUsers();

        assertNotNull(test);
        assertEquals(list.get(1).getEmail(), test.get(1).getEmail());
        assertEquals(list.get(0).getFirstName(), test.get(0).getFirstName());
    }

    @Test
    void testGetSearchUsers() {
        when(userService.getSearchUsers(anyString())).thenReturn(list);

        List<UserResponse> test = userController.getSearchUsers("aa11");

        assertNotNull(test);
        assertEquals(list.get(1).getEmail(), test.get(1).getEmail());
        assertEquals(list.get(0).getFirstName(), test.get(0).getFirstName());
    }

    @Test
    void testAddFriend() {
        when(friendService.addFriend(any(), anyString())).thenReturn(friendDTO);

        FriendResponse test = userController.addFriend(friendModel, "aaa");

        assertNotNull(test);
        assertEquals(email, test.getFriendEmail());
    }

    @Test
    void testGetFriendList() {
        when(friendService.getAllFriends(anyString())).thenReturn(list2);

        List<FriendResponse> test = userController.getFriendList("aaa");

        assertNotNull(test);
        assertEquals(email, test.get(0).getFriendEmail());
        assertEquals(email2, test.get(1).getFriendEmail());
    }

    @Test
    void testAddMessage() {
    }

    @Test
    void testGetAllMessages() {
    }
}