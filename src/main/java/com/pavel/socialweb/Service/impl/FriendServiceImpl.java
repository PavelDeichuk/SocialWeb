package com.pavel.socialweb.Service.impl;

import com.pavel.socialweb.Dto.FriendDto;
import com.pavel.socialweb.Dto.UserDto;
import com.pavel.socialweb.Entity.FriendEntity;
import com.pavel.socialweb.Entity.UserEntity;
import com.pavel.socialweb.Enum.FriendStatus;
import com.pavel.socialweb.Exception.BadRequestException;
import com.pavel.socialweb.Exception.NotFoundException;
import com.pavel.socialweb.Mapper.FriendMapper;
import com.pavel.socialweb.Mapper.UserMapper;
import com.pavel.socialweb.Repository.FriendRepository;
import com.pavel.socialweb.Repository.SubscriptionRepository;
import com.pavel.socialweb.Repository.UserRepository;
import com.pavel.socialweb.Service.FriendService;
import com.pavel.socialweb.Service.SubscriptionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {
    private final FriendRepository friendRepository;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final FriendMapper friendMapper;

    private final SubscriptionRepository subscriptionRepository;

    private final SubscriptionService subscriptionService;

    public FriendServiceImpl(FriendRepository friendRepository, UserRepository userRepository, UserMapper userMapper, FriendMapper friendMapper, SubscriptionRepository subscriptionRepository, SubscriptionService subscriptionService) {
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.friendMapper = friendMapper;
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionService = subscriptionService;
    }

    @Override
    public List<UserDto> GetFriends(Long user_id) {
        UserEntity user = userRepository
                .findById(user_id)
                .orElseThrow(() -> new NotFoundException("Not found for first user id!"));
        List<FriendEntity> firstFriend = friendRepository.findByFirstUser(user);
        List<FriendEntity> secondFriend = friendRepository.findBySecondUser(user);
        List<UserEntity> friendUsers = new ArrayList<>();
        for (FriendEntity friend : firstFriend) {
            if(friend.getStatus() == FriendStatus.ACCEPT) {
                friendUsers.add(userRepository.findById(friend.getSecondUser().getId()).get());
            }
        }
        for (FriendEntity friend : secondFriend) {
            if(friend.getStatus() == FriendStatus.ACCEPT) {
                friendUsers.add(userRepository.findById(friend.getFirstUser().getId()).get());
            }
        }
        if(friendUsers.isEmpty()) {
            throw new BadRequestException("You not have friends! :(");
        }
        return friendUsers
                .stream()
                .map(userMapper::USER_DTO)
                .toList();
    }

    @Override
    public FriendDto AddFriend(Long first_user_id, Long second_user_id) {
       UserEntity firstUser = userRepository
                .findById(first_user_id)
                .orElseThrow(() -> new NotFoundException("Not found for first user id!"));
       UserEntity secondUser = userRepository
                .findById(second_user_id)
                .orElseThrow(() -> new NotFoundException("Not found for second id!"));
        FriendEntity friendEntity = new FriendEntity();
        friendEntity.setStatus(FriendStatus.SEND);
        friendEntity.setFirstUser(firstUser);
        friendEntity.setSecondUser(secondUser);
        FriendEntity save = friendRepository.save(friendEntity);
        if(!subscriptionRepository.existsBySubUserAndPubUser(firstUser, secondUser)){
            subscriptionService.subscription(first_user_id, second_user_id);
        }
        return friendMapper.FRIEND_DTO(save);
    }

    @Override
    public FriendDto AcceptFriend(Long friend_id) {
        FriendEntity friend = friendRepository
                .findById(friend_id)
                .orElseThrow(() -> new NotFoundException("Not found for friend id!"));
        friend.setStatus(FriendStatus.ACCEPT);
        FriendEntity save = friendRepository.save(friend);
        if(!subscriptionRepository.existsBySubUserAndPubUser(friend.getSecondUser(), friend.getFirstUser())){
            subscriptionService.subscription(friend.getSecondUser().getId(), friend.getFirstUser().getId());
        }
        return friendMapper.FRIEND_DTO(save);
    }

    @Override
    public FriendDto DeniedFriend(Long friend_id) {
       FriendEntity friend = friendRepository
                .findById(friend_id)
                .orElseThrow(() -> new NotFoundException("Not found for friend id!"));
       friend.setStatus(FriendStatus.DENIED);
       FriendEntity save = friendRepository.save(friend);
       return friendMapper.FRIEND_DTO(save);
    }

    @Override
    public FriendDto DeleteFriend(Long friend_id) {
     FriendEntity friend = friendRepository
              .findById(friend_id)
              .orElseThrow(() -> new NotFoundException("Not found for friend id"));
      friendRepository.deleteById(friend_id);
      subscriptionService.unSubscription(friend.getFirstUser().getId(), friend.getSecondUser().getId());
      return friendMapper.FRIEND_DTO(friend);
    }
}
