package com.junsu.cyr.repository;

import com.junsu.cyr.domain.attendances.Attendance;
import com.junsu.cyr.domain.attendances.AttendanceId;
import com.junsu.cyr.model.attendance.AttendanceDailyCount;
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

    @Query("select a from Attendance a where a.attendanceId.attendedAt between :start and :end order by a.attendanceId.attendedAt desc")
    List<Attendance> findTodayAttendance(LocalDateTime start, LocalDateTime end);

    @Query("SELECT a.attendanceId.attendedAt AS date, COUNT(a) AS count " +
            "FROM Attendance a " +
            "WHERE a.attendanceId.attendedAt BETWEEN :start AND :end " +
            "GROUP BY a.attendanceId.attendedAt " +
            "ORDER BY a.attendanceId.attendedAt ASC")
    List<AttendanceDailyCount> findWeeklyAttendanceByDay(LocalDate start, LocalDate end);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.attendanceId.attendedAt BETWEEN :start AND :end")
    Integer findAttendanceCnt(LocalDate start, LocalDate end);

}
