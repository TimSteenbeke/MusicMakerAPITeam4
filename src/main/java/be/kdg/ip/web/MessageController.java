package be.kdg.ip.web;

import be.kdg.ip.domain.*;
import be.kdg.ip.services.api.MessageService;
import be.kdg.ip.web.resources.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/message")
public class MessageController {
    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService= messageService;
    }


    //1 message opvragen messageId
    @GetMapping("/{messageId}")
    //ToDo: Authorization fix: user get
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<MessageResource> findMessageByMessageId(@PathVariable int messageId) {
        Message message= messageService.findMessage(messageId);
        MessageResource messageResource = new MessageResource();
        messageResource.setChatroom(message.getChatroom());
        messageResource.setMessage(message.getMessage());
        return new ResponseEntity<>(messageResource, HttpStatus.OK);
    }

    //Alle messages opvragen
    @GetMapping
    @CrossOrigin(origins = "*")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<MessageGetResource> findAll() {
        List<Message> messages= messageService.getMessages();

        MessageGetResource messageGetResource= new MessageGetResource();
        messageGetResource.setMessages(new ArrayList<>());
        for (Message message : messages){
            MessageResource messageResource = createMessageResource(message);
                    messageGetResource.getMessages().add(messageResource);
        }

        return new ResponseEntity<>(messageGetResource, HttpStatus.OK);
    }

    //Alle messages voor chatroom opvragen
    @GetMapping("/chatroom/{chatroom}")
    @CrossOrigin(origins = "*")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<MessageGetResource> findChatroomMessages(@PathVariable("chatroom") String chatroom) {
        List<Message> messages= messageService.getMessagesForChatroom(chatroom);
        MessageGetResource messageGetResource= new MessageGetResource();
        messageGetResource.setMessages(new ArrayList<>());
        for (Message message : messages){
            MessageResource messageResource = createMessageResource(message);
            messageGetResource.getMessages().add(messageResource);
        }
        return new ResponseEntity<>(messageGetResource, HttpStatus.OK);
    }

    //Een message verwijderen
    @DeleteMapping("/{messageId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<User> deleteUser(@PathVariable("messageId") Integer messageId) {
        messageService.deleteMessage(messageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('TEACHER') or hasAuthority('STUDENT')")
    public ResponseEntity<Message> createMessage(@Valid @RequestBody MessageResource messageResource) {
        Message message = new Message();
        message.setMessage(messageResource.getMessage());
        message.setChatroom(messageResource.getChatroom());
        Message out = messageService.addMessage(message);
        return new ResponseEntity<>(out, HttpStatus.OK);
    }

    private MessageResource createMessageResource(Message message){
        MessageResource messageResource = new MessageResource();
        messageResource.setChatroom(message.getChatroom());
        messageResource.setMessage(message.getMessage());
        return messageResource;
    }
}