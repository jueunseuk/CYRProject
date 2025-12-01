package com.junsu.cyr.service.chat;

import com.junsu.cyr.domain.chats.ChatRoom;
import com.junsu.cyr.domain.chats.ChatRoomUser;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.repository.ChatRoomUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomUserService {

    private final ChatRoomUserRepository chatRoomUserRepository;

    public Boolean checkUserInChatRoom(User user, ChatRoom chatRoom) {
        return chatRoomUserRepository.existsByUserAndChatRoom(user, chatRoom);
    }

    @Transactional
    public void deleteAllUserByChatRoom(Long chatRoomId) {
        chatRoomUserRepository.deleteAllByChatRoom_ChatRoomId(chatRoomId);
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
    public void deleteChatRoomUser(Integer userId, Long chatRoomId) {
        chatRoomUserRepository.deleteByUser_UserIdAndChatRoom_ChatRoomId(userId, chatRoomId);
    }

    public List<ChatRoom> getChatRoomByUser(User user) {
        return chatRoomUserRepository.findALlByUser(user);
    }

    public List<Long> getChatRoomIdByUser(User user) {
        return chatRoomUserRepository.findAllIdByUser(user);
    }
}
