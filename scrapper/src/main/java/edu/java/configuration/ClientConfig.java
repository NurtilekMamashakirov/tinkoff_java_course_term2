package edu.java.configuration;

import edu.java.clients.github.GitHubClient;
import edu.java.clients.stackOverflow.StackOverflowClient;
import org.springframework.context.annotation.Bean;

public class ClientConfig {

    @Bean
    GitHubClient gitHubClient() {
        return new GitHubClient("https://api.github.com/");
    }

    @Bean
    StackOverflowClient stackOverflowClient() {
        return new StackOverflowClient("https://api.stackexchange.com/");
    }

}
