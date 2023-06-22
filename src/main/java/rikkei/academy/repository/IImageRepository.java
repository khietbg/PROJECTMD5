package rikkei.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rikkei.academy.model.Images;

import java.util.Optional;

@Repository
public interface IImageRepository extends JpaRepository<Images,Long> {
    Optional<Images> findByName(String fileName);
    Optional<Images> findImagesById(Long id);
}
