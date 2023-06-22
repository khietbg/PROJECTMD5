package rikkei.academy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.model.Comments;
import rikkei.academy.service.commentService.ICommentService;
import rikkei.academy.service.postService.IPostService;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final ICommentService commentService;
    private final IPostService iPostService;
    @GetMapping("/findAllByPost/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM','USER')")
    public ResponseEntity<List<Comments>> findAllComment(@PathVariable Long id){
        List<Comments> commentsList =commentService.getCommentsByPostAndStatusTrue(iPostService.findById(id));
        return new ResponseEntity<>(commentsList, HttpStatus.OK);
    }
    @PostMapping("/createComment")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM','USER')")
    public ResponseEntity<?> createComment(@RequestBody Comments comments){
        comments.setCommentDate(LocalDate.now());
        comments.setStatus(true);
        commentService.save(comments);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/updateComment/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM','USER')")
    public ResponseEntity<?> updateComment(@PathVariable Long id,@RequestBody Comments comments){
       Comments comments1 =  commentService.findById(id);
       comments1.setContent(comments.getContent());
       commentService.save(comments1);
       return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/deleteComment/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM','USER')")
    public ResponseEntity<?> deleteComment(@PathVariable Long id){
        commentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/changeStatus/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> panComment(@PathVariable Long id){
       Comments comments = commentService.findById(id);
        comments.setStatus(false);
        commentService.save(comments);
        return new ResponseEntity<>("Change Status success! ",HttpStatus.ACCEPTED);
    }
    @GetMapping("/addLike/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM','USER')")
    public ResponseEntity<?> addLike(@PathVariable Long id){
        Comments comments = commentService.findById(id);
        comments.setLike(comments.getLike()+1);
        commentService.save(comments);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
