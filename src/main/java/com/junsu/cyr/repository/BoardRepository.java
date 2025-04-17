package com.junsu.cyr.repository;

import com.junsu.cyr.domain.boards.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    @Query("select b from Board as b where b.boardId = :boardId")
    Board findBoardByBoardId(Integer boardId);
}
