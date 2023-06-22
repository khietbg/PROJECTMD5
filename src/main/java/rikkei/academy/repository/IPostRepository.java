package rikkei.academy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rikkei.academy.model.Post;

import java.util.List;

@Repository
public interface IPostRepository extends JpaRepository<Post,Long> {
    Page<Post> findAllByStatusTrue(Pageable pageable);

}
