package be.kdg.ip.Chat.ChatControllers;

import be.kdg.ip.Chat.History.ChatHistoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
public class MessageController {

    @Autowired
    private ChatHistoryDao chatHistoryDao;

    /*
     * This MessageMapping annotated method will be handled by
     * SimpAnnotationMethodMessageHandler and after that the Message will be
     * forwarded to Broker channel to be forwarded to the client via WebSocket
     */

    @CrossOrigin(origins = "*")
    @MessageMapping("/all")
    @SendTo("/topic/all")
    public Map<String, String> post(@Payload Map<String, String> message) {
        message.put("timestamp", Long.toString(System.currentTimeMillis()));
        chatHistoryDao.save(message);
        return message;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/history")
    public List<Map<String, String>> getChatHistory() {
        return chatHistoryDao.get();
    }
}
