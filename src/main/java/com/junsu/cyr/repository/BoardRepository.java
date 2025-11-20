package com.junsu.cyr.repository;

import com.junsu.cyr.domain.boards.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
}
