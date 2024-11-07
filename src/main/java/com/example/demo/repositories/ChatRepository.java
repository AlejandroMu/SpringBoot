package com.example.demo.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Chat;


@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findByFromAndRecipient(String from, String recipient);
    
}
