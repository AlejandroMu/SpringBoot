package com.example.demo.services.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.model.Chat;
import com.example.demo.model.Message;
import com.example.demo.repositories.ChatRepository;
import com.example.demo.repositories.MessageRepository;



@Service
public class ChatServices {
    
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public Message addMessage(Message message) {
        
        String from = message.getFrom();
        String recipient = message.getRecipient();

        if (from == null || recipient == null || from.isBlank() || recipient.isBlank()
                || message.getContent() == null || message.getContent().isBlank()
                || message.getType() == null || message.getType().isBlank()) {
            return null;
        }

        Chat chat = chatRepository.findByFromAndRecipient(from, recipient).orElse(
                chatRepository.findByFromAndRecipient(recipient, from).orElse(
                    Chat.builder().from(from).recipient(recipient).build()
                )
        );
        chatRepository.save(chat);
        message.setChat(chat);
        messageRepository.save(message);
        messagingTemplate.convertAndSend("/api/messageTo/" + message.getRecipient(), message);
        return message;
    }

    public Chat getChat(String from, String recipient) {
        return chatRepository.findByFromAndRecipient(from, recipient).orElse(
                chatRepository.findByFromAndRecipient(recipient, from).orElse(
                    Chat.builder().from(from).recipient(recipient).build()
                )
            );  
    }

    public void updateMessage(Message message) {
        Message messageToUpdate = messageRepository.findById(message.getId()).orElse(null);
        if (messageToUpdate != null) {
            messageToUpdate.setRead(message.isRead());
            messageToUpdate.setContent(message.getContent());
            messageToUpdate.setType(message.getType());
            messageRepository.save(messageToUpdate);
            messagingTemplate.convertAndSend("/api/messageTo/" + message.getRecipient(), message);
        }

    }
}
