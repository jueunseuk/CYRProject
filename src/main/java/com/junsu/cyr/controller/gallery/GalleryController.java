package com.junsu.cyr.controller.gallery;

import com.junsu.cyr.flow.gallery.CreateGalleryFlow;
import com.junsu.cyr.flow.gallery.DeleteGalleryFlow;
import com.junsu.cyr.flow.gallery.UpdateGalleryFlow;
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
    private final CreateGalleryFlow createGalleryFlow;
    private final DeleteGalleryFlow deleteGalleryFlow;
    private final UpdateGalleryFlow updateGalleryFlow;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadGallery(@ModelAttribute GalleryUploadRequest galleryUploadRequest, @RequestAttribute Integer userId) {
        createGalleryFlow.createGallery(galleryUploadRequest, userId);
        return ResponseEntity.ok("success to upload gallery");
    }

    @GetMapping("/random/{amount}")
    public ResponseEntity<List<GalleryImageResponse>> getRandomGallery(@PathVariable Integer amount) {
        List<GalleryImageResponse> galleryImageResponses = galleryService.getRandomImages(amount);
        return ResponseEntity.ok(galleryImageResponses);
    }

    @GetMapping("")
    public ResponseEntity<Page<GalleryImageResponse>> getAllGalleryImage(GalleryImageRequest request) {
        Page<GalleryImageResponse> galleryImageResponses = galleryService.getAllGalleryImages(request);
        return ResponseEntity.ok(galleryImageResponses);
    }

    @GetMapping("/{galleryId}")
    public ResponseEntity<GalleryResponse> getGalleryImage(@PathVariable Long galleryId) {
        GalleryResponse galleryResponse = galleryService.getGallery(galleryId);
        return ResponseEntity.ok(galleryResponse);
    }

    @DeleteMapping("/{galleryId}")
    public ResponseEntity<?> deleteGallery(@PathVariable Long galleryId, @RequestAttribute Integer userId) {
        deleteGalleryFlow.deleteGallery(galleryId, userId);
        return ResponseEntity.ok("success to delete gallery");
    }

    @PostMapping("/{galleryId}")
    public ResponseEntity<?> updateGallery(@ModelAttribute GalleryUploadRequest galleryUploadRequest, @PathVariable Long galleryId, @RequestAttribute Integer userId) {
        updateGalleryFlow.updateGallery(galleryId, galleryUploadRequest, userId);
        return ResponseEntity.ok("success to update gallery");
    }
}
