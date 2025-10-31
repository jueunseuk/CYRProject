package com.junsu.cyr.model.search;

import com.junsu.cyr.domain.comments.Comment;
import com.junsu.cyr.domain.posts.Post;
import lombok.Getter;

@Getter
public class CommentSearchResponse extends SearchResponse {
    public Integer boardId;
    public String name;
    public String korean;
    public String content;
    public String title;
    public Long postId;
    public Long viewCnt;
    public Long empathyCnt;
    public Long commentCnt;

    public CommentSearchResponse(Comment comment, String keyword) {
        super(comment.getUser().getUserId(), comment.getUser().getNickname(), comment.getCreatedAt(), "comment");
        Post post = comment.getPost();
        this.boardId = post.getBoard().getBoardId();
        this.name = post.getBoard().getName();
        this.korean = post.getBoard().getKorean();
        this.title = post.getTitle();
        this.content = extractHighlight(comment.getContent(), keyword);
        this.postId = post.getPostId();
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
