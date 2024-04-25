package edu.java.services.kafka;

import edu.java.configuration.ApplicationConfig;
import edu.java.dto.request.LinkUpdate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ScrapperQueueProducer implements QueueProducer {

    private KafkaTemplate<String, LinkUpdate> kafkaTemplate;
    private ApplicationConfig applicationConfig;

    @Override
    public void send(LinkUpdate linkUpdate) {
        try {
            log.info("Send linkUpdate with kafka");
            kafkaTemplate.send(applicationConfig.kafka().topic(), linkUpdate);
        } catch (Exception ex) {
            log.error("Error by sending linkUpdate with Kafka: ", ex);
        }
    }

}
