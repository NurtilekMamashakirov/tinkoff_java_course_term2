package edu.java.configuration;

import edu.java.clients.GitHubClient;
import edu.java.clients.StackOverflowClient;
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
