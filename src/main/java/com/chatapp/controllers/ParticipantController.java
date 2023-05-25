package com.chatapp.controllers;

import com.chatapp.models.EParticipantRole;
import com.chatapp.models.Participant;
import com.chatapp.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/participant")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    @GetMapping("")
    public List<Participant> getParticipants() {
        return participantService.getParticipants();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getParticipantById(@PathVariable int id) {
        if (participantService.getParticipantById(id).isPresent()) {
            return ResponseEntity.ok().body(participantService.getParticipantById(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{id}")
    public List<Participant> getParticipantsByUserId(@PathVariable int id) {
        return participantService.getParticipantsByUserId(id);
    }

    @GetMapping("/conversation/{id}")
    public List<Participant> getParticipantsByConversationId(@PathVariable int id) {
        return participantService.getParticipantsByConversationId(id);
    }

    @PostMapping("")
    public ResponseEntity<Participant> addParticipant(@RequestBody Participant participant) {
        Participant newParticipant = participantService.addParticipant(participant);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newParticipant.getId())
                .toUri();
        return ResponseEntity.created(location).body(newParticipant);
    }

    @PatchMapping("/role/{id}")
    public ResponseEntity<?> updateParticipantRole(@PathVariable int id, @RequestBody EParticipantRole role){
        if (participantService.getParticipantById(id).isPresent()) {
            participantService.setParticipantRole(id, role);
            return ResponseEntity.ok().body(participantService.getParticipantById(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/remove/{id}")
    public ResponseEntity<?> removeParticipant(@PathVariable int id){
        if (participantService.getParticipantById(id).isPresent()) {
            participantService.setParticipantRemoved(id);
            return ResponseEntity.ok().body(participantService.getParticipantById(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
