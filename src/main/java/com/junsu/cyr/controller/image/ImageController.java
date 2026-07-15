package com.junsu.cyr.controller.image;

import com.junsu.cyr.domain.images.Image;
import com.junsu.cyr.domain.images.Type;
import com.junsu.cyr.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<String> uploadChatImage(@RequestParam("image") MultipartFile file, @RequestAttribute Integer userId) {
        Image image = imageService.uploadImage(file, userId.longValue(), Type.CHAT);
        return ResponseEntity.ok(image.getUrl());
    }
}
