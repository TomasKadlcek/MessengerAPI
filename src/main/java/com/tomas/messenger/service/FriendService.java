package com.tomas.messenger.service;

import com.tomas.messenger.shared.FriendDTO;

import java.util.List;

public interface FriendService {

    FriendDTO addFriend(FriendDTO friendDTO, String userId);

    List<FriendDTO> getAllFriends(String id);
}
