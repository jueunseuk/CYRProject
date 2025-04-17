package com.junsu.cyr.service.board;

import com.junsu.cyr.domain.boards.Board;
import com.junsu.cyr.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Board findBoardByBoardId(Integer boardId) {
        return boardRepository.findBoardByBoardId(boardId);
    }
}
