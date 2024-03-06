package edu.java.controllers;

import edu.java.Services.ScrapperService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TgChatController {

    private ScrapperService scrapperService;

    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<Void> registerChat(@PathVariable Long id) {
        scrapperService.addChat(id);
        return ResponseEntity
            .ok()
            .build();
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        scrapperService.deleteChat(id);
        return ResponseEntity
            .ok()
            .build();
    }
}
