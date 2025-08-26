package com.junsu.cyr.controller.admin;

import com.junsu.cyr.model.admin.AdminResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/{adminId}")
    public ResponseEntity<AdminResponse> admin(@PathVariable int adminId) {
        AdminResponse adminResponse = new AdminResponse();
        return ResponseEntity.ok(adminResponse);
    }
}
