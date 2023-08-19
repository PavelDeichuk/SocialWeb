package com.pavel.socialweb.Repository;

import com.pavel.socialweb.Entity.FriendEntity;
import com.pavel.socialweb.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<FriendEntity,Long> {
    Boolean existsByFirstUserAndSecondUser(UserEntity firstUser, UserEntity secondUser);

    Optional<FriendEntity> findByFirstUserAndSecondUser(UserEntity firstUser, UserEntity secondUser);

    List<FriendEntity> findByFirstUser(UserEntity firstUser);

    List<FriendEntity> findBySecondUser(UserEntity secondUser);
}
