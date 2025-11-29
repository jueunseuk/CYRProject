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
public class ExitChatRoomFlow {

    private final UserService userService;
    private final ChatRoomService chatRoomService;
    private final ChatRoomUserService chatRoomUserService;
    private final ChatMessageService chatMessageService;
    private final DeleteChatRoomFlow deleteChatRoomFlow;

    @Transactional
    public void exitChatRoom(Long chatRoomId, Integer userId) {
        User user = userService.getUserById(userId);
        ChatRoom chatRoom = chatRoomService.getChatRoomByChatRoomId(chatRoomId);

        if(!chatRoomUserService.checkUserInChatRoom(user, chatRoom)) {
            throw new BaseException(ChatRoomUserExceptionCode.NOT_FOUND_CHAT_ROOM_USER);
        }

        chatRoomUserService.deleteChatRoomUser(userId, chatRoomId);

        chatRoom.decreaseMemberCount();

        if(chatRoom.getMemberCount() == 0) {
            deleteChatRoomFlow.deleteChatRoom(chatRoomId, userId);
            return;
        }

        String content = String.format(ChatSystemMessageConstant.EXIT, user.getNickname());
        chatMessageService.createSystemMessage(chatRoom, content);
    }
}
