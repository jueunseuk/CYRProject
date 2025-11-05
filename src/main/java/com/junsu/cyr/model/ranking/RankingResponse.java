package com.junsu.cyr.model.ranking;

import com.junsu.cyr.domain.rankings.*;
import com.junsu.cyr.domain.users.User;
import lombok.Data;

@Data
public class RankingResponse {
    private Integer rankingId;
    private Long score;
    private Long priority;
    private Integer userId;
    private String nickname;
    private String profileUrl;
    private Integer rankingCategoryId;
    private Type type;
    private Period period;
    private String name;
    private String korean;
    private String description;
    private Refresh refresh;

    public RankingResponse(Ranking ranking) {
        this.rankingId = ranking.getRankingId();
        this.score = ranking.getScore();
        this.priority = ranking.getPriority();

        User user = ranking.getUser();
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.profileUrl = user.getProfileUrl();

        RankingCategory rankingCategory = ranking.getRankingCategory();
        this.rankingCategoryId = rankingCategory.getRankingCategoryId();
        this.type = rankingCategory.getType();
        this.period = rankingCategory.getPeriod();
        this.name = rankingCategory.getName();
        this.korean = rankingCategory.getKorean();
        this.description = rankingCategory.getDescription();
        this.refresh = rankingCategory.getRefresh();
    }
}
