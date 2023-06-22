package rikkei.academy.service.messageService;

import rikkei.academy.model.Friends;
import rikkei.academy.model.Message;
import rikkei.academy.model.Users;
import rikkei.academy.service.IGenericService;

import java.util.List;

public interface IMessageService extends IGenericService<Message,Long> {
    List<Message> findAllByUsersAndFriendAndStatusTrue(Users users, Users friend);
    List<Message> findAllByUsers(Users users);
    List<Message> findAllByUsersAndFriend(Users users,Users friend);
}
