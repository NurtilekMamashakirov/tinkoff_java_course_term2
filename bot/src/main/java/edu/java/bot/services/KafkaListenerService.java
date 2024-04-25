package edu.java.bot.services;

import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.dto.request.LinkUpdate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaListenerService {

    private ApplicationConfig applicationConfig;
    private BotService botService;

    @KafkaListener(topics = "", containerFactory = "")
    public void listen(LinkUpdate linkUpdate) {
        log.info("Kafka consumer принял сообщение: {}", linkUpdate);
        botService.handleUpdates(linkUpdate);
    }

}
