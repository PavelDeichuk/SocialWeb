package com.pavel.socialweb.Repository;

import com.pavel.socialweb.Dto.SubscriptionDto;
import com.pavel.socialweb.Entity.SubscriptionEntity;
import com.pavel.socialweb.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {
    Optional<SubscriptionEntity> findBySubUserAndPubUser(UserEntity subUser, UserEntity pubUser);

    List<SubscriptionEntity> findBySubUser(UserEntity subUser);

    Boolean existsBySubUserAndPubUser(UserEntity subUser, UserEntity pubUser);
}
