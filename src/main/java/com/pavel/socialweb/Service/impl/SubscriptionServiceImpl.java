package com.pavel.socialweb.Service.impl;

import com.pavel.socialweb.Dto.SubscriptionDto;
import com.pavel.socialweb.Entity.SubscriptionEntity;
import com.pavel.socialweb.Entity.UserEntity;
import com.pavel.socialweb.Exception.NotFoundException;
import com.pavel.socialweb.Mapper.SubscriptionMapper;
import com.pavel.socialweb.Repository.SubscriptionRepository;
import com.pavel.socialweb.Repository.UserRepository;
import com.pavel.socialweb.Service.SubscriptionService;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserRepository userRepository;

    private final SubscriptionRepository subscriptionRepository;

    private final SubscriptionMapper subscriptionMapper;

    public SubscriptionServiceImpl(UserRepository userRepository, SubscriptionRepository subscriptionRepository, SubscriptionMapper subscriptionMapper) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionMapper = subscriptionMapper;
    }


    @Override
    public SubscriptionDto subscription(Long sub_user_id, Long pub_user_id) {
        UserEntity subUser = userRepository
                .findById(sub_user_id)
                .orElseThrow(() -> new NotFoundException("Not found for sub user id!"));
        UserEntity pubUser = userRepository
                .findById(pub_user_id)
                .orElseThrow(() -> new NotFoundException("Not found for pub user id!"));
        SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
        subscriptionEntity.setSubUser(subUser);
        subscriptionEntity.setPubUser(pubUser);
        SubscriptionEntity save = subscriptionRepository.save(subscriptionEntity);
        return subscriptionMapper.SUBSCRIPTION_DTO(save);
    }

    @Override
    public SubscriptionDto unSubscription(Long sub_user_id, Long pub_user_id) {
        UserEntity subUser = userRepository
                .findById(sub_user_id)
                .orElseThrow(() -> new RuntimeException("Not found for sub user id!"));
        UserEntity pubUser = userRepository
                .findById(pub_user_id)
                .orElseThrow(() -> new RuntimeException("Not found for pub user id!"));
       SubscriptionEntity subscription = subscriptionRepository
               .findBySubUserAndPubUser(subUser, pubUser)
               .orElseThrow(() -> new NotFoundException("Not found for subscription!"));
       subscriptionRepository.delete(subscription);
       return subscriptionMapper.SUBSCRIPTION_DTO(subscription);
    }
}
