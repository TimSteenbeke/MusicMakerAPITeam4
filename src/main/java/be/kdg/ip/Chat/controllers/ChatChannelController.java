//package be.kdg.ip.Chat.controllers;
//
//
//import java.util.List;
//
//import be.kdg.ip.Chat.DTOs.ChatChannelInitializationDTO;
//import be.kdg.ip.Chat.DTOs.ChatMessageDTO;
//import be.kdg.ip.Chat.DTOs.EstablishedChatChannelDTO;
//import be.kdg.ip.Chat.http.JSONResponseHelper;
//import be.kdg.ip.Chat.interfaces.IChatChannelController;
//import be.kdg.ip.Chat.services.ChatService;
//import be.kdg.ip.domain.User;
//import be.kdg.ip.services.api.UserService;
//import be.kdg.ip.services.exceptions.IsSameUserException;
//import be.kdg.ip.services.exceptions.UserNotFoundException;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//@Controller
//public class ChatChannelController implements IChatChannelController {
//  @Autowired
//  private ChatService chatService;
//
//  @Autowired
//    private UserService userService;
//
//    @MessageMapping("/private.chat.{channelId}")
//    @SendTo("/topic/private.chat.{channelId}")
//    public ChatMessageDTO chatMessage(@DestinationVariable String channelId, ChatMessageDTO message)
//        throws BeansException, UserNotFoundException {
//      chatService.submitMessage(message);
//
//      return message;
//    }
//
//    @RequestMapping(value="/api/private-chat/channel", method=RequestMethod.PUT, produces="application/json", consumes="application/json")
//    public ResponseEntity<String> establishChatChannel(@RequestBody ChatChannelInitializationDTO chatChannelInitialization)
//        throws IsSameUserException, UserNotFoundException {
//      String channelUuid = chatService.establishChatSession(chatChannelInitialization);
//      User userOne = userService.findUser(chatChannelInitialization.getUserIdOne());
//      User userTwo = userService.findUser(chatChannelInitialization.getUserIdTwo());
//
//      EstablishedChatChannelDTO establishedChatChannel = new EstablishedChatChannelDTO(
//        channelUuid,
//        userOne.getUsername(),
//        userTwo.getUsername()
//      );
//
//      return JSONResponseHelper.createResponse(establishedChatChannel, HttpStatus.OK);
//    }
//
//    @RequestMapping(value="/api/private-chat/channel/{channelUuid}", method=RequestMethod.GET, produces="application/json")
//    public ResponseEntity<String> getExistingChatMessages(@PathVariable("channelUuid") String channelUuid) {
//      List<ChatMessageDTO> messages = chatService.getExistingChatMessages(channelUuid);
//
//      return JSONResponseHelper.createResponse(messages, HttpStatus.OK);
//    }
//}