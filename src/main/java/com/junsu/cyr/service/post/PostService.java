package com.junsu.cyr.service.post;

import com.junsu.cyr.constant.PostSortFieldConstant;
import com.junsu.cyr.domain.boards.Board;
import com.junsu.cyr.domain.empathys.EmpathyId;
import com.junsu.cyr.domain.posts.Locked;
import com.junsu.cyr.domain.posts.Post;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.post.*;
import com.junsu.cyr.model.search.SearchConditionRequest;
import com.junsu.cyr.repository.EmpathyRepository;
import com.junsu.cyr.repository.PostRepository;
import com.junsu.cyr.response.exception.code.BoardExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.response.exception.code.PostExceptionCode;
import com.junsu.cyr.service.board.BoardService;
import com.junsu.cyr.service.user.UserService;
import com.junsu.cyr.util.PageableMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserService userService;
    private final PostRepository postRepository;
    private final BoardService boardService;
    private final EmpathyRepository empathyRepository;

    public Post getPostByPostId(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(PostExceptionCode.POST_NOT_BE_FOUND));
    }

    @Transactional
    public PostResponse getPost(Long postId, Integer userId) {
        userService.getUserById(userId);

        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new BaseException(PostExceptionCode.POST_NOT_BE_FOUND));

        Boolean alreadyEmpathy = empathyRepository.existsById(new EmpathyId(postId, userId));

        post.increaseViewCnt();

        return new PostResponse(post, alreadyEmpathy);
    }

    public Page<PostListResponse> getAllPosts(PostSearchConditionRequest condition) {
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), Sort.by(condition.getSort()).descending());

        Page<Post> posts = postRepository.findAllNew(9, 17, Locked.PUBLIC, pageable);

        return posts.map(PostListResponse::new);
    }

    public Page<PostListResponse> getPopularPosts(PostSearchConditionRequest condition) {
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), Sort.by(condition.getSort()).descending());

        Page<Post> posts = postRepository.findPopularPostsWithinDates(
                9,
                17,
                LocalDateTime.parse(condition.getStart()),
                LocalDateTime.parse(condition.getEnd()),
                Locked.PUBLIC,
                pageable
        );

        return posts.map(PostListResponse::new);
    }

    public Page<PostListResponse> getPosts(PostSearchConditionRequest condition) {
        if(condition.getBoardId() == null) {
            throw new BaseException(BoardExceptionCode.NOT_FOUND_BOARD_ID);
        }

        Board board = boardService.findBoardByBoardId(condition.getBoardId());

        Page<Post> posts = postRepository.findAllByBoard(board, PageableMaker.of(condition.getPage(), condition.getSize(), condition.getSort(), condition.getDirection()));

        return posts.map(PostListResponse::new);
    }

    public Page<PostListResponse> getPostsByUser(Integer searchId, Integer userId, PostSearchConditionRequest condition) {
        User user = userService.getUserById(searchId);

        String sortField = condition.getSort();
        if(!PostSortFieldConstant.ALLOWED_FIELDS.contains(sortField)) {
            throw new BaseException(PostExceptionCode.UNSUPPORTED_SORT_FIELD);
        }

        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), Sort.by(Sort.Direction.fromString(condition.getDirection()), sortField));

        Page<Post> posts;

        if (searchId.equals(userId)) {
            posts = postRepository.findAllByUser(user, pageable);
        } else {
            posts = postRepository.findAllByUserAndLocked(user, Locked.PUBLIC, pageable);
        }

        return posts.map(PostListResponse::new);
    }

    public Long getPostCnt() {
        return postRepository.count();
    }

    public Long getPostCnt(LocalDateTime start, LocalDateTime now) {
        return postRepository.countByCreatedAtBetween(start, now);
    }

    public Page<Post> searchByTitle(SearchConditionRequest condition) {
        Sort sort = Sort.by(Sort.Direction.fromString(condition.getDirection()), condition.getSort());
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), sort);

        return postRepository.findAllByTitleContaining(condition.getKeyword(), pageable);
    }

    public Page<Post> searchByContent(SearchConditionRequest condition) {
        Sort sort = Sort.by(Sort.Direction.fromString(condition.getDirection()), condition.getSort());
        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), sort);

        return postRepository.findAllByContentContaining(condition.getKeyword(), pageable);
    }
}