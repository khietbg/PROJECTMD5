package rikkei.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rikkei.academy.model.Comments;
import rikkei.academy.model.Post;

import java.awt.*;
import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository<Comments,Long> {


    List<Comments> getCommentsByPostAndStatusTrue(Post post);
}
