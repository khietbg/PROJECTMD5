package rikkei.academy.service.commentService;

import rikkei.academy.model.Comments;
import rikkei.academy.model.Post;
import rikkei.academy.service.IGenericService;

import java.util.List;

public interface ICommentService extends IGenericService<Comments,Long> {
    List<Comments> getCommentsByPostAndStatusTrue(Post post);
}
