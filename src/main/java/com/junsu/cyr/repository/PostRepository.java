package com.junsu.cyr.repository;

import com.junsu.cyr.domain.posts.Locked;
import com.junsu.cyr.domain.posts.Post;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.ranking.CountRankingProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByPostId(Long postId);

    @Query("SELECT p FROM Post p WHERE p.locked = :locked AND p.board.boardId BETWEEN :start AND :end")
    Page<Post> findAllNew(Integer start, Integer end, Locked locked, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.locked = :locked AND p.board.boardId BETWEEN :startId AND :endId AND p.createdAt BETWEEN :start AND :end")
    Page<Post> findPopularPostsWithinDates(@Param("startId") Integer startId,
                                           @Param("endId") Integer endId,
                                           @Param("start") LocalDateTime start,
                                           @Param("end") LocalDateTime end,
                                           Locked locked,
                                           Pageable pageable);

    @Query("select count(p) from Post p where p.user = :user")
    Long countByUser(User user);

    Page<Post> findAllByUser(User user, Pageable pageable);

    Page<Post> findAllByUserAndLocked(User user, Locked locked, Pageable pageable);

    Long countByCreatedAtBetween(LocalDateTime start, LocalDateTime now);

    Page<Post> findAllByTitleContaining(String keyword, Pageable pageable);

    Page<Post> findAllByContentContaining(String keyword, Pageable pageable);

    @Query("select p.user.userId as userId, count(*) as count from Post as p where p.createdAt between :start and :now group by p.user order by count(*) desc")
    List<CountRankingProjection> sumAllByCreatedAtBetween(LocalDateTime start, LocalDateTime now, PageRequest of);
}
