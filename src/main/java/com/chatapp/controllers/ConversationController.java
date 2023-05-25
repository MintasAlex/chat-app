package com.chatapp.controllers;

import com.chatapp.models.Conversation;
import com.chatapp.services.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/conversation")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @GetMapping("")
//    @PreAuthorize("hasRole('ADMIN')")
    public List<Conversation> getConversations() {
        return conversationService.getConversations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getConversationById(@PathVariable int id) {
        //TODO: Check if user is a member of the conversation
        if (conversationService.getConversationById(id).isPresent()) {
            return ResponseEntity.ok().body(conversationService.getConversationById(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<Conversation> addConversation(@RequestBody Conversation conversation) {
        Conversation newConversation = conversationService.addConversation(conversation);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newConversation.getId())
                .toUri();
        return ResponseEntity.created(location).body(newConversation);
    }

    @PatchMapping("/rename/{id}")
    public ResponseEntity<?> renameConversation(@PathVariable int id, @RequestBody String name) {
        if (conversationService.getConversationById(id).isPresent()) {
            conversationService.renameConversation(id, name);
            return ResponseEntity.ok().body(conversationService.getConversationById(id));
        } else {
            return ResponseEntity.notFound().build();

        }
    }

}
