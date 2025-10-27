package com.junsu.cyr.repository;

import com.junsu.cyr.domain.chats.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    ChatRoom findByChatRoomId(Long chatRoomId);

    List<ChatRoom> findAllByChatRoomIdNotIn(List<Long> chatRooms);
}
