package be.kdg.ip.repositories.api;

import be.kdg.ip.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findMessagesByChatroom(String chatroom);

    Message findMessageByMessageId(int messageId);
}
