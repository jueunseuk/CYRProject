package com.junsu.cyr.flow.user.maintenance;

import com.junsu.cyr.domain.users.Status;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCleanupFlow {

    private final UserRepository userRepository;

    @Transactional
    public Integer userCleanup() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime aMonthAgo = now.minusMonths(1);

        List<User> users = userRepository.findAllByStatusAndDeletedAtBefore(Status.SECESSION, aMonthAgo);

        for(User user : users) {
            user.delete();
        }

        return users.size();
    }
}
