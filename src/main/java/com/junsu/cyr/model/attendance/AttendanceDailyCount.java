package com.junsu.cyr.model.attendance;

import java.time.LocalDate;

public interface AttendanceDailyCount {
    LocalDate getDate();
    Integer getCount();
}
