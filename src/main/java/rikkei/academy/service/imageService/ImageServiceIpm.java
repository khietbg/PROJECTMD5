package rikkei.academy.service.imageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rikkei.academy.model.Images;
import rikkei.academy.repository.IImageRepository;
import rikkei.academy.util.ImageUtils;


import java.io.IOException;
import java.util.Optional;

@Service
public class ImageServiceIpm {
    @Autowired
    private IImageRepository imageRepository;

    public String uploadFile(MultipartFile file) throws IOException {
        Images images = imageRepository.save(Images.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .build());
        if (images != null) {
            return "upload successfully!!" + file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadImage(String fileName) {
        Optional<Images> img = imageRepository.findByName(fileName);
        byte[] image = ImageUtils.decompressImage(img.get().getImageData());
        return image;
    }

    public Optional<Images> findImageById(Long id) {
        return imageRepository.findImagesById(id);
    }
}



