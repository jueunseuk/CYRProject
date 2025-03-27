package com.junsu.cyr.service.post;

import com.junsu.cyr.domain.posts.Post;
import com.junsu.cyr.model.post.PostListResponse;
import com.junsu.cyr.model.post.PostResponse;
import com.junsu.cyr.model.post.PostSearchConditionRequest;
import com.junsu.cyr.repository.PostRepository;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.PostExceptionCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final EntityManager entityManager;

    public PostResponse getPost(Long postId) {
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new BaseException(PostExceptionCode.POST_NOT_BE_FOUND));
        return new PostResponse(post);
    }

    public Page<PostListResponse> getAllPosts(PostSearchConditionRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(request.getSort()).descending());

        Page<Post> posts = postRepository.findAllNew(9, 17, pageable);

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
            query.setParameter("boardId", condition.getBoardId());
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

    private static String getCondition(PostSearchConditionRequest condition) {
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

}
