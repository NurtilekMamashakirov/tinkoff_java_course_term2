package edu.java.bot.configuration;

import edu.java.bot.dto.request.LinkUpdate;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@AllArgsConstructor
public class KafkaConfig {

    private ApplicationConfig applicationConfig;

    @Bean
    public ConsumerFactory<String, LinkUpdate> linkUpdateConsumerFactory() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, applicationConfig.kafka().bootstrapServer());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, applicationConfig.kafka().groupId());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, applicationConfig.kafka().autoOffsetReset());
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, applicationConfig.kafka().enableAutoCommit());
        properties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, applicationConfig.kafka().maxPollIntervalMs());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(properties);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, LinkUpdate> kafkaListenerContainerFactory(
        ConsumerFactory<String, LinkUpdate> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, LinkUpdate> containerFactory =
            new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(consumerFactory);
        containerFactory.setConcurrency(applicationConfig.kafka().concurrency());
        return containerFactory;
    }

}
