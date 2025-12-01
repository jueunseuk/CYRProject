package com.junsu.cyr.flow.chat;

import com.junsu.cyr.constant.ChatSystemMessageConstant;
import com.junsu.cyr.domain.chats.ChatRoom;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.chat.ChatRoomRequest;
import com.junsu.cyr.model.chat.ChatRoomResponse;
import com.junsu.cyr.service.chat.ChatMessageService;
import com.junsu.cyr.service.chat.ChatRoomService;
import com.junsu.cyr.service.chat.ChatRoomUserService;
import com.junsu.cyr.service.notification.usecase.ChatNotificationUseCase;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateChatRoomFlow {

    private final UserService userService;
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final ChatRoomUserService chatRoomUserService;
    private final ChatNotificationUseCase chatNotificationUseCase;

    @Transactional
    public ChatRoomResponse createChatRoom(ChatRoomRequest request, Integer userId) {
        User user = userService.getUserById(userId);

        ChatRoom chatRoom = chatRoomService.createChatRoom(request.getName() == null ? "새로운 채팅방" : request.getName(), request.getMaxMember());

        String content = String.format(ChatSystemMessageConstant.CREATE);
        chatMessageService.createSystemMessage(chatRoom, content);

        chatRoomUserService.createChatRoomUser(chatRoom, user);

        if(request.getOtherId() != null) {
            User other = userService.getUserById(request.getOtherId());
            chatRoomUserService.createChatRoomUser(chatRoom, other);

            chatNotificationUseCase.invitedChatRoom(other, user);
        }

        return new ChatRoomResponse(chatRoom);
    }
}
