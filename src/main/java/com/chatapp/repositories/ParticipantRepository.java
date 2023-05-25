package com.chatapp.repositories;

import com.chatapp.models.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

    public Optional<Participant> findByUserIdAndConversationId(int userId, int conversationId);

    public List<Participant> findByConversationId(int conversationId);

    public List<Participant> findByUserId(int userId);



}
