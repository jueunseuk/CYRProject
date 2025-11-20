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
import java.util.Optional;

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
    public void updateFavoriteBoard(Integer boardId, Integer userId) {
        User user = userService.getUserById(userId);
        Board board = boardService.findBoardByBoardId(boardId);
        Optional<UserBoardSetting> userBoardSetting = userBoardSettingRepository.findByUserAndBoard(user, board);

        if(userBoardSetting.isPresent()) {
            userBoardSettingRepository.delete(userBoardSetting.get());
        } else {
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
