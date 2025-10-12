package com.junsu.cyr.controller.gallery;

import com.junsu.cyr.model.gallery.*;
import com.junsu.cyr.service.gallery.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gallery")
public class GalleryController {

    private final GalleryService galleryService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadGallery(@ModelAttribute GalleryUploadRequest galleryUploadRequest, @RequestAttribute Integer userId) {
        galleryService.uploadGallery(galleryUploadRequest, userId);
        return ResponseEntity.ok("success to upload gallery");
    }

    @GetMapping("/random/{amount}")
    public ResponseEntity<List<GalleryImageResponse>> getRandomGallery(@PathVariable Integer amount) {
        List<GalleryImageResponse> galleryImageResponses = galleryService.getRandomImages(amount);
        return ResponseEntity.ok(galleryImageResponses);
    }

    @GetMapping("")
    public ResponseEntity<Page<GalleryImageResponse>> getAllGalleryImage(GalleryImageRequest galleryImageRequest) {
        Page<GalleryImageResponse> galleryImageResponses = galleryService.getAllGalleryImages(galleryImageRequest);
        return ResponseEntity.ok(galleryImageResponses);
    }

    @GetMapping("/{galleryId}")
    public ResponseEntity<GalleryResponse> getGalleryImage(@PathVariable Long galleryId) {
        GalleryResponse galleryResponse = galleryService.getGallery(galleryId);
        return ResponseEntity.ok(galleryResponse);
    }

    @DeleteMapping("/{galleryId}")
    public ResponseEntity<?> deleteGallery(@PathVariable Long galleryId, @RequestAttribute Integer userId) {
        galleryService.deleteGallery(galleryId, userId);
        return ResponseEntity.ok("success to delete gallery");
    }

    @PostMapping("/{galleryId}")
    public ResponseEntity<?> updateGallery(@ModelAttribute GalleryUploadRequest galleryUploadRequest, @PathVariable Long galleryId, @RequestAttribute Integer userId) {
        galleryService.updateGallery(galleryId, galleryUploadRequest, userId);
        return ResponseEntity.ok("success to update gallery");
    }

}
