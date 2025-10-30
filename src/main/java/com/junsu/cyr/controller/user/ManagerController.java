package com.junsu.cyr.controller.user;

import com.junsu.cyr.domain.users.Role;
import com.junsu.cyr.domain.users.Status;
import com.junsu.cyr.model.user.UserConditionRequest;
import com.junsu.cyr.model.user.UserManagementResponse;
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

    @GetMapping("/members")
    public ResponseEntity<List<UserManagementResponse>> getMembers(@ModelAttribute UserConditionRequest condition, @RequestAttribute Integer userId) {
        List<UserManagementResponse> userManagerResponses = managerService.getMemberList(condition, Role.MEMBER, userId);
        return ResponseEntity.ok(userManagerResponses);
    }

    @PatchMapping("/user/{memberId}/warn")
    public ResponseEntity<String> updateWarnCnt(@PathVariable Integer memberId, @RequestParam Integer amount, @RequestAttribute Integer userId) {
        managerService.updateWarnCnt(memberId, amount, userId);
        return ResponseEntity.ok("success to increase warn cnt");
    }

    @PatchMapping("/user/{memberId}/status")
    public ResponseEntity<String> updateStatus(@PathVariable Integer memberId, @RequestParam Status status, @RequestAttribute Integer userId) {
        managerService.updateStatus(memberId, status, userId);
        return ResponseEntity.ok("success to update status");
    }
}
