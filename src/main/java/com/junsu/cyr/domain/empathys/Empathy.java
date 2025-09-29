package com.junsu.cyr.domain.empathys;

import com.junsu.cyr.domain.globals.BaseTime;
import com.junsu.cyr.domain.posts.Post;
import com.junsu.cyr.domain.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "empathy")
public class Empathy extends BaseTime {
    @EmbeddedId
    @Column(name = "empathy_id")
    private EmpathyId empathyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;
}
