package com.junsu.cyr.service.chat;

import com.junsu.cyr.domain.chats.ChatRoom;
import com.junsu.cyr.domain.chats.ChatRoomUser;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.repository.ChatRoomUserRepository;
import com.junsu.cyr.response.exception.BaseException;
import com.junsu.cyr.response.exception.code.ChatRoomUserExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomUserService {

    private final ChatRoomUserRepository chatRoomUserRepository;

    public ChatRoomUser getChatRoomByChatRoomId(Long chatRoomUserId) {
        return chatRoomUserRepository.findById(chatRoomUserId)
                .orElseThrow(() -> new BaseException(ChatRoomUserExceptionCode.NOT_FOUND_CHAT_ROOM_USER));
    }

    public Boolean checkUserInChatRoom(User user, ChatRoom chatRoom) {
        return chatRoomUserRepository.existsByUserAndChatRoom(user, chatRoom);
    }

    @Transactional
    public void deleteAllByChatRoom(ChatRoom chatRoom) {
        chatRoomUserRepository.deleteAllByChatRoom(chatRoom);
    }

    @Transactional
    public void createChatRoomUser(ChatRoom chatRoom, User user) {
        ChatRoomUser chatRoomUser = ChatRoomUser.builder()
                .chatRoom(chatRoom)
                .user(user)
                .build();

        chatRoomUserRepository.save(chatRoomUser);
    }

    @Transactional
    public void deleteChatRoomUser(ChatRoom chatRoom, User user) {
        chatRoomUserRepository.deleteByUserAndChatRoom(user, chatRoom);
    }
}
