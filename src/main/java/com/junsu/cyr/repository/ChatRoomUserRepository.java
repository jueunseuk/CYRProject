package com.junsu.cyr.repository;

import com.junsu.cyr.domain.chats.ChatRoom;
import com.junsu.cyr.domain.chats.ChatRoomUser;
import com.junsu.cyr.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {

    boolean existsByUser(User user);

    Boolean existsByUserAndChatRoom(User user, ChatRoom chatRoom);

    void deleteAllByChatRoom(ChatRoom chatRoom);

    void deleteByUserAndChatRoom(User user, ChatRoom chatRoom);

    Long countAllByChatRoom(ChatRoom chatRoom);

    @Query("select cru.chatRoom from ChatRoomUser cru where cru.user = :user order by cru.chatRoom.lastMessagedAt desc")
    List<ChatRoom> findALlByUser(User user);

    @Query("select cru.chatRoom.chatRoomId from ChatRoomUser cru where cru.user = :user order by cru.chatRoom.lastMessagedAt desc")
    List<Long> findAllIdByUser(User user);
}
