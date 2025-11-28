package com.junsu.cyr.domain.comments;

import com.junsu.cyr.domain.globals.BaseTime;
import com.junsu.cyr.domain.posts.Post;
import com.junsu.cyr.domain.shop.ShopItem;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.response.exception.code.CommentExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
public class Comment extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "fixed", nullable = false)
    private Boolean fixed;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "locked")
    private Boolean locked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emoticon")
    private ShopItem emoticon;

    public void updateContent(String content, Boolean locked) {
        if(content == null || content.length() < 5 || locked == null) {
            throw new BaseException(CommentExceptionCode.INVALID_VALUE_INJECTION);
        }

        this.content = content;
        this.locked = locked;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateFixed(Boolean fixed) {
        if(fixed == null) {
            throw new BaseException(CommentExceptionCode.INVALID_VALUE_INJECTION);
        }

        this.fixed = fixed;
    }

    public void updateEmoticon(ShopItem shopItem) {
        if(shopItem == null || shopItem.getShopCategory().getShopCategoryId() != 1) {
            throw new BaseException(CommentExceptionCode.INVALID_EMOTICON_ID);
        }
        this.emoticon = shopItem;
    }
}
