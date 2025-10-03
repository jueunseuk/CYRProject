package com.junsu.cyr.repository.projection;

import java.time.LocalDate;

public interface DailyMaxProjection {
    LocalDate getDate();
    Long getAfter();
}
