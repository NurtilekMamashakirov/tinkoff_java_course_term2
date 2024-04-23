package edu.java.configuration;

import edu.java.retry.RetryTemplateFactory;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Set;
import jdk.jfr.Name;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.support.RetryTemplate;
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
    @NotNull
    Retry retry
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

    @Bean
    @ConditionalOnProperty(prefix = "app.retry", name = "type", havingValue = "const")
    public RetryTemplate constantRetry() {
        return RetryTemplateFactory.constant(
            retry.statusCodes,
            retry.maxAttempts,
            retry.initialInterval
        );
    }

    @Bean
    @ConditionalOnProperty(prefix = "app.retry", name = "type", havingValue = "linear")
    public RetryTemplate linearRetry() {
        return RetryTemplateFactory.linear(
            retry.statusCodes,
            retry.maxAttempts,
            retry.initialInterval,
            retry.maxInterval
        );
    }

    @Bean
    @ConditionalOnProperty(prefix = "app.retry", name = "type", havingValue = "exponential")
    public RetryTemplate exponentialRetry() {
        return RetryTemplateFactory.exponential(
            retry.statusCodes,
            retry.maxAttempts,
            retry.initialInterval,
            retry.multiplier,
            retry.maxInterval
        );
    }

    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }

    public enum AccessType {
        JDBC, JPA, JOOQ
    }

    public record Retry(
        @DefaultValue("429")
        Set<Integer> statusCodes,
        @DefaultValue("const")
        Retry.Type type,
        @DefaultValue("3")
        Integer maxAttempts,
        @DefaultValue("10")
        Long initialInterval,
        @DefaultValue("10")
        Long maxInterval,
        @DefaultValue("2.78")
        Double multiplier
    ) {
        public enum Type {
            CONST, LINEAR, EXPONENTIAL
        }
    }
}
