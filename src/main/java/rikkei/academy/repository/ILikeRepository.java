package rikkei.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rikkei.academy.model.Like;
import rikkei.academy.model.Post;
import rikkei.academy.model.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface ILikeRepository extends JpaRepository<Like,Long> {
    boolean existsByPostAndUsers(Post post, Users users);
    Optional<Like> findByPostAndUsers(Post post,Users users);

    @Query(value = "SELECT count(*) FROM likes l WHERE l.status = true and l.post_id = ?1",nativeQuery = true)
    Long  findCount(Long id);
    List<Like> findAllByPostAndStatusTrue(Post post);

}
