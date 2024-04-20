package com.example.service.impl;



import com.example.mapper.ChatMessageRepository;
import com.example.pojo.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@EnableScheduling
public class ChatService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public ChatMessage saveChatMessage(ChatMessage chatMessage) {
        chatMessage.setTimestamp(LocalDateTime.now());
        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> getMessagesForReceiverSince(String receiver, LocalDateTime since) {
        return chatMessageRepository.findByReceiverAndTimestampAfter(receiver, since);
    }

    public List<ChatMessage> getMessagesBySenderSince(String sender, LocalDateTime since) {
        return chatMessageRepository.findMessagesBySenderSince(sender, since);
    }

    @Scheduled(cron = "0 0 1 * * ?") // 每天凌晨1点执行
    public void cleanupOldMessages() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        chatMessageRepository.deleteByTimestampBefore(sevenDaysAgo);
    }
}