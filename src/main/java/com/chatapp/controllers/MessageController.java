package com.chatapp.controllers;

import com.chatapp.models.Message;
import com.chatapp.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("")
    public List<Message> getMessages() {
        return messageService.getMessages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMessagesById(@PathVariable int id) {
        if (messageService.getMessageById(id).isPresent()) {
            return ResponseEntity.ok().body(messageService.getMessageById(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/conversation/{id}")
    public List<Message> getConversationMessages(@PathVariable int id) {
        return messageService.getConversationMessages(id);
    }

    @PostMapping("")
    public ResponseEntity<Message> addMessage(@RequestBody Message message) {
        Message newMessage = messageService.addMessage(message);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newMessage.getId())
                .toUri();
        return ResponseEntity.created(location).body(newMessage);
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<?> updateMessageDeleted(@PathVariable int id){
        if (messageService.getMessageById(id).isPresent()) {
            messageService.setMessageDeleted(id);
            return ResponseEntity.ok().body(messageService.getMessageById(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
