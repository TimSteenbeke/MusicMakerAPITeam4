package be.kdg.ip.Chat.repositories;


import be.kdg.ip.Chat.models.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {

    public List<ChatMessage> getExistingChatMessages(
           int userIdOne,int userIdTwo, Pageable pageable);
}
