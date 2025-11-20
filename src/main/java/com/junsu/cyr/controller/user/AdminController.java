package com.junsu.cyr.controller.user;

import com.junsu.cyr.domain.users.Role;
import com.junsu.cyr.domain.users.Status;
import com.junsu.cyr.model.user.UserManagementResponse;
import com.junsu.cyr.model.user.UserConditionRequest;
import com.junsu.cyr.service.user.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/managers")
    public ResponseEntity<List<UserManagementResponse>> getMembers(@ModelAttribute UserConditionRequest condition, @RequestAttribute Integer userId) {
        List<UserManagementResponse> userManagerResponses = adminService.getManagerList(condition, Role.MANAGER, userId);
        return ResponseEntity.ok(userManagerResponses);
    }

    @PatchMapping("/user/{memberId}/status")
    public ResponseEntity<String> updateStatus(@PathVariable Integer memberId, @RequestParam Status status, @RequestAttribute Integer userId) {
        adminService.updateStatus(memberId, status, userId);
        return ResponseEntity.ok("success to update status");
    }

    @PatchMapping("/user/{memberId}/role")
    public ResponseEntity<String> updateRole(@PathVariable Integer memberId, @RequestParam Role role, @RequestAttribute Integer userId) {
        adminService.updateRole(memberId, role, userId);
        return ResponseEntity.ok("success to update role");
    }
}
