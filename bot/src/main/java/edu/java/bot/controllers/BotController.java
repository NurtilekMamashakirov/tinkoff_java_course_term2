package edu.java.bot.controllers;

import edu.java.bot.dto.request.LinkUpdate;
import edu.java.bot.services.BotService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BotController {

    private BotService botService;

    @PostMapping("/updates")
    public ResponseEntity<Void> postUpdates(@Valid @RequestBody LinkUpdate request) {
        botService.handleUpdates(request);
        return ResponseEntity
            .ok()
            .build();
    }

}
