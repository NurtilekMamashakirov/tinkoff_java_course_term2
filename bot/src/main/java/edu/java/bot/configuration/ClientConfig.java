package edu.java.bot.configuration;

import edu.java.bot.clients.ScrapperClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {

    @Bean
    public ScrapperClient scrapperClient(ApplicationConfig applicationConfig) {
        return new ScrapperClient(
            applicationConfig.scrapper().baseUrl(),
            applicationConfig.scrapper().linksUri(),
            applicationConfig.scrapper().chatUri()
        );
    }

}
