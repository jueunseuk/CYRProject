package com.junsu.cyr.service.search;

import com.junsu.cyr.domain.gallery.GalleryImage;
import com.junsu.cyr.model.search.*;
import com.junsu.cyr.response.exception.code.SearchExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.comment.CommentService;
import com.junsu.cyr.service.gallery.GalleryService;
import com.junsu.cyr.service.post.PostService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final GalleryService galleryService;

    public Page<? extends SearchResponse> search(SearchConditionRequest condition) {
        if (condition.getKeyword() == null || condition.getKeyword().isEmpty()) {
            throw new BaseException(SearchExceptionCode.NOT_FOUND_KEYWORD);
        }

        return switch (condition.getType()) {
            case "user" -> userService.searchByNickname(condition)
                    .map(user -> new UserSearchResponse(user, condition.getKeyword()));

            case "title" -> postService.searchByTitle(condition)
                    .map(post -> new TitleSearchResponse(post, condition.getKeyword()));

            case "content" -> postService.searchByContent(condition)
                    .map(post -> new ContentSearchResponse(post, condition.getKeyword()));

            case "comment" -> commentService.searchByContent(condition)
                    .map(comment -> new CommentSearchResponse(comment, condition.getKeyword()));

            case "gallery" -> galleryService.searchByTitle(condition)
                    .map(gallery -> {
                        List<GalleryImage> galleryImages = galleryService.getGalleryImageByGallery(gallery);
                        return new GallerySearchResponse(gallery, galleryImages, condition.getKeyword());
                    });
            default -> throw new BaseException(SearchExceptionCode.INVALID_SEARCH_TYPE);
        };
    }
}
