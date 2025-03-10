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

    @Column(name = "board_name", nullable = false)
    private String boardName;

    @Column(name = "board_description", nullable = false)
    private String boardDescription;

    @Column(name = "board_permission", nullable = false)
    private Permission boardPermission;
}
