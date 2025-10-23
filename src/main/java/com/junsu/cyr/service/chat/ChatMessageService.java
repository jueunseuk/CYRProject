package com.junsu.cyr.service.chat;

import com.junsu.cyr.domain.chats.ChatMessage;
import com.junsu.cyr.domain.chats.ChatRoom;
import com.junsu.cyr.domain.chats.Type;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.repository.ChatMessageRepository;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.ChatMessageExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessage getChatRoomByChatRoomId(Long chatMessageId) {
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
    public void deleteByChatMessageId(Long chatMessageId) {
        chatMessageRepository.deleteById(chatMessageId);
    }

    @Transactional
    public void deleteAllByChatRoom(ChatRoom chatRoom) {
        chatMessageRepository.deleteAllByChatRoom(chatRoom);
    }
}
