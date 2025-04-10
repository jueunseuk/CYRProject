package com.junsu.cyr.repository;

import com.junsu.cyr.domain.attendances.Attendance;
import com.junsu.cyr.domain.attendances.AttendanceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, AttendanceId> {
    Optional<Attendance> findByAttendanceId(AttendanceId attendanceId);

    @Query("select a from Attendance as a where a.createdAt between :start and :end order by a.createdAt desc")
    List<Attendance> findTodayAttendance(LocalDateTime start, LocalDateTime end);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.attendanceId.attendedAt BETWEEN :start AND :end GROUP BY a.attendanceId.attendedAt ORDER BY a.attendanceId.attendedAt ASC")
    List<Integer> findWeeklyAttendanceByDay(LocalDate start, LocalDate end);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.attendanceId.attendedAt BETWEEN :start AND :end GROUP BY DAY(a.attendanceId.attendedAt) ORDER BY DAY(a.attendanceId.attendedAt)")
    Integer findMonthlyAttendance(LocalDate start, LocalDate end);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.attendanceId.attendedAt BETWEEN :start AND :end")
    Integer findAttendanceCntBetween(LocalDate start, LocalDate end);

}
