package be.kdg.ip.Chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin(origins = "*")
@RestController
public class WebSocketController {
    private final SimpMessagingTemplate template;

    @Autowired
    public WebSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/send/message/{chatroom}")
    public void onRecieveMessage(@PathVariable("chatroom") String chatroom, String message) {
        System.out.println("========== ========== ========== ========== ========== ========== ==========");
        System.out.println("/send/message/{chatroom}");
        System.out.println("id: " + chatroom);
        System.out.println("message: " + message);
        System.out.println("========== ========== ========== ========== ========== ========== ==========");
        this.template.convertAndSend("/chat/" + chatroom,
                new SimpleDateFormat("HH:mm:ss").format(new Date()) + "- " + message);
    }
}
