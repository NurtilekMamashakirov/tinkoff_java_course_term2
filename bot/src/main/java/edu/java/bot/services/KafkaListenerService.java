package edu.java.bot.services;

import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.dto.request.LinkUpdate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaListenerService {

    private ApplicationConfig applicationConfig;
    private BotService botService;

    @KafkaListener(topics = "${app.kafka.topic}",
                   containerFactory = "kafkaListenerContainerFactory",
                   groupId = "${app.kafka.group-id}")
    @RetryableTopic(
        attempts = "1",
        kafkaTemplate = "retryableTopicKafkaTemplate",
        dltStrategy = DltStrategy.FAIL_ON_ERROR
    )
    public void listen(LinkUpdate linkUpdate) {
        log.info("Kafka consumer принял сообщение: {}", linkUpdate);
        botService.handleUpdates(linkUpdate);
    }

    @DltHandler
    public void listenDlt(LinkUpdate linkUpdate) {
        log.info("Error with message: {}", linkUpdate);
    }

}
