package com.example.demo.controllers.restControllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Chat;
import com.example.demo.model.Message;
import com.example.demo.services.impl.ChatServices;





@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatServices chatServices;
    
    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestBody Message entity) {
        Message m =chatServices.addMessage(entity);
        return ResponseEntity.ok(m);
    }

    @PutMapping
    public ResponseEntity<?> updateMessage(@RequestBody Message entity) {
        chatServices.updateMessage(entity);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getChats(@ModelAttribute Chat entity) {
        if (entity.getFrom() == null || entity.getRecipient() == null) {
            return ResponseEntity.badRequest().build();
            
        }
        Chat chat = chatServices.getChat(entity.getFrom(), entity.getRecipient());
        if (chat == null) {
            chat = Chat.builder().from(entity.getFrom()).recipient(entity.getRecipient()).build();
            
        }
        return ResponseEntity.ok(chat);
    }
    
    
}
