package com.junsu.cyr.service.chat;

import com.junsu.cyr.constant.ChatSystemMessageConstant;
import com.junsu.cyr.domain.chats.ChatMessage;
import com.junsu.cyr.domain.chats.ChatRoom;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.chat.ChatMessageRequest;
import com.junsu.cyr.model.chat.ChatMessageResponse;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.response.exception.code.ChatMessageExceptionCode;
import com.junsu.cyr.response.exception.code.ChatRoomUserExceptionCode;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatWebSocketService {

    private final UserService userService;
    private final ChatRoomService chatRoomService;
    private final ChatRoomUserService chatRoomUserService;
    private final ChatMessageService chatMessageService;

    @Transactional
    public ChatMessageResponse saveAndBroadcast(Long chatRoomId, ChatMessageRequest request) {
        User user = userService.getUserById(request.getUserId());
        ChatRoom chatRoom = chatRoomService.getChatRoomByChatRoomId(chatRoomId);

        if(!chatRoomUserService.checkUserInChatRoom(user, chatRoom)) {
            throw new BaseException(ChatRoomUserExceptionCode.NOT_FOUND_CHAT_ROOM_USER);
        }

        if(request.getContent() == null || request.getContent().isEmpty()) {
            throw new BaseException(ChatMessageExceptionCode.TOO_SHORT_CONTENT);
        }

        ChatMessage chatMessage = chatMessageService.createUserMessage(chatRoom, user, request.getContent(), request.getType());

        return new ChatMessageResponse(chatMessage);
    }

    @Transactional
    public ChatMessageResponse joinRoom(Long chatRoomId, Integer userId) {
        User user = userService.getUserById(userId);
        ChatRoom chatRoom = chatRoomService.getChatRoomByChatRoomId(chatRoomId);

        if(chatRoomUserService.checkUserInChatRoom(user, chatRoom)) {
            throw new BaseException(ChatRoomUserExceptionCode.ALREADY_JOIN_CHAT_ROOM);
        }

        String content = String.format(ChatSystemMessageConstant.JOIN, user.getNickname());
        ChatMessage chatMessage = chatMessageService.createSystemMessage(chatRoom, content);

        return new ChatMessageResponse(chatMessage);
    }

    @Transactional
    public ChatMessageResponse exitRoom(Long chatRoomId, Integer userId) {
        User user = userService.getUserById(userId);
        ChatRoom chatRoom = chatRoomService.getChatRoomByChatRoomId(chatRoomId);

        if(!chatRoomUserService.checkUserInChatRoom(user, chatRoom)) {
            throw new BaseException(ChatRoomUserExceptionCode.NOT_FOUND_CHAT_ROOM_USER);
        }

        String content = String.format(ChatSystemMessageConstant.EXIT, user.getNickname());
        ChatMessage chatMessage = chatMessageService.createSystemMessage(chatRoom, content);

        return new ChatMessageResponse(chatMessage);
    }

    public ChatMessageResponse enterRoom(Long chatRoomId, ChatMessageRequest request) {
        User user = userService.getUserById(request.getUserId());
        ChatRoom chatRoom = chatRoomService.getChatRoomByChatRoomId(chatRoomId);

        if(!chatRoomUserService.checkUserInChatRoom(user, chatRoom)) {
            throw new BaseException(ChatRoomUserExceptionCode.NOT_FOUND_CHAT_ROOM_USER);
        }

        String content = String.format(ChatSystemMessageConstant.ENTER, user.getNickname());
        ChatMessage chatMessage = chatMessageService.createSystemMessage(chatRoom, content);

        return new ChatMessageResponse(chatMessage);
    }

    public ChatMessageResponse leaveRoom(Long chatRoomId, ChatMessageRequest request) {
        User user = userService.getUserById(request.getUserId());
        ChatRoom chatRoom = chatRoomService.getChatRoomByChatRoomId(chatRoomId);

        if(!chatRoomUserService.checkUserInChatRoom(user, chatRoom)) {
            throw new BaseException(ChatRoomUserExceptionCode.NOT_FOUND_CHAT_ROOM_USER);
        }

        String content = String.format(ChatSystemMessageConstant.LEAVE, user.getNickname());
        ChatMessage chatMessage = chatMessageService.createSystemMessage(chatRoom, content);

        return new ChatMessageResponse(chatMessage);
    }
}
