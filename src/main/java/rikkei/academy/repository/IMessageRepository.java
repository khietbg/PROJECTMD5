package rikkei.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rikkei.academy.model.Friends;
import rikkei.academy.model.Message;
import rikkei.academy.model.Users;

import java.util.List;

@Repository
public interface IMessageRepository extends JpaRepository<Message,Long> {
    List<Message> findAllByUsersAndFriendAndStatusTrue(Users users, Users friend);
    List<Message> findAllByUsers(Users users);
    List<Message> findAllByUsersAndFriend(Users users,Users friend);
}
