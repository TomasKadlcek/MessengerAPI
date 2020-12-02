package com.tomas.messenger.service.implement;

import com.tomas.messenger.repositories.Entity.FriendEntity;
import com.tomas.messenger.repositories.Entity.UserEntity;
import com.tomas.messenger.repositories.FriendRepository;
import com.tomas.messenger.repositories.UserRepository;
import com.tomas.messenger.service.UserService;
import com.tomas.messenger.shared.UserDTO;
import com.tomas.messenger.shared.Utilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FriendRepository friendRepository;

    @Autowired
    Utilities utilities;

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        if (userRepository.findByEmail(userDTO.getEmail()) != null)throw new RuntimeException("Email Exists");

        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);


        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        userEntity.setUserId(utilities.generateUserId(30));

        UserEntity storedEntity = userRepository.save(userEntity);

        return modelMapper.map(storedEntity, UserDTO.class);
    }

    @Override
    public UserDTO getUserById(String userId) {
        UserDTO returnValue = new UserDTO();

        UserEntity userEntity = userRepository.findByUserId(userId);

        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserDTO> returnValue = new ArrayList<>();

        List<UserEntity> allUserEntities = (List<UserEntity>) userRepository.findAll();

        for (UserEntity userEntity : allUserEntities){
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(userEntity, userDTO);
            returnValue.add(userDTO);
        }
         return returnValue;
    }

    @Override
    public UserDTO getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) throw new UsernameNotFoundException(email);

        UserDTO returnValue = new UserDTO();

        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    @Override
    public List<UserDTO> getSearchUsers(String id) {
        List<UserDTO> returnValue = new ArrayList<>();

        UserEntity user = userRepository.findByUserId(id);

        List<FriendEntity> inFriendList = friendRepository.findByUser(user);
        List<Long> ids = new ArrayList<>();
        ids.add(0L);

        for (FriendEntity friend : inFriendList){
            ids.add(friend.getFriendId());
        }
        List<UserEntity> notInFriendList = userRepository.findByIdNotIn(ids);

        for (UserEntity users : notInFriendList){
            if(users.getId() != user.getId()){
                UserDTO userDTO = new UserDTO();
                BeanUtils.copyProperties(users, userDTO);
                returnValue.add(userDTO);
            }
        }

        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null)throw new UsernameNotFoundException("User not found");

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}
