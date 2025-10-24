package com.junsu.cyr.service.chat;

import com.junsu.cyr.constant.ChatSystemMessageConstant;
import com.junsu.cyr.domain.chats.ChatMessage;
import com.junsu.cyr.domain.chats.ChatRoom;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.chat.ChatRoomRequest;
import com.junsu.cyr.model.chat.ChatRoomResponse;
import com.junsu.cyr.response.exception.code.ChatRoomUserExceptionCode;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomService chatRoomService;
    private final ChatRoomUserService chatRoomUserService;
    private final ChatMessageService chatMessageService;
    private final UserService userService;

    @Transactional
    public ChatRoomResponse createChatRoom(ChatRoomRequest request, Integer userId) {
        userService.getUserById(userId);

        ChatRoom chatRoom = chatRoomService.createChatRoom(request.getName() == null ? "새로운 채팅방" : request.getName());

        String content = String.format(ChatSystemMessageConstant.CREATE);
        ChatMessage chatMessage = chatMessageService.createSystemMessage(chatRoom, content);

        chatRoom.updateLastMessage(chatMessage);

        return new ChatRoomResponse(chatRoom);
    }

    @Transactional
    public void deleteChatRoom(Long chatRoomId, Integer userId) {
        User user = userService.getUserById(userId);
        ChatRoom chatRoom = chatRoomService.getChatRoomByChatRoomId(chatRoomId);

        if(!chatRoomUserService.checkUserInChatRoom(user, chatRoom)) {
            throw new BaseException(ChatRoomUserExceptionCode.NOT_FOUND_CHAT_ROOM_USER);
        }

        chatMessageService.deleteAllByChatRoom(chatRoom);

        chatRoomUserService.deleteAllUserByChatRoom(chatRoom);

        chatRoomService.deleteChatRoom(chatRoom);
    }

    @Transactional
    public void firstJoinChatRoom(Long chatRoomId, Integer userId) {
        User user = userService.getUserById(userId);
        ChatRoom chatRoom = chatRoomService.getChatRoomByChatRoomId(chatRoomId);

        if(chatRoomUserService.checkUserInChatRoom(user, chatRoom)) {
            throw new BaseException(ChatRoomUserExceptionCode.ALREADY_JOIN_CHAT_ROOM);
        }

        chatRoomUserService.createChatRoomUser(chatRoom, user);

        chatRoom.increaseMemberCount();
    }

    @Transactional
    public void deleteUserFromChatRoom(Long chatRoomId, Integer userId) {
        User user = userService.getUserById(userId);
        ChatRoom chatRoom = chatRoomService.getChatRoomByChatRoomId(chatRoomId);

        if(!chatRoomUserService.checkUserInChatRoom(user, chatRoom)) {
            throw new BaseException(ChatRoomUserExceptionCode.NOT_FOUND_CHAT_ROOM_USER);
        }

        chatRoomUserService.deleteChatRoomUser(chatRoom, user);

        chatRoom.decreaseMemberCount();
    }
}
