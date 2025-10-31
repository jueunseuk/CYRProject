package com.junsu.cyr.model.search;

import com.junsu.cyr.domain.users.Role;
import com.junsu.cyr.domain.users.User;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class UserSearchResponse extends SearchResponse {
    public Role role;
    public String highlightNickname;
    public String profileUrl;
    public String introduction;
    public Integer temperature;

    public UserSearchResponse(User user, String keyword) {
        super(user.getUserId(), user.getNickname(), user.getCreatedAt(), "user");
        this.highlightNickname = extractHighlight(user.getNickname(), keyword);
        this.role = user.getRole();
        this.profileUrl = user.getProfileUrl();
        this.introduction = user.getIntroduction();
        this.temperature = user.getTemperature();
    }

    private String extractHighlight(String text, String keyword) {
        if (text == null || keyword == null || !text.contains(keyword)) return text.substring(0, Math.min(text.length(), 50));

        return text.replace(keyword, "<mark>" + keyword + "</mark>");
    }
}
