package com.junsu.cyr.controller.image;

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
    public ResponseEntity<String> uploadChatImage(@RequestParam("image") MultipartFile image, @RequestAttribute Integer userId) {
        String imageUrl = imageService.uploadImage(image, Type.CHAT, userId);
        return ResponseEntity.ok(imageUrl);
    }
}
