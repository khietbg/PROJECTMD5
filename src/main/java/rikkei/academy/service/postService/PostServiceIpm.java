package rikkei.academy.service.postService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rikkei.academy.model.Post;
import rikkei.academy.repository.IPostRepository;

import java.util.List;

@Service
public class PostServiceIpm implements IPostService{
    @Autowired
    private IPostRepository postRepository;
    @Override
    public Iterable<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).get();
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }


    @Override
    public Page<Post> findAllByStatusTrue(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(),5);
        return postRepository.findAllByStatusTrue(pageable);
    }
}
