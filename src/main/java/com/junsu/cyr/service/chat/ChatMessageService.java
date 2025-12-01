package com.junsu.cyr.service.chat;

import com.junsu.cyr.domain.chats.ChatMessage;
import com.junsu.cyr.domain.chats.ChatRoom;
import com.junsu.cyr.domain.chats.Type;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.chat.ChatMessageConditionRequest;
import com.junsu.cyr.model.chat.ChatMessageResponse;
import com.junsu.cyr.repository.ChatMessageRepository;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.response.exception.code.ChatMessageExceptionCode;
import com.junsu.cyr.response.exception.code.ChatRoomUserExceptionCode;
import com.junsu.cyr.service.user.UserNicknameSettingService;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserService userService;
    private final ChatRoomService chatRoomService;
    private final ChatRoomUserService chatRoomUserService;
    private final UserNicknameSettingService userNicknameSettingService;

    public ChatMessage getChatMessageByChatRoomId(Long chatMessageId) {
        return chatMessageRepository.findById(chatMessageId)
                .orElseThrow(() -> new BaseException(ChatMessageExceptionCode.NOT_FOUND_CHAT_ROOM));
    }

    @Transactional
    public ChatMessage createUserMessage(ChatRoom chatRoom, User user, String content, Type type) {
        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .user(user)
                .content(content)
                .type(type)
                .build();

        return chatMessageRepository.save(chatMessage);
    }

    @Transactional
    public ChatMessage createSystemMessage(ChatRoom chatRoom, String content) {
        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .user(null)
                .content(content)
                .type(Type.SYSTEM)
                .build();

        return chatMessageRepository.save(chatMessage);
    }

    @Transactional
    public void deleteAllByChatRoom(Long chatRoomId) {
        chatMessageRepository.deleteAllByChatRoom_ChatRoomId(chatRoomId);
    }

    public List<ChatMessageResponse> getMessageList(ChatMessageConditionRequest request, Long chatRoomId, Integer userId) {
        User user = userService.getUserById(userId);
        ChatRoom chatRoom = chatRoomService.getChatRoomByChatRoomId(chatRoomId);

        if(!chatRoomUserService.checkUserInChatRoom(user, chatRoom)) {
            throw new BaseException(ChatRoomUserExceptionCode.NOT_FOUND_CHAT_ROOM_USER);
        }

        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSort());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);

        List<ChatMessage> chatMessages = chatMessageRepository.findALlByChatRoom(chatRoom, pageable);

        return chatMessages.stream().map(chatMessage -> new ChatMessageResponse(chatMessage, userNicknameSettingService.getUserNicknameColor(chatMessage.getUser()))).toList();
    }
}
