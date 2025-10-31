package com.junsu.cyr.controller.search;

import com.junsu.cyr.model.search.SearchConditionRequest;
import com.junsu.cyr.model.search.SearchResponse;
import com.junsu.cyr.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<Page<? extends SearchResponse>> getType(@ModelAttribute SearchConditionRequest condition, @RequestAttribute Integer userId) {
        Page<? extends SearchResponse> response = searchService.search(condition, userId);
        return ResponseEntity.ok(response);
    }
}
