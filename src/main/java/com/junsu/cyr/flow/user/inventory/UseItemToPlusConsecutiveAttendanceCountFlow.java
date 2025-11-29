package com.junsu.cyr.flow.user.inventory;

import com.junsu.cyr.domain.attendances.Attendance;
import com.junsu.cyr.domain.attendances.AttendanceId;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.userInventory.ItemUseRequest;
import com.junsu.cyr.model.userInventory.ItemUseResult;
import com.junsu.cyr.repository.AttendanceRepository;
import com.junsu.cyr.response.exception.code.AttendanceExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.user.useitem.base.UseConsumableItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service("ATTENDANCE_PLUS_TICKET")
@RequiredArgsConstructor
public class UseItemToPlusConsecutiveAttendanceCountFlow implements UseConsumableItem {

    private final AttendanceRepository attendanceRepository;

    @Override
    public ItemUseResult use(User user, ItemUseRequest request) {
        AttendanceId attendanceId = new AttendanceId(user.getUserId(), LocalDate.now());

        Optional<Attendance> attendance = attendanceRepository.findByAttendanceId(attendanceId);
        if(attendance.isEmpty()) {
            throw new BaseException(AttendanceExceptionCode.NOT_YET_IN_ATTENDANCE);
        }

        user.increaseConsecutiveAttendanceCnt();

        return ItemUseResult.builder()
                .success(true)
                .message("success to increase consecutive attendance cnt")
                .data(user.getConsecutiveAttendanceCnt())
                .type("ATTENDANCE_PLUS_TICKET")
                .build();
    }
}
