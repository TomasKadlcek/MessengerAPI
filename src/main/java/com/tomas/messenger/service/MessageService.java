package com.tomas.messenger.service;

import com.tomas.messenger.shared.MessageDTO;

import java.util.List;

public interface MessageService {

    MessageDTO addMessage(String userId, MessageDTO messageDTO);
    List<MessageDTO> getAllMessages(String userId, String friendId);


}
