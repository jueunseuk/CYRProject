package com.junsu.cyr.domain.calendar;

import com.junsu.cyr.domain.globals.BaseTime;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.calendar.CalendarEditRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "calendar")
public class Calendar extends BaseTime implements Comparable<Calendar> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_id", nullable = false)
    private Long calendarId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Column(name = "title", nullable = false, length = 1000)
    private String title;

    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "link1")
    private String link1;

    @Column(name = "link2")
    private String link2;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Override
    public int compareTo(Calendar o) {
        return this.getDate().compareTo(o.getDate());
    }

    public void updateCalendar(CalendarEditRequest newData, String imageUrl) {
        this.title = newData.getTitle();
        this.description = newData.getDescription();
        this.location = newData.getLocation();
        this.date = newData.getDate();
        this.link1 = newData.getLink1();
        this.link2 = newData.getLink2();
        this.imageUrl = imageUrl;
        updatedAt = LocalDateTime.now();
    }
}
