package be.kdg.ip.Chat;

import be.kdg.ip.domain.Message;
import be.kdg.ip.services.api.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin(origins = "*")
@RestController
public class WebSocketController {
    private final SimpMessagingTemplate template;
    private MessageService messageService;

    @Autowired
    public WebSocketController(SimpMessagingTemplate template, MessageService messageService) {
        this.template = template;
        this.messageService = messageService;
    }

    @MessageMapping("/send/message/{chatroom}")
    public void onRecieveMessage(@PathVariable("chatroom") String chatroom, String message) {
        Message out = messageService.addMessage(new Message(message,chatroom));
        this.template.convertAndSend("/chat/" + chatroom,
                new SimpleDateFormat("HH:mm:ss").format(new Date()) + "- " + message);
        System.out.println("---------- ---------- ---------- ---------- message Added ---------- ---------- ---------- ----------");
        System.out.println(out);
        System.out.println("---------- ---------- ---------- ---------- message Added ---------- ---------- ---------- ----------");
    }
}
