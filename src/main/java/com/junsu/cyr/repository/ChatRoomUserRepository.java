package com.junsu.cyr.repository;

import com.junsu.cyr.domain.chats.ChatRoom;
import com.junsu.cyr.domain.chats.ChatRoomUser;
import com.junsu.cyr.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {

    boolean existsByUser(User user);

    Boolean existsByUserAndChatRoom(User user, ChatRoom chatRoom);

    void deleteAllByChatRoom(ChatRoom chatRoom);

    void deleteByUserAndChatRoom(User user, ChatRoom chatRoom);
}
