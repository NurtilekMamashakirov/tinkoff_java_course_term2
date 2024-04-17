package edu.java.configuration;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import jdk.jfr.Name;
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
    AccessType databaseAccessType
) {

    @Bean
    Duration schedulerInterval() {
        return scheduler.interval;
    }

    @Bean
    public AccessType accessType() {
        return databaseAccessType;
    }

    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }

    public enum AccessType {
        JDBC, JPA, JOOQ
    }

}
