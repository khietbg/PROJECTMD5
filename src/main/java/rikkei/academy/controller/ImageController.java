package rikkei.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rikkei.academy.service.imageService.ImageServiceIpm;


import java.io.IOException;


@RestController
@RequestMapping("/api/images")
public class ImageController {
    @Autowired
    private ImageServiceIpm imageServiceIpm;

    @PostMapping("/uploadFile")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM','USER')")
    public ResponseEntity<?> uploadFile(@RequestParam("image") MultipartFile file) throws IOException {
        String uploadFile = imageServiceIpm.uploadFile(file);
        return ResponseEntity.status(HttpStatus.OK).body(uploadFile);
    }

    @GetMapping("/{fileName}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PM','USER')")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
        byte[] imageData= imageServiceIpm.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

}
