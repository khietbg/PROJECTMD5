package rikkei.academy.service.likeService;

import rikkei.academy.model.Like;
import rikkei.academy.model.Post;
import rikkei.academy.model.Users;
import rikkei.academy.service.IGenericService;

import java.util.List;
import java.util.Optional;

public interface ILikeService extends IGenericService<Like,Long> {
    boolean existsByPostAndUsers(Post post, Users users);
    Optional<Like> findByPostAndUsers(Post post, Users users);

    Long findCount(Long id);
    List<Like> findAllByPostAndStatusTrue(Post post);
}
