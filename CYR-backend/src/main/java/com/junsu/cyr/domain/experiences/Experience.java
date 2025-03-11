package com.junsu.cyr.domain.experiences;

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
@Table(name = "experience")
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "experience_id", nullable = false)
    private Integer experienceId;

    @Column(name = "experience_name", nullable = false)
    private String experienceName;

    @Column(name = "experience_amount", nullable = false)
    private Integer experienceAmount;
}
