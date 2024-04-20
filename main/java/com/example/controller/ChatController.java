package com.example.controller;



import com.example.pojo.ChatMessage;
import com.example.service.impl.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ChatController {
    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // 接收并处理新消息，然后将其发送到特定用户
    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        ChatMessage savedMessage = chatService.saveChatMessage(chatMessage);
        messagingTemplate.convertAndSendToUser(chatMessage.getReceiver(), "/queue/messages", savedMessage);
    }

    // 获取指定接收者的消息记录
    @GetMapping("/messages/receiver/{receiver}")
    @ResponseBody
    public List<ChatMessage> getMessagesForReceiver(@PathVariable String receiver) {
        return chatService.getMessagesForReceiverSince(receiver, LocalDateTime.now().minusDays(30));  // 例如获取过去30天的消息
    }

    // 获取指定发送者的消息记录
    @GetMapping("/messages/sender/{sender}")
    @ResponseBody
    public List<ChatMessage> getMessagesBySender(@PathVariable String sender) {
        return chatService.getMessagesBySenderSince(sender, LocalDateTime.now().minusDays(30));  // 同样获取过去30天的消息
    }
}