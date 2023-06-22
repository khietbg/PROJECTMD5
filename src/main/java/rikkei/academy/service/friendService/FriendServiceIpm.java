package rikkei.academy.service.friendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.model.Friends;
import rikkei.academy.model.Users;
import rikkei.academy.repository.IFriendRepository;

import java.util.List;

@Service
public class FriendServiceIpm implements IFriendService{
    @Autowired
    private IFriendRepository friendRepository;
    @Override
    public Iterable<Friends> findAll() {
        return friendRepository.findAll();
    }

    @Override
    public Friends findById(Long id) {
        return friendRepository.findById(id).get();
    }

    @Override
    public Friends save(Friends friends) {
        return friendRepository.save(friends);
    }

    @Override
    public void deleteById(Long id) {
friendRepository.deleteById(id);
    }

    @Override
    public List<Friends> getFriendsByUsers(Users users) {
        return friendRepository.getFriendsByUsers(users);
    }

    @Override
    public Friends findFriendsByUsersAndFriend(Users users, Users friend) {
        return friendRepository.findFriendsByUsersAndFriend(users,friend);
    }

}
