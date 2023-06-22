package rikkei.academy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.model.Post;
import rikkei.academy.service.commentService.ICommentService;
import rikkei.academy.service.imageService.ImageServiceIpm;
import rikkei.academy.service.postService.PostServiceIpm;
import rikkei.academy.service.userService.IUserService;

import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostServiceIpm postServiceIpm;

    @GetMapping("/findAll")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM','USER')")
    public ResponseEntity<?> findAll(Pageable pageable) {
        return new ResponseEntity<>(postServiceIpm.findAllByStatusTrue(pageable), HttpStatus.OK);
    }
    @PostMapping("/createPost")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM','USER')")
    public ResponseEntity<?> createPost(@RequestBody Post  post){
        post.setPostDate(LocalDate.now());
        post.setStatus(true);
        Post post1 = postServiceIpm.save(post);
        return new ResponseEntity<>(post1,HttpStatus.ACCEPTED);
    }
    @PutMapping("/updatePost/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM','USER')")
    public ResponseEntity<?> updatePost(@RequestBody Post postUpdate,@PathVariable Long id){
        Post post = postServiceIpm.findById(id);
       post.setTitle(postUpdate.getTitle());
       post.setContent(post.getContent());
       post.setImages(postUpdate.getImages());
       postServiceIpm.save(post);
        return new ResponseEntity<>("update success!",HttpStatus.ACCEPTED);
    }
    @GetMapping("/deletePost/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        Post post = postServiceIpm.findById(id);
        post.setStatus(!post.isStatus());
        postServiceIpm.save(post);
        return new ResponseEntity<>("Change Status success!",HttpStatus.ACCEPTED);
    }

}
