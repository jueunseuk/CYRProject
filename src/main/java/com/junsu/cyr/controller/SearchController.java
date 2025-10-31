package com.junsu.cyr.controller;

import com.junsu.cyr.model.search.SearchConditionRequest;
import com.junsu.cyr.model.search.SearchResponse;
import com.junsu.cyr.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<Page<? extends SearchResponse>> getType(@ModelAttribute SearchConditionRequest condition) {
        Page<? extends SearchResponse> response = searchService.search(condition);
        return ResponseEntity.ok(response);
    }
}
