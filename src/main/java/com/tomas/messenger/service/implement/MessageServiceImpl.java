package com.tomas.messenger.service.implement;

import com.tomas.messenger.repositories.Entity.MessageEntity;
import com.tomas.messenger.repositories.Entity.UserEntity;
import com.tomas.messenger.repositories.MessageRepository;
import com.tomas.messenger.repositories.UserRepository;
import com.tomas.messenger.service.MessageService;
import com.tomas.messenger.shared.MessageDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public MessageDTO addMessage(String userId, MessageDTO messageDTO) {
        MessageDTO returnValue = new MessageDTO();

        UserEntity sender = userRepository.findByUserId(userId);
        UserEntity receiver = userRepository.findByUserId(messageDTO.getReceiverUserId());
        Timestamp current = new Timestamp(new Date().getTime());

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        MessageEntity messageEntity = modelMapper.map(messageDTO, MessageEntity.class);

        messageEntity.setReceiver(receiver);
        messageEntity.setSender(sender);
        messageEntity.setTimestamp(current);

        MessageEntity storedMessage = messageRepository.save(messageEntity);

        returnValue = modelMapper.map(storedMessage, MessageDTO.class);

        returnValue.setSenderUserId(sender.getUserId());
        returnValue.setReceiverUserId(receiver.getUserId());
        return returnValue;
    }


    @Override
    public List<MessageDTO> getAllMessages(String userId, String friendId) {
        List<MessageDTO> returnValue = new ArrayList<>();

        UserEntity user = userRepository.findByUserId(userId);
        UserEntity friend = userRepository.findByUserId(friendId);

        List<MessageEntity> allMessages = messageRepository.findAllByReceiverAndSenderOrReceiverAndSenderOrderByChatIdDesc(user, friend, friend, user);

        for (MessageEntity messageEntity : allMessages){
            MessageDTO messageDTO = new MessageDTO();
            BeanUtils.copyProperties(messageEntity, messageDTO);
            messageDTO.setReceiverUserId(messageEntity.getReceiver().getUserId());
            messageDTO.setSenderUserId(messageEntity.getSender().getUserId());
            messageDTO.setSenderName(messageEntity.getSender().getFirstName() + " " + messageEntity.getSender().getLastName());
            messageDTO.setReceiverName(messageEntity.getReceiver().getFirstName() + " " + messageEntity.getReceiver().getLastName());
            returnValue.add(messageDTO);
        }
        return returnValue;
    }
}
