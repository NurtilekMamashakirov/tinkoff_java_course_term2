package edu.java.configuration;

import edu.java.clients.GitHub.GitHubClient;
import edu.java.clients.StackOverflow.StackOverflowClient;
import org.springframework.context.annotation.Bean;

public class ClientConfig {

    @Bean
    GitHubClient gitHubClient() {
        return new GitHubClient();
    }

    @Bean
    StackOverflowClient stackOverflowClient() {
        return new StackOverflowClient();
    }

}
