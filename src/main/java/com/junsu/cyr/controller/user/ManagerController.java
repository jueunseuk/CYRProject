package com.junsu.cyr.controller.user;

import com.junsu.cyr.domain.users.Role;
import com.junsu.cyr.domain.users.Status;
import com.junsu.cyr.flow.user.asset.*;
import com.junsu.cyr.flow.user.profile.AllocateUserStatusFlow;
import com.junsu.cyr.flow.user.profile.GrantUserWarningFlow;
import com.junsu.cyr.flow.user.profile.RevokeUserWarningFlow;
import com.junsu.cyr.model.user.UserConditionRequest;
import com.junsu.cyr.model.user.UserManagementResponse;
import com.junsu.cyr.service.comment.CommentService;
import com.junsu.cyr.service.gallery.GalleryService;
import com.junsu.cyr.service.post.PostService;
import com.junsu.cyr.service.user.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {

    private final ManagerService managerService;
    private final GalleryService galleryService;
    private final PostService postService;
    private final CommentService commentService;
    private final GrantUserWarningFlow grantUserWarningFlow;
    private final RevokeUserWarningFlow revokeUserWarningFlow;
    private final GrantUserGlassFlow grantUserGlassFlow;
    private final RevokeUserGlassFlow revokeUserGlassFlow;
    private final GrantUserSandFlow grantUserSandFlow;
    private final RevokeUserSandFlow revokeUserSandFlow;
    private final GrantUserTemperatureFlow grantUserTemperatureFlow;
    private final RevokeUserTemperatureFlow revokeUserTemperatureFlow;
    private final AllocateUserStatusFlow allocateUserStatusFlow;

    @GetMapping("/members")
    public ResponseEntity<List<UserManagementResponse>> getMembers(@ModelAttribute UserConditionRequest condition, @RequestAttribute Integer userId) {
        List<UserManagementResponse> userManagerResponses = managerService.getMemberList(condition, Role.MEMBER, userId);
        return ResponseEntity.ok(userManagerResponses);
    }

    @PatchMapping("/user/{memberId}/warn")
    public ResponseEntity<String> updateWarnCnt(@PathVariable Integer memberId, @RequestParam Integer amount, @RequestAttribute Integer userId) {
        if(amount > 0) {
            grantUserWarningFlow.grantUserWarning(memberId, amount, userId);
        } else {
            revokeUserWarningFlow.revokeUserWarning(memberId, amount, userId);
        }
        return ResponseEntity.ok("success to increase warn cnt");
    }

    @PatchMapping("/user/{memberId}/glass")
    public ResponseEntity<String> updateGlass(@PathVariable Integer memberId, @RequestParam Integer amount, @RequestAttribute Integer userId) {
        if(amount > 0) {
            grantUserGlassFlow.grantUserGlass(memberId, amount, userId);
        } else {
            revokeUserGlassFlow.revokeUserGlass(memberId, amount, userId);
        }
        return ResponseEntity.ok("success to increase glass cnt");
    }

    @PatchMapping("/user/{memberId}/sand")
    public ResponseEntity<String> updateSand(@PathVariable Integer memberId, @RequestParam Integer amount, @RequestAttribute Integer userId) {
        if(amount > 0) {
            grantUserSandFlow.grantUserSand(memberId, amount, userId);
        } else {
            revokeUserSandFlow.revokeUserSand(memberId, amount, userId);
        }
        return ResponseEntity.ok("success to increase sand cnt");
    }

    @PatchMapping("/user/{memberId}/temperature")
    public ResponseEntity<String> updateTemperature(@PathVariable Integer memberId, @RequestParam Integer amount, @RequestAttribute Integer userId) {
        if(amount > 0) {
            grantUserTemperatureFlow.grantUserTemperature(memberId, amount, userId);
        } else {
            revokeUserTemperatureFlow.revokeUserTemperature(memberId, amount, userId);
        }
        return ResponseEntity.ok("success to increase temperature cnt");
    }

    @PatchMapping("/user/{memberId}/status")
    public ResponseEntity<String> updateStatus(@PathVariable Integer memberId, @RequestParam Status status, @RequestAttribute Integer userId) {
        allocateUserStatusFlow.allocateUserStatus(memberId, status, userId);
        return ResponseEntity.ok("success to update status");
    }

    @DeleteMapping("/gallery/{galleryId}")
    public ResponseEntity<String> deleteGalleryForce(@PathVariable Long galleryId, @RequestAttribute Integer userId) {
        galleryService.deleteGalleryForce(galleryId, userId);
        return ResponseEntity.ok("success to delete gallery");
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<String> deletePostForce(@PathVariable Long postId, @RequestAttribute Integer userId) {
        postService.deletePostForce(postId, userId);
        return ResponseEntity.ok("success to delete post");
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<String> deleteCommentForce(@PathVariable Long commentId, @RequestAttribute Integer userId) {
        commentService.deleteCommentForce(commentId, userId);
        return ResponseEntity.ok("success to delete comment");
    }
}
