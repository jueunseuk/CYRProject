package com.junsu.cyr.flow.chat;

import com.junsu.cyr.constant.ChatSystemMessageConstant;
import com.junsu.cyr.domain.chats.ChatRoom;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.response.exception.code.ChatRoomUserExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.chat.ChatMessageService;
import com.junsu.cyr.service.chat.ChatRoomService;
import com.junsu.cyr.service.chat.ChatRoomUserService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JoinChatRoomFlow {

    private final UserService userService;
    private final ChatRoomService chatRoomService;
    private final ChatRoomUserService chatRoomUserService;
    private final ChatMessageService chatMessageService;

    @Transactional
    public void joinChatRoom(Long chatRoomId, Integer userId) {
        User user = userService.getUserById(userId);
        ChatRoom chatRoom = chatRoomService.getChatRoomByChatRoomId(chatRoomId);

        if(chatRoomUserService.checkUserInChatRoom(user, chatRoom)) {
            throw new BaseException(ChatRoomUserExceptionCode.ALREADY_JOIN_CHAT_ROOM);
        }

        if(chatRoom.getMaxMember() < chatRoom.getMemberCount() + 1) {
            throw new BaseException(ChatRoomUserExceptionCode.CHAT_ROOM_CAPACITY_EXCEEDED);
        }

        String content = String.format(ChatSystemMessageConstant.JOIN, user.getNickname());
        chatMessageService.createSystemMessage(chatRoom, content);
        chatRoomUserService.createChatRoomUser(chatRoom, user);
    }
}
