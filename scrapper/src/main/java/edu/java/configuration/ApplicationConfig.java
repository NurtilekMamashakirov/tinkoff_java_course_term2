package edu.java.configuration;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import jdk.jfr.Name;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app")
public record ApplicationConfig(
    @NotNull
    Scheduler scheduler,
    @DefaultValue("jdbc")
    @Name("database-access-type")
    @NotNull
    AccessType databaseAccessType,
    Kafka kafka,
    @DefaultValue("true")
    Boolean useQueue,

    GithubClientConfig github,
    StackOverflowClientConfig stackOverflow,
    BotConfig bot
) {

    @Bean
    Duration schedulerInterval() {
        return scheduler.interval;
    }

    @Bean
    public AccessType accessType() {
        return databaseAccessType;
    }

    @Bean
    public DefaultConfigurationCustomizer postgresJooqCustomizer() {
        return (DefaultConfiguration c) -> c.settings()
            .withRenderSchema(false)
            .withRenderFormatted(true)
            .withRenderQuotedNames(RenderQuotedNames.NEVER);
    }

    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }

    public enum AccessType {
        JDBC, JPA, JOOQ
    }

    public record Kafka(
        String bootstrapServer,
        String clientId,
        String acksMode,
        Duration deliveryTimeout,
        Integer lingerMs,
        Integer batchSize,
        Integer maxInFlightPerConnection,
        Boolean enableIdempotence,
        String topic,
        String securityProtocol,
        String saslMechanism,
        String saslJaasConfig
    ) {
    }

    public record GithubClientConfig(
        String baseUrl
    ) {
    }

    public record StackOverflowClientConfig(
        String baseUrl
    ) {
    }

    public record BotConfig(
        String baseUrl,
        String updatesUri
    ) {
    }
}
