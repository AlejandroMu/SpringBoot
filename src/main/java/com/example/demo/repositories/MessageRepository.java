package com.example.demo.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Message;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    
}
