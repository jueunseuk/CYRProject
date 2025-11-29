package com.junsu.cyr.service.chat;

import com.junsu.cyr.domain.chats.ChatRoom;
import com.junsu.cyr.domain.users.User;
import com.junsu.cyr.model.chat.ChatRoomResponse;
import com.junsu.cyr.repository.ChatRoomRepository;
import com.junsu.cyr.response.exception.http.BaseException;
import com.junsu.cyr.response.exception.code.ChatRoomExceptionCode;
import com.junsu.cyr.response.exception.code.ChatRoomUserExceptionCode;
import com.junsu.cyr.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserService chatRoomUserService;
    private final UserService userService;

    public ChatRoom getChatRoomByChatRoomId(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new BaseException(ChatRoomExceptionCode.NOT_FOUND_CHAT_ROOM));
    }

    public ChatRoomResponse getChatRoomInfo(Long chatRoomId, Integer userId) {
        User user = userService.getUserById(userId);
        ChatRoom chatRoom = getChatRoomByChatRoomId(chatRoomId);

        if(!chatRoomUserService.checkUserInChatRoom(user, chatRoom)) {
            throw new BaseException(ChatRoomUserExceptionCode.NOT_FOUND_CHAT_ROOM_USER);
        }

        return new ChatRoomResponse(chatRoom);
    }

    public List<ChatRoomResponse> getChatRoomList(Integer userId) {
        User user = userService.getUserById(userId);

        List<ChatRoom> chatRoomUsers = chatRoomUserService.getChatRoomByUser(user);

        return chatRoomUsers.stream().map(ChatRoomResponse::new).toList();
    }

    public List<ChatRoomResponse> getOhterChatRoomList(Integer userId) {
        User user = userService.getUserById(userId);

        List<Long> chatRoomIds = chatRoomUserService.getChatRoomIdByUser(user);
        List<ChatRoom> otherChatRooms = chatRoomRepository.findAllByChatRoomIdNotIn(chatRoomIds);

        return otherChatRooms.stream().map(ChatRoomResponse::new).toList();
    }

    @Transactional
    public ChatRoom createChatRoom(String name, Long maxMember) {
        ChatRoom chatRoom = ChatRoom.builder()
                .name(name)
                .memberCount(1L)
                .maxMember(maxMember)
                .lastMessagedAt(LocalDateTime.now())
                .build();

        return chatRoomRepository.save(chatRoom);
    }
}
