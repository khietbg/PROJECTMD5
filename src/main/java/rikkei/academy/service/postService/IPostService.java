package rikkei.academy.service.postService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rikkei.academy.model.Post;
import rikkei.academy.service.IGenericService;

import java.util.List;

public interface IPostService extends IGenericService<Post,Long> {
    Page<Post> findAllByStatusTrue(Pageable pageable);

}
