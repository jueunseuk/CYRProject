package com.junsu.cyr.domain.boards;

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
@Table(name = "board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id", nullable = false)
    private Integer boardId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "korean", nullable = false)
    private String korean;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "read_permission", nullable = false)
    private Permission readPermission;

    @Enumerated(EnumType.STRING)
    @Column(name = "write_permission", nullable = false)
    private Permission writePermission;
}
