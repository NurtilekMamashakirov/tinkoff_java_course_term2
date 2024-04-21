package edu.java.bot.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.util.retry.Retry;

@Configuration
@ConditionalOnProperty(prefix = "app.retry", name = "type", havingValue = "linear")
public class LinearRetryConfig {

    @Bean Retry retry() {
        return Retry.withThrowable(null);
    }

}
