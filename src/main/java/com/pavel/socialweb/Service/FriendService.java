package com.pavel.socialweb.Service;

import com.pavel.socialweb.Dto.FriendDto;
import com.pavel.socialweb.Dto.UserDto;

import java.util.List;

public interface FriendService {
    List<UserDto> GetFriends(Long user_id);

    FriendDto AddFriend(Long first_user_id, Long second_user_id);

    FriendDto AcceptFriend(Long friend_id);

    FriendDto DeniedFriend(Long friend_id);

    FriendDto DeleteFriend(Long friend_id);
}
