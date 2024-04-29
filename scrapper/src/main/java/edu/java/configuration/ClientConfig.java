package edu.java.configuration;

import edu.java.clients.bot.BotClient;
import edu.java.clients.github.GitHubClient;
import edu.java.clients.stackOverflow.StackOverflowClient;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {
    @Bean
    public GitHubClient gitHubClient(ApplicationConfig applicationConfig) {
        return new GitHubClient(applicationConfig.github().baseUrl());
    }

    @Bean
    public StackOverflowClient stackOverflowClient(ApplicationConfig applicationConfig) {
        return new StackOverflowClient(applicationConfig.stackOverflow().baseUrl());
    }

    @Bean
    public BotClient botClient(ApplicationConfig applicationConfig) {
        return new BotClient(applicationConfig.bot().baseUrl(), applicationConfig.bot().updatesUri());
    }

}
