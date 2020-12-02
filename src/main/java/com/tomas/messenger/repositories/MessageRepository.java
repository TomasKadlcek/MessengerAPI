package com.tomas.messenger.repositories;

import com.tomas.messenger.repositories.Entity.MessageEntity;
import com.tomas.messenger.repositories.Entity.UserEntity;
import com.tomas.messenger.service.MessageService;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends PagingAndSortingRepository<MessageEntity, Long> {
    MessageEntity findFirstByReceiverAndSenderOrReceiverAndSenderOrderByChatIdDesc(UserEntity user, UserEntity user2, UserEntity user3, UserEntity user4);

    List<MessageEntity> findAllByReceiverAndSenderOrReceiverAndSenderOrderByChatIdDesc(UserEntity user, UserEntity user2, UserEntity user3, UserEntity user4);


}
