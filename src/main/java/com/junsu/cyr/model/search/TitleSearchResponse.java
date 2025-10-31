package com.junsu.cyr.model.search;

import com.junsu.cyr.domain.posts.Post;
import lombok.Getter;

@Getter
public class TitleSearchResponse extends SearchResponse {
    public Integer boardId;
    public String korean;
    public Long postId;
    public String title;
    public String content;
    public Long viewCnt;
    public Long empathyCnt;
    public Long commentCnt;

    public TitleSearchResponse(Post post, String keyword) {
        super(post.getUser().getUserId(), post.getUser().getNickname(), post.getCreatedAt(), "post");
        this.boardId = post.getBoard().getBoardId();
        this.korean = post.getBoard().getKorean();
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = extractHighlight(post.getContent(), keyword);
        this.viewCnt = post.getViewCnt();
        this.empathyCnt = post.getEmpathyCnt();
        this.commentCnt = post.getCommentCnt();
    }

    private String extractHighlight(String text, String keyword) {
        if (text == null || keyword == null || !text.contains(keyword)) return text.substring(0, Math.min(text.length(), 50));

        int idx = text.indexOf(keyword);
        int start = Math.max(idx - 20, 0);
        int end = Math.min(idx + keyword.length() + 20, text.length());

        String snippet = text.substring(start, end);
        return snippet.replace(keyword, "<mark>" + keyword + "</mark>");
    }
}
