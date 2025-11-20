package com.junsu.cyr.repository;

import com.junsu.cyr.domain.boards.Board;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.domain.users.UserBoardSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBoardSettingRepository extends JpaRepository<UserBoardSetting, Long> {
    List<UserBoardSetting> findAllByUser(User user);

    Optional<UserBoardSetting> findByUserAndBoard(User user, Board board);
}
