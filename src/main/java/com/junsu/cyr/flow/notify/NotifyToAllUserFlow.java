package com.junsu.cyr.flow.notify;

import com.junsu.cyr.domain.notification.Type;
import com.junsu.cyr.domain.users.Status;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.global.annotation.ManagerOnly;
import com.junsu.cyr.model.notification.NotificationAllRequest;
import com.junsu.cyr.repository.UserRepository;
import com.junsu.cyr.response.exception.code.NotificationExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.notification.NotificationService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotifyToAllUserFlow {

    private final UserService userService;
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    @ManagerOnly
    @Transactional
    public void notifyToAllUser(NotificationAllRequest request, Integer userId) {
        userService.getUserById(userId);

        if(request.getMessage() == null) {
            throw new BaseException(NotificationExceptionCode.TOO_SHORT_MESSAGE);
        }

        List<User> userList = userRepository.findAllByStatus(Status.ACTIVE);
        for(User u : userList) {
            notificationService.createNotification(u, Type.SYSTEM, request.getMessage(), null);
        }
    }
}
