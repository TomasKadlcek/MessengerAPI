package com.tomas.messenger.service.implement;

import com.tomas.messenger.repositories.Entity.FriendEntity;
import com.tomas.messenger.repositories.Entity.MessageEntity;
import com.tomas.messenger.repositories.Entity.UserEntity;
import com.tomas.messenger.repositories.FriendRepository;
import com.tomas.messenger.repositories.MessageRepository;
import com.tomas.messenger.repositories.UserRepository;
import com.tomas.messenger.service.FriendService;
import com.tomas.messenger.shared.FriendDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    FriendRepository friendRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;


    @Override
    public FriendDTO addFriend(FriendDTO friendDTO, String userId) {
        FriendDTO returnValue = new FriendDTO();

        UserEntity userEntity = userRepository.findByUserId(userId);
        UserEntity friend = userRepository.findByEmail(friendDTO.getFriendEmail());

        if (friendRepository.findByFriendEmailAndUser(friendDTO.getFriendEmail(), userEntity) != null)throw new RuntimeException("Friend Exists");

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        FriendEntity friendEntity = modelMapper.map(friendDTO, FriendEntity.class);


        friendEntity.setFriendId(friend.getId());
        friendEntity.setUser(userEntity);


        FriendEntity secondAdd = new FriendEntity();
        secondAdd.setFriendId(userEntity.getId());
        secondAdd.setFriendEmail(userEntity.getEmail());
        secondAdd.setUser(friend);

        FriendEntity storedEntity = friendRepository.save(friendEntity);
        friendRepository.save(secondAdd);

        returnValue = modelMapper.map(storedEntity, FriendDTO.class);

        return returnValue;
    }

    @Override
    public List<FriendDTO> getAllFriends(String id) {
        // add order by message timestamp or descending by friendlist id...
        List<FriendDTO> returnValue = new ArrayList<>();

        UserEntity user = userRepository.findByUserId(id);

        List<FriendEntity> allFriendEntities = friendRepository.findAllByUserOrderByIdDesc(user);
        List<FriendEntity> messaged = new ArrayList<>();
        List<FriendEntity> notMessaged = new ArrayList<>();
        List<FriendEntity> sortedFriendEntities = new ArrayList<>();


        for (FriendEntity friend : allFriendEntities){
            UserEntity friendEnt = userRepository.findByEmail(friend.getFriendEmail());
            MessageEntity message = messageRepository.findFirstByReceiverAndSenderOrReceiverAndSenderOrderByChatIdDesc(user, friendEnt, friendEnt, user);

            if (message == null){
                notMessaged.add(friend);
            }
            else{
                friend.setLastMessageId(message.getChatId());
                messaged.add(friend);
            }
        }
        messaged.sort((o1, o2) -> {
            long messageId1 = o1.getLastMessageId();
            long messageId2 = o2.getLastMessageId();
            return (int) (messageId2-messageId1);
        });

        sortedFriendEntities.addAll(messaged);
        sortedFriendEntities.addAll(notMessaged);


        for (FriendEntity friendEntity : sortedFriendEntities){
            FriendDTO friendDTO = new FriendDTO();
            BeanUtils.copyProperties(friendEntity, friendDTO);
            friendDTO.setFriendUserId(userRepository.findByEmail(friendEntity.getFriendEmail()).getUserId());
            friendDTO.setFirstName(userRepository.findByEmail(friendEntity.getFriendEmail()).getFirstName());
            friendDTO.setLastName(userRepository.findByEmail(friendEntity.getFriendEmail()).getLastName());
            returnValue.add(friendDTO);
        }
        return returnValue;
    }


}
