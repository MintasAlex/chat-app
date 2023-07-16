package com.chatapp.services;

import com.chatapp.models.Message;
import com.chatapp.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(int id) {
        return messageRepository.findById(id);
    }

    public List<Message> getConversationMessages(int conversationId) {
        return messageRepository.findByConversationId(conversationId);
    }

    public Message addMessage(Message message) {
        Message newMessage = new Message(message.getConversationId(), message.getSenderId(), message.getContent());
        simpMessagingTemplate.convertAndSend("/topic/messages" + message.getConversationId(), newMessage); // Publish to the "/topic/messages" topic
        return messageRepository.save(newMessage);
    }

    public Message setMessageDeleted(int id) {
        if (messageRepository.existsById(id)) {
            Message message = messageRepository.findById(id).get();
            message.setIsDeleted(true);
            message.setDeletedAt(new Timestamp(System.currentTimeMillis()));
            return messageRepository.save(message);
        } else {
            return null;
        }
    }
}
