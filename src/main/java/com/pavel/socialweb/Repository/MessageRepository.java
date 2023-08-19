package com.pavel.socialweb.Repository;

import com.pavel.socialweb.Entity.MessageEntity;
import com.pavel.socialweb.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<MessageEntity,Long> {
    Optional<MessageEntity> findByFirstUserAndSecondUser(UserEntity firstUser, UserEntity secondUser);

    List<MessageEntity> findByFirstUser(UserEntity firstUser);

    List<MessageEntity> findBySecondUser(UserEntity secondUser);
}
