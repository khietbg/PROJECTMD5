package rikkei.academy.service.likeService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rikkei.academy.model.Like;
import rikkei.academy.model.Post;
import rikkei.academy.model.Users;
import rikkei.academy.repository.ILikeRepository;

import java.util.List;
import java.util.Optional;

@Service

@RequiredArgsConstructor
public class LikeServiceIpm implements ILikeService{
    private final ILikeRepository likeRepository;

    @Override
    public Iterable<Like> findAll() {
        return likeRepository.findAll();
    }

    @Override
    public Like findById(Long id) {
        return likeRepository.findById(id).get();
    }

    @Override
    public Like save(Like like) {
        return likeRepository.save(like);
    }

    @Override
    public void deleteById(Long id) {
        likeRepository.deleteById(id);
    }

    @Override
    public boolean existsByPostAndUsers(Post post, Users users) {
        return likeRepository.existsByPostAndUsers(post,users);
    }

    @Override
    public Optional<Like> findByPostAndUsers(Post post, Users users) {
        return likeRepository.findByPostAndUsers(post,users);
    }

    @Override
    public Long findCount(Long id) {
        return likeRepository.findCount(id);
    }

    @Override
    public List<Like> findAllByPostAndStatusTrue(Post post) {
        return likeRepository.findAllByPostAndStatusTrue(post);
    }


}
