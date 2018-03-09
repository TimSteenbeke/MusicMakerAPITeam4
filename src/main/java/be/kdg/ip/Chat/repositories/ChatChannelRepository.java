package be.kdg.ip.Chat.repositories;


import be.kdg.ip.Chat.models.ChatChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ChatChannelRepository extends JpaRepository<ChatChannel, String> {

  public List<ChatChannel> findExistingChannel(
      int userOneId, int userTwoId);

  public String getChannelUuid( int userIdOne, int userIdTwo);

  public ChatChannel getChannelDetails(String uuid);
}