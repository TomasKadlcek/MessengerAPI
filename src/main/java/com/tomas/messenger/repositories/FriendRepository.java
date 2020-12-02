package com.tomas.messenger.repositories;

import com.tomas.messenger.repositories.Entity.FriendEntity;
import com.tomas.messenger.repositories.Entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends PagingAndSortingRepository<FriendEntity, Long> {

    FriendEntity findByFriendEmailAndUser(String email, UserEntity userEntity);
    List<FriendEntity> findAllByUserOrderByIdDesc(UserEntity userEntity);
    List<FriendEntity> findByUser(UserEntity user);

}
