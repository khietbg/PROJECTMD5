package rikkei.academy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.model.Friends;
import rikkei.academy.model.Message;
import rikkei.academy.model.Users;
import rikkei.academy.service.friendService.IFriendService;
import rikkei.academy.service.messageService.IMessageService;
import rikkei.academy.service.userService.IUserService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friends")
public class FriendController {
    private final IFriendService friendService;
    private final IUserService userService;
    private final IMessageService messageService;

    @GetMapping("/getAllFriend/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM','USER')")
    public ResponseEntity<?> getAllFriend(@PathVariable Long id) {
        List<Friends> friendsList = friendService.getFriendsByUsers(userService.findById(id));
        return new ResponseEntity<>(friendsList, HttpStatus.OK);
    }

    @PostMapping("/CreateFriend")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM','USER')")
    public ResponseEntity<?> createFriend(@RequestBody Friends friends) {
        Users users = userService.findById(friends.getUsers().getId());
        friends.setCreatedDate(LocalDate.now());
        friends.setStatus(false);
        friendService.save(friends);
        users.getFriendsList().add(friends);
        userService.save(users);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/confirm/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM','USER')")
    public ResponseEntity<?> confirm(@PathVariable Long id) {
        Friends friends = friendService.findById(id);
        friends.setStatus(true);
        friendService.save(friends);
        Friends friends1 = Friends.builder()
                .users(friends.getFriend())
                .friend(friends.getUsers())
                .createdDate(LocalDate.now())
                .status(true)
                .build();
        friends.getFriend().getFriendsList().add(friends1);
        friendService.save(friends1);
        userService.save(friends.getFriend());
        userService.save(friends.getUsers());
        Users users = userService.findById(friends.getUsers().getId());
        Users friend = userService.findById(friends.getFriend().getId());
        List<Message> list = messageService.findAllByUsersAndFriend(users, friend);
        if (!list.isEmpty()) {
            for (Message m : list) {
                m.setStatus(true);
                messageService.save(m);
            }
        }
        List<Message> list1 = messageService.findAllByUsersAndFriend(friend,users);
        if (!list1.isEmpty()) {
            for (Message m : list) {
                m.setStatus(true);
                messageService.save(m);
            }
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/unFriend/{id}")
    public ResponseEntity<?> unFriend(@PathVariable Long id){
        Friends friends = friendService.findById(id);
        Friends friends1 = friendService.findFriendsByUsersAndFriend(friends.getFriend(),friends.getUsers());
        friendService.deleteById(friends.getId());
        friendService.deleteById(friends1.getId());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
