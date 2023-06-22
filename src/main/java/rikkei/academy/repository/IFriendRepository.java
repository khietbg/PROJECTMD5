package rikkei.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rikkei.academy.model.Friends;
import rikkei.academy.model.Users;

import java.util.List;

@Repository
public interface IFriendRepository extends JpaRepository<Friends,Long> {
    List<Friends> getFriendsByUsers(Users users);
    Friends findFriendsByUsersAndFriend(Users users,Users friend);
}
