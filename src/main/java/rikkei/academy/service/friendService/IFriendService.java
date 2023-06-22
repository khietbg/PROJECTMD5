package rikkei.academy.service.friendService;

import rikkei.academy.model.Friends;
import rikkei.academy.model.Users;
import rikkei.academy.service.IGenericService;

import java.util.List;

public interface IFriendService extends IGenericService<Friends,Long> {
    List<Friends> getFriendsByUsers(Users users);
    Friends findFriendsByUsersAndFriend(Users users,Users friend);

}
