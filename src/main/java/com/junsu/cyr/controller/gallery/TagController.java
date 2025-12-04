package com.junsu.cyr.controller.gallery;

import com.junsu.cyr.model.gallery.TagResponse;
import com.junsu.cyr.service.gallery.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gallery/tag")
public class TagController {

    private final TagService tagService;

    @GetMapping("/all")
    public ResponseEntity<List<TagResponse>> getAllTag() {
        List<TagResponse> tagResponses = tagService.getAllTags();
        return ResponseEntity.ok(tagResponses);
    }
}
