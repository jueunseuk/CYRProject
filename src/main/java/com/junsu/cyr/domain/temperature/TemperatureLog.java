package com.junsu.cyr.domain.temperature;

import com.junsu.cyr.domain.globals.BaseTime;
import com.junsu.cyr.domain.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "temperature_log")
public class TemperatureLog extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "temperature_log_id", nullable = false)
    private Integer temperatureId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "temperature")
    private Temperature temperature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "after", nullable = false)
    private Integer after;
}
