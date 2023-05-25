package com.chatapp.services;

import com.chatapp.models.EParticipantRole;
import com.chatapp.models.Participant;
import com.chatapp.repositories.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    public List<Participant> getParticipants() {
        return participantRepository.findAll();
    }

    public Optional<Participant> getParticipantById(int id) {
        return participantRepository.findById(id);
    }

    public List<Participant> getParticipantsByConversationId(int conversationId) {
        return participantRepository.findByConversationId(conversationId);
    }

    public List<Participant> getParticipantsByUserId(int userId) {
        return participantRepository.findByUserId(userId);
    }

    public Participant addParticipant(Participant participant) {
        Participant newParticipant = new Participant(participant.getConversationId(), participant.getUserId(), participant.getRole());
        return participantRepository.save(newParticipant);
    }

    public Participant setParticipantRole(int id, EParticipantRole role) {
        if (participantRepository.existsById(id)) {
            Participant participant = participantRepository.findById(id).get();
            participant.setRole(role);
            return participantRepository.save(participant);
        } else {
            return null;
        }
    }

    public Participant setParticipantRemoved(int id) {
        if (participantRepository.existsById(id)) {
            Participant participant = participantRepository.findById(id).get();
            participant.setIsRemoved(true);
            return participantRepository.save(participant);
        } else {
            return null;
        }
    }
}
