package rikkei.academy.service.commentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.model.Comments;
import rikkei.academy.model.Post;
import rikkei.academy.repository.ICommentRepository;

import java.util.List;

@Service
public class CommentServiceIpm implements ICommentService{
    @Autowired
    private ICommentRepository commentRepository;
    @Override
    public Iterable<Comments> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comments findById(Long id) {
        return commentRepository.findById(id).get();
    }

    @Override
    public Comments save(Comments comments) {
        return commentRepository.save(comments);
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }


    @Override
    public List<Comments> getCommentsByPostAndStatusTrue(Post post) {
        return commentRepository.getCommentsByPostAndStatusTrue(post);
    }
}
