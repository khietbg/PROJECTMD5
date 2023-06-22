package rikkei.academy.service.messageService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rikkei.academy.model.Friends;
import rikkei.academy.model.Message;
import rikkei.academy.model.Users;
import rikkei.academy.repository.IMessageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceIpm implements IMessageService{
    private final IMessageRepository messageRepository;
    @Override
    public Iterable<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Message findById(Long id) {
        return messageRepository.findById(id).get();
    }

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public void deleteById(Long id) {
        messageRepository.deleteById(id);
    }

    @Override
    public List<Message> findAllByUsersAndFriendAndStatusTrue(Users users, Users friend) {
        return messageRepository.findAllByUsersAndFriendAndStatusTrue(users,friend);
    }

    @Override
    public List<Message> findAllByUsers(Users users) {
        return messageRepository.findAllByUsers(users);
    }

    @Override
    public List<Message> findAllByUsersAndFriend(Users users, Users friend) {
        return messageRepository.findAllByUsersAndFriend(users,friend);
    }

}
