package com.chatapp.services;

import com.chatapp.models.Conversation;
import com.chatapp.repositories.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    public List<Conversation> getConversations() {
        return conversationRepository.findAll();
    }

    public Optional<Conversation> getConversationById(int id) {
        return conversationRepository.findById(id);
    }

    public Conversation addConversation(Conversation conversation) {
        Conversation newConversation = new Conversation(conversation.getTitle());
        return conversationRepository.save(newConversation);
    }

    public Conversation renameConversation(int id, String name) {
        if (conversationRepository.existsById(id)) {
            Conversation conversation = conversationRepository.findById(id).get();
            conversation.setTitle(name);
            return conversationRepository.save(conversation);
        } else {
            return null;
        }
    }

}
