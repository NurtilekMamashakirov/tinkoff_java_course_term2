package edu.java.configuration;

import edu.java.repository.jooq.impls.JooqLinkRepository;
import edu.java.repository.jooq.impls.JooqTgChatRepository;
import edu.java.services.LinkService;
import edu.java.services.TgChatService;
import edu.java.services.jooq.JooqLinkService;
import edu.java.services.jooq.JooqTgChatService;
import lombok.AllArgsConstructor;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
@AllArgsConstructor
public class JooqAccessConfiguration {
    @Bean
    public LinkService jooqLinkService(JooqLinkRepository linkRepository) {
        return new JooqLinkService(linkRepository);
    }

    @Bean
    public TgChatService jooqTgChatService(JooqTgChatRepository chatRepository) {
        return new JooqTgChatService(chatRepository);
    }

    @Bean
    public DefaultConfigurationCustomizer postgresJooqCustomizer() {
        return (DefaultConfiguration c) -> c.settings()
            .withRenderSchema(false)
            .withRenderFormatted(true)
            .withRenderQuotedNames(RenderQuotedNames.NEVER);
    }
}
