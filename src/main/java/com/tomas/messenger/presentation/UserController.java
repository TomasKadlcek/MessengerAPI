package com.tomas.messenger.presentation;

import com.tomas.messenger.presentation.model.request.FriendDetailsRequestModel;
import com.tomas.messenger.presentation.model.request.MessageDetailsRequest;
import com.tomas.messenger.presentation.model.request.UserDetailsRequestModel;
import com.tomas.messenger.presentation.model.response.FriendResponse;
import com.tomas.messenger.presentation.model.response.MessageResponse;
import com.tomas.messenger.presentation.model.response.UserResponse;
import com.tomas.messenger.repositories.UserRepository;
import com.tomas.messenger.service.FriendService;
import com.tomas.messenger.service.MessageService;
import com.tomas.messenger.service.UserService;
import com.tomas.messenger.shared.FriendDTO;
import com.tomas.messenger.shared.MessageDTO;
import com.tomas.messenger.shared.UserDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    FriendService friendService;

    @Autowired
    MessageService messageService;


    @PostMapping
    public UserResponse createUser(@RequestBody UserDetailsRequestModel userDetails){

        // Initialize returnValue object of UserResponse class
        UserResponse returnValue = new UserResponse();

        // Using ModelMapper to transfer data between objects
        ModelMapper modelMapper = new ModelMapper();
        UserDTO userDTO = modelMapper.map(userDetails, UserDTO.class);

        // Send to service class and map data to returnValue
        UserDTO createdUser = userService.createUser(userDTO);
        returnValue = modelMapper.map(createdUser, UserResponse.class);

        return returnValue;
    }

    @GetMapping(path = "/{id}")
    public UserResponse getUser(@PathVariable String id){
        UserResponse returnValue = new UserResponse();

        UserDTO userDTO = userService.getUserById(id);
        ModelMapper modelMapper = new ModelMapper();
        returnValue = modelMapper.map(userDTO, UserResponse.class);

        return returnValue;
    }

    @GetMapping
    public List<UserResponse> getAllUsers(){
        List<UserResponse> returnValue = new ArrayList<>();

        List<UserDTO> allUsers = userService.getAllUsers();

        for (UserDTO userDTO : allUsers){
            UserResponse userResponse = new UserResponse();
            BeanUtils.copyProperties(userDTO, userResponse);
            returnValue.add(userResponse);
        }

        return returnValue;
    }

    @GetMapping(path = "/{id}/search")
    public List<UserResponse> getSearchUsers(@PathVariable String id){

        List<UserResponse> returnValue = new ArrayList<>();

        List<UserDTO> searchUsers = userService.getSearchUsers(id);

        for (UserDTO user : searchUsers){
            UserResponse userResponse = new UserResponse();
            BeanUtils.copyProperties(user, userResponse);
            returnValue.add(userResponse);
        }
        return returnValue;
    }

    @PostMapping(path = "/{id}/friends")
    public FriendResponse addFriend(@RequestBody FriendDetailsRequestModel friendDetails, @PathVariable String id){

        FriendResponse returnValue = new FriendResponse();

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        FriendDTO friendDTO = modelMapper.map(friendDetails, FriendDTO.class);

        FriendDTO addedFriend = friendService.addFriend(friendDTO, id);

        returnValue = modelMapper.map(addedFriend, FriendResponse.class);

        return returnValue;
    }

    @GetMapping(path = "/{id}/friends")
    public List<FriendResponse> getFriendList (@PathVariable String id){
        List<FriendResponse> returnValue = new ArrayList<>();

        List<FriendDTO> allFriends = friendService.getAllFriends(id);

        for (FriendDTO friendDTO : allFriends){
            FriendResponse friendResponse = new FriendResponse();
            BeanUtils.copyProperties(friendDTO, friendResponse);
            returnValue.add(friendResponse);
        }
        return returnValue;
    }

    @PostMapping(path = "/{id}/messages")
    public MessageResponse addMessage (@PathVariable String id, @RequestBody MessageDetailsRequest messageDetailsRequest){
        MessageResponse returnValue = new MessageResponse();

        ModelMapper modelMapper = new ModelMapper();
        MessageDTO messageDTO = modelMapper.map(messageDetailsRequest, MessageDTO.class);

        MessageDTO createdMessage = messageService.addMessage(id, messageDTO);
        returnValue = modelMapper.map(createdMessage, MessageResponse.class);
        return returnValue;
    }

    @GetMapping(path = "{userid}/messages/{friendid}")
    public List<MessageResponse> getAllMessages (@PathVariable String userid, @PathVariable String friendid) {
        List<MessageResponse> returnValue = new ArrayList<>();

        List<MessageDTO> allMessages = messageService.getAllMessages(userid, friendid);

        for (MessageDTO messageDTO : allMessages){
            MessageResponse messageResponse = new MessageResponse();
            BeanUtils.copyProperties(messageDTO, messageResponse);
            returnValue.add(messageResponse);
        }
        return returnValue;
    }


}
