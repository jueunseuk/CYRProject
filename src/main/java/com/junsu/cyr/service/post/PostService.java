package com.junsu.cyr.service.post;

import com.junsu.cyr.constant.PostSortFieldConstant;
import com.junsu.cyr.domain.achievements.Achievement;
import com.junsu.cyr.domain.achievements.Scope;
import com.junsu.cyr.domain.achievements.Type;
import com.junsu.cyr.domain.boards.Board;
import com.junsu.cyr.domain.comments.Comment;
import com.junsu.cyr.domain.empathys.Empathy;
import com.junsu.cyr.domain.empathys.EmpathyId;
import com.junsu.cyr.domain.posts.Locked;
import com.junsu.cyr.domain.posts.Post;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.post.*;
import com.junsu.cyr.model.search.SearchConditionRequest;
import com.junsu.cyr.repository.CommentRepository;
import com.junsu.cyr.repository.EmpathyRepository;
import com.junsu.cyr.repository.PostRepository;
import com.junsu.cyr.response.exception.code.UserExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.response.exception.code.PostExceptionCode;
import com.junsu.cyr.service.achievement.AchievementProcessor;
import com.junsu.cyr.service.achievement.AchievementService;
import com.junsu.cyr.service.board.BoardService;
import com.junsu.cyr.service.comment.CommentService;
import com.junsu.cyr.service.user.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserService userService;
    private final PostRepository postRepository;
    private final BoardService boardService;
    private final EntityManager entityManager;
    private final EmpathyRepository empathyRepository;
    private final CommentRepository commentRepository;
    private final AchievementProcessor achievementProcessor;

    private Post getPostByPostId(Long postId) {
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
        String jpql = getCondition(condition);

        Pageable pageable = PageRequest.of(condition.getPage(), condition.getSize(), Sort.by(condition.getSort()).descending());

        TypedQuery<Post> query = entityManager.createQuery(jpql, Post.class);

        if(condition.getBoardId() != null) {
            query.setParameter("boardId", condition.getBoardId());
        }

        if(condition.getTitle() != null && !condition.getTitle().isEmpty()) {
            query.setParameter("title", "%"+condition.getTitle()+"%");
        }

        if(condition.getUserNickname() != null && !condition.getUserNickname().isEmpty()) {
            query.setParameter("nickname", condition.getUserNickname()+"%");
        }

        if(condition.getStart() != null && condition.getEnd() != null) {
            query.setParameter("start", condition.getStart());
            query.setParameter("end", condition.getEnd());
        }

        query.setFirstResult((condition.getPage()) * condition.getSize());
        query.setMaxResults(condition.getSize());

        List<Post> resultList = query.getResultList();

        String countJpql = jpql.replaceFirst("SELECT p", "SELECT COUNT(p)");
        TypedQuery<Long> countQuery = entityManager.createQuery(countJpql, Long.class);

        if(condition.getBoardId() != null) {
            countQuery.setParameter("boardId", condition.getBoardId());
        }

        if(condition.getTitle() != null && !condition.getTitle().isEmpty()) {
            countQuery.setParameter("title", "%"+condition.getTitle()+"%");
        }

        if(condition.getUserNickname() != null && !condition.getUserNickname().isEmpty()) {
            countQuery.setParameter("nickname", condition.getUserNickname()+"%");
        }

        if(condition.getStart() != null && condition.getEnd() != null) {
            countQuery.setParameter("start", condition.getStart());
            countQuery.setParameter("end", condition.getEnd());
        }

        Long totalCount = countQuery.getSingleResult();

        List<PostListResponse> postListResponses = resultList.stream()
                .map(PostListResponse::new)
                .toList();

        return new PageImpl<>(postListResponses, pageable, totalCount);
    }

    public String getCondition(PostSearchConditionRequest condition) {
        StringBuilder jpql = new StringBuilder("SELECT p FROM Post p WHERE 1=1");

        if (condition.getBoardId() != null) {
            jpql.append(" AND p.board.boardId = :boardId");
        }

        if(condition.getTitle() != null && !condition.getTitle().isEmpty()) {
            jpql.append(" AND p.title LIKE :title");
        }

        if(condition.getUserNickname() != null && !condition.getUserNickname().isEmpty()) {
            jpql.append(" AND p.userId.nickname LIKE :nickname");
        }

        if(condition.getStart() != null && condition.getEnd() != null) {
            jpql.append(" AND p.createdAt BETWEEN :start AND :end");
        }

        jpql.append(" ORDER BY p.")
                .append(condition.getSort())
                .append(" ")
                .append(condition.getDirection());

        return jpql.toString();
    }

    @Transactional
    public PostUploadResponse uploadPost(PostUploadRequest request, Integer userId) {
        User user = userService.getUserById(userId);

        Board board = boardService.findBoardByBoardId(Integer.parseInt(request.getBoardId()));

        if(request.getContent() == null){
            throw new BaseException(PostExceptionCode.CONTENT_IS_EMPTY);
        }

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .board(board)
                .user(user)
                .viewCnt(1L)
                .commentCnt(0L)
                .empathyCnt(0L)
                .locked(request.getLocked())
                .build();

        postRepository.save(post);
        user.increasePostCnt();

        achievementProcessor.achievementFlow(user, Type.POST, Scope.TOTAL, user.getPostCnt());
        Long todayPostCnt = postRepository.countByCreatedAtBetween(LocalDate.now().atStartOfDay(), LocalDateTime.now());
        achievementProcessor.achievementFlow(user, Type.POST, Scope.DAILY, todayPostCnt);

        switch(board.getBoardId()) {
            case 9 -> userService.addSand(user, 1);
            case 10 -> userService.addSand(user, 2);
            case 11 -> userService.addSand(user, 3);
            case 12 -> userService.addSand(user, 4);
            case 13 -> userService.addSand(user, 5);
            case 14 -> userService.addSand(user, 6);
            case 15 -> userService.addSand(user, 7);
            case 16 -> userService.addSand(user, 8);
            case 17 -> userService.addSand(user, 9);
            default -> userService.addSand(user, 10);
        }
        userService.addExperience(user, 1);

        return new PostUploadResponse(post.getBoard(), post.getPostId());
    }

    @Transactional
    public void deletePosts(Long postId, Integer userId) {
        User user = userService.getUserById(userId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(PostExceptionCode.POST_NOT_BE_FOUND));

        if(post.getUser().getUserId() != userId) {
            throw new BaseException(PostExceptionCode.DO_NOT_HAVE_PERMISSION);
        }

        postRepository.delete(post);
        user.decreasePostCnt();
    }

    @Transactional
    public PostUploadResponse updatePosts(PostUploadRequest request, Long postId, Integer userId) {
        userService.getUserById(userId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(PostExceptionCode.POST_NOT_BE_FOUND));

        if(post.getUser().getUserId() != userId) {
            throw new BaseException(PostExceptionCode.DO_NOT_HAVE_PERMISSION);
        }

        if(request.getContent() == null || request.getContent().isEmpty()){
            throw new BaseException(PostExceptionCode.CONTENT_IS_EMPTY);
        }

        Board board = boardService.findBoardByBoardId(Integer.parseInt(request.getBoardId()));

        post.update(request.getTitle(),
                request.getContent(),
                board,
                request.getLocked());

        return new PostUploadResponse(post.getBoard(), post.getPostId());
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

    @Transactional
    public void deletePostForce(Long postId, Integer userId) {
        User user = userService.getUserById(userId);

        if(!userService.isLeastManager(user)) {
            throw new BaseException(UserExceptionCode.REQUIRES_AT_LEAST_MANAGER);
        }

        Post post = getPostByPostId(postId);

        List<Comment> comments = commentRepository.findByPost(post);
        List<Empathy> empathyList = empathyRepository.findAllByPost(post);

        commentRepository.deleteAll(comments);
        empathyRepository.deleteAll(empathyList);
        postRepository.delete(post);
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