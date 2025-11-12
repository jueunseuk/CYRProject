package com.junsu.cyr.service.user;

import com.junsu.cyr.domain.boards.Board;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.domain.users.UserBoardSetting;
import com.junsu.cyr.repository.UserBoardSettingRepository;
import com.junsu.cyr.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserBoardSettingService {

    private final UserBoardSettingRepository userBoardSettingRepository;
    private final UserService userService;
    private final BoardService boardService;

    public List<Integer> getUserFavoriteBoard(Integer userId) {
        User user = userService.getUserById(userId);
        List<UserBoardSetting> userBoardSettings = userBoardSettingRepository.findAllByUser(user);
        return userBoardSettings.stream().map(ubs -> ubs.getBoard().getBoardId()).toList();
    }

    @Transactional
    public void updateFavoriteBoard(List<Integer> boardIds, Integer userId) {
        User user = userService.getUserById(userId);
        List<UserBoardSetting> userBoardSettings = userBoardSettingRepository.findAllByUser(user);
        userBoardSettingRepository.deleteAll(userBoardSettings);
        for(Integer boardId : boardIds) {
            Board board = boardService.findBoardByBoardId(boardId);
            createUserBoardSetting(user, board);
        }
    }

    @Transactional
    public void createUserBoardSetting(User user, Board board) {
        UserBoardSetting userBoardSetting = UserBoardSetting.builder()
                .user(user)
                .board(board)
                .build();
        userBoardSettingRepository.save(userBoardSetting);
    }
}
