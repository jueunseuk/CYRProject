package com.junsu.cyr.flow.chat;

import com.junsu.cyr.domain.chats.ChatRoom;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.repository.ChatRoomRepository;
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
public class DeleteChatRoomFlow {

    private final UserService userService;
    private final ChatRoomUserService chatRoomUserService;
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public void deleteChatRoom(Long chatRoomId, Integer userId) {
        User user = userService.getUserById(userId);
        ChatRoom chatRoom = chatRoomService.getChatRoomByChatRoomId(chatRoomId);

        if(!chatRoomUserService.checkUserInChatRoom(user, chatRoom)) {
            throw new BaseException(ChatRoomUserExceptionCode.NOT_FOUND_CHAT_ROOM_USER);
        }

        chatMessageService.deleteAllByChatRoom(chatRoomId);
        chatRoomUserService.deleteAllUserByChatRoom(chatRoomId);
        chatRoomRepository.delete(chatRoom);
    }
}
