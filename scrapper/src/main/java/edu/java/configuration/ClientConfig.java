package edu.java.configuration;

import edu.java.clients.bot.BotClient;
import edu.java.clients.github.GitHubClient;
import edu.java.clients.stackOverflow.StackOverflowClient;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.support.RetryTemplate;

public class ClientConfig {

    @Bean
    GitHubClient gitHubClient(RetryTemplate retryTemplate) {
        return new GitHubClient("https://api.github.com/", retryTemplate);
    }

    @Bean
    StackOverflowClient stackOverflowClient(RetryTemplate retryTemplate) {
        return new StackOverflowClient("https://api.stackexchange.com/", retryTemplate);
    }

    @Bean
    BotClient botClient(RetryTemplate retryTemplate) {
        return new BotClient(retryTemplate);
    }

}
