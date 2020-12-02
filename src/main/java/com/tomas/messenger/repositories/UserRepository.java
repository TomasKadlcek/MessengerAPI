package com.tomas.messenger.repositories;

import com.tomas.messenger.repositories.Entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
    // Add find by methods....

    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userId);
    UserEntity findById(long id);
    List<UserEntity> findByIdNotIn(List<Long> ids);

}
