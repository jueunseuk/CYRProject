package com.junsu.cyr.flow.user.inventory;

import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.userInventory.ItemUseRequest;
import com.junsu.cyr.model.userInventory.ItemUseResult;
import com.junsu.cyr.service.attendance.AttendanceService;
import com.junsu.cyr.service.user.useitem.base.UseConsumableItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("ATTENDANCE_PLUS_TICKET")
@RequiredArgsConstructor
public class AttendancePlusUseCase implements UseConsumableItem {

    private final AttendanceService attendanceService;

    @Override
    public ItemUseResult use(User user, ItemUseRequest request) {
        Integer result = attendanceService.increaseConsecutiveAttendanceToForce(user);
        return ItemUseResult.builder()
                .success(true)
                .message("success to increase consecutive attendance cnt")
                .data(result)
                .type("ATTENDANCE_PLUS_TICKET")
                .build();
    }
}
