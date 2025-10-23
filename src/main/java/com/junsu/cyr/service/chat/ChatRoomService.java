package com.junsu.cyr.service.chat;

import com.junsu.cyr.domain.chats.ChatRoom;
import com.junsu.cyr.repository.ChatRoomRepository;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.ChatRoomExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageService chatMessageService;
    private final ChatRoomUserService chatRoomUserService;

    public ChatRoom getChatRoomByChatRoomId(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new BaseException(ChatRoomExceptionCode.NOT_FOUND_CHAT_ROOM));
    }

    @Transactional
    public ChatRoom createChatRoom(String name) {
        return ChatRoom.builder()
                .name(name == null ? "새로운 채팅" : name)
                .build();
    }

    @Transactional
    public void deleteChatRoom(ChatRoom chatRoom) {
        chatMessageService.deleteAllByChatRoom(chatRoom);

        chatRoomUserService.deleteAllByChatRoom(chatRoom);

        chatRoomRepository.delete(chatRoom);
    }
}
