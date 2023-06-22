package rikkei.academy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.model.Like;
import rikkei.academy.model.Post;
import rikkei.academy.model.Users;
import rikkei.academy.service.likeService.ILikeService;
import rikkei.academy.service.postService.IPostService;
import rikkei.academy.service.userService.IUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {
    private final ILikeService likeService;
    private final IPostService postService;
    private final IUserService userService;
    @GetMapping("/getLikeByPost/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM','USER')")
    public ResponseEntity<?> getLikeByPost(@PathVariable Long id){
        Long likes = likeService.findCount(id);
        return new ResponseEntity<>(likes, HttpStatus.OK);
    }
    @PostMapping("/createLike")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM','USER')")
    public ResponseEntity<?> createLike(@RequestBody Like like){
        Users users = userService.findById(like.getUsers().getId());
        Post post = postService.findById(like.getPost().getId());
       Optional<Like> like1 =  likeService.findByPostAndUsers(post,users);
       if (like1.isPresent()){
           Like like2 = like1.get();
           like2.setStatus(!like2.isStatus());
           likeService.save(like2);
       }else {
           like.setStatus(true);
           likeService.save(like);
       }
       return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @GetMapping("/findUserLikedByPost/{id}")
    public ResponseEntity<?> findUserLikedByPost(@PathVariable Long id){
        Post post = postService.findById(id);
        List<Like> likes =likeService.findAllByPostAndStatusTrue(post);
        List<Users> usersList = new ArrayList<>();
        for (Like l:likes) {
            usersList.add(l.getUsers());
        }
        return new ResponseEntity<>(usersList,HttpStatus.ACCEPTED);
    }
}
