package rikkei.academy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.dto.request.MessageDto;
import rikkei.academy.dto.request.NewMessage;
import rikkei.academy.model.Friends;
import rikkei.academy.model.Message;
import rikkei.academy.model.Users;
import rikkei.academy.service.friendService.IFriendService;
import rikkei.academy.service.messageService.IMessageService;
import rikkei.academy.service.userService.IUserService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/message")
public class MessageController {
    private final IMessageService messageService;
    private final IUserService userService;
    private final IFriendService friendService;
    @PostMapping("/findMessageByUserAndFriend")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM','USER')")
    public ResponseEntity<List<Message>> findMessageByUserAndFriend(@RequestBody MessageDto messageDto){
       Users users = userService.findById(messageDto.getUserId());
       Users friends = userService.findById(messageDto.getFriendId());
        List<Message> messageList = messageService.findAllByUsersAndFriendAndStatusTrue(users,friends);
        List<Message> messageList1 = messageService.findAllByUsersAndFriendAndStatusTrue(friends,users);
        messageList.addAll(messageList1);
        Collections.sort(messageList, new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                if (o1.getId()>o2.getId()){
                    return 1;
                }else if (o1.getId()<o2.getId()){
                    return -1;
                }
                return 0;
            }
        });
        return new ResponseEntity<>(messageList, HttpStatus.OK);
    }
    @PostMapping("/createMessage")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM','USER')")
    public ResponseEntity<?> createMessage(@RequestBody NewMessage newMessage){
        List<Friends> list = friendService.getFriendsByUsers(userService.findById(newMessage.getUserId()));
        Users users = userService.findById(newMessage.getFriendId());
        for (Friends f:list) {
            if (f.getFriend().getId()==users.getId()){

                messageService.save(Message.builder()
                        .users(userService.findById(newMessage.getUserId()))
                        .friend(users)
                        .content(newMessage.getContent())
                        .created(LocalDate.now())
                        .status(true)
                        .build());
                return new ResponseEntity<>(HttpStatus.OK);
        }

        }
                messageService.save(Message.builder()
                        .users(userService.findById(newMessage.getUserId()))
                        .content(newMessage.getContent())
                        .friend(users)
                        .created(LocalDate.now())
                        .build());
            return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("findMessageByUser/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM','USER')")
    public ResponseEntity<?> findMessageByUser(@PathVariable Long id){
       List<Message> messageList = messageService.findAllByUsers(userService.findById(id));
       return new ResponseEntity<>(messageList,HttpStatus.OK);
    }
    @GetMapping("deleteMessage/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM','USER')")
    public ResponseEntity<?> deleteMessage(@PathVariable Long id){
        messageService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
