package com.junsu.cyr.domain.sand;

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
@Table(name = "sand")
public class Sand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sand_id")
    private Integer sandId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "description")
    private String description;
}
