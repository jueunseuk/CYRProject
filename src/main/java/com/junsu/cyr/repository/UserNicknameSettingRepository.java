package com.junsu.cyr.repository;

import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.domain.users.UserNicknameSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNicknameSettingRepository extends JpaRepository<UserNicknameSetting, Long> {
    void deleteByUser(User user);
    UserNicknameSetting findByUser(User user);
    UserNicknameSetting getUserNicknameSettingByUser_UserId(Integer userId);
}
