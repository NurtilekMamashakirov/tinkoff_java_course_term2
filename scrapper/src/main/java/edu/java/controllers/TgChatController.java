package edu.java.controllers;

import edu.java.Services.jdbc.JdbcTgChatService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TgChatController {

    private JdbcTgChatService tgChatService;

    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<Void> registerChat(@PathVariable Long id) {
        tgChatService.register(id);
        return ResponseEntity
            .ok()
            .build();
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        tgChatService.unregister(id);
        return ResponseEntity
            .ok()
            .build();
    }
}
