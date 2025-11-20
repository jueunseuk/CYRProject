package com.junsu.cyr.service.statistic;

import com.junsu.cyr.domain.statistics.Statistic;
import com.junsu.cyr.repository.StatisticRepository;
import com.junsu.cyr.service.comment.CommentService;
import com.junsu.cyr.service.gallery.GalleryService;
import com.junsu.cyr.service.glass.GlassService;
import com.junsu.cyr.service.post.PostService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StatisticService {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final GalleryService galleryService;
    private final GlassService glassService;
    private final StatisticRepository statisticRepository;

    public Statistic getStatistic() {
        return statisticRepository.findFirstByOrderByCreatedAtDesc();
    }

    @Transactional
    public void createStatistic() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime now = LocalDateTime.now();

        Long totalMember = userService.getUserCnt();
        Long todayMember = userService.getUserCnt(start, now);

        Long totalPost = postService.getPostCnt();
        Long todayPost = postService.getPostCnt(start, now);

        Long totalComment = commentService.getCommentCnt();
        Long todayComment = commentService.getCommentCnt(start, now);

        Long totalGallery = galleryService.getGalleryImageCnt();
        Long todayGallery = galleryService.getGalleryImageCnt(start, now);

        Long totalConvert = glassService.getGlassConvertCnt();
        Long todayConvert = glassService.getGlassConvertCnt(start, now);

        Statistic statistic = Statistic.builder()
                .totalMember(totalMember)
                .todayMember(todayMember)
                .totalPost(totalPost)
                .todayPost(todayPost)
                .totalComment(totalComment)
                .todayComment(todayComment)
                .totalGallery(totalGallery)
                .todayGallery(todayGallery)
                .totalConvert(totalConvert)
                .todayConvert(todayConvert)
                .build();

        statisticRepository.save(statistic);
    }
}
