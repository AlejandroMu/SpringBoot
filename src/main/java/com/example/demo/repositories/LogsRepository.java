package com.example.demo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.mongo.Logs;

@Repository
public interface LogsRepository extends MongoRepository<Logs, String> {
    Logs findByUsername(String username);
    Logs findByAction(String action);
    Logs findByDate(String date);
}
