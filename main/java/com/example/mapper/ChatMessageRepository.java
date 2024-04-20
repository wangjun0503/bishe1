package com.example.mapper;




import com.example.pojo.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    // 根据接收者查找所有消息，方法名遵循 Spring Data JPA 命名规则，自动解析为查询
    List<ChatMessage> findByReceiver(String receiver);

    // 根据接收者和时间戳之后查找消息
    List<ChatMessage> findByReceiverAndTimestampAfter(String receiver, LocalDateTime timestamp);

    // 使用自定义查询注解来实现复杂查询
    @Query("SELECT m FROM ChatMessage m WHERE m.sender = :sender AND m.timestamp >= :since")
    List<ChatMessage> findMessagesBySenderSince(String sender, LocalDateTime since);

    // 删除特定时间之前的消息
    @Transactional
    void deleteByTimestampBefore(LocalDateTime before);
}