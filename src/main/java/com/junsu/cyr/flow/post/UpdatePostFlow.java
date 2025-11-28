package com.junsu.cyr.flow.post;

import com.junsu.cyr.domain.boards.Board;
import com.junsu.cyr.domain.posts.Post;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.post.PostUploadRequest;
import com.junsu.cyr.model.post.PostUploadResponse;
import com.junsu.cyr.response.exception.code.PostExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.board.BoardService;
import com.junsu.cyr.service.post.PostService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatePostFlow {

    private final UserService userService;
    private final BoardService boardService;
    private final PostService postService;

    public PostUploadResponse updatePost(PostUploadRequest request, Long postId, Integer userId) {
        User user = userService.getUserById(userId);
        Post post = postService.getPostByPostId(postId);

        if(post.getUser() != user) {
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
}
