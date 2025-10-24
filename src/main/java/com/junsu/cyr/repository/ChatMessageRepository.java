package com.junsu.cyr.repository;

import com.junsu.cyr.domain.chats.ChatMessage;
import com.junsu.cyr.domain.chats.ChatRoom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    void deleteAllByChatRoom(ChatRoom chatRoom);

    List<ChatMessage> findALlByChatRoom(ChatRoom chatRoom, Pageable pageable);
}
