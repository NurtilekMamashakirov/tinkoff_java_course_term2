package edu.java.bot.retry;

import java.util.Set;
import lombok.experimental.UtilityClass;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

@UtilityClass
public class RetryTemplateFactory {
    public static RetryTemplate constant(
        Set<Integer> retryStatuses,
        Integer maxAttempts,
        Long period
    ) {
        RetryTemplate template = new RetryTemplate();
        FixedBackOffPolicy backoffPolicy = new FixedBackOffPolicy();
        backoffPolicy.setBackOffPeriod(period);
        template.setBackOffPolicy(backoffPolicy);
        template.setRetryPolicy(new HttpStatusRetryPolicy(
            retryStatuses, maxAttempts
        ));
        return template;
    }

    public static RetryTemplate linear(
        Set<Integer> retryStatuses,
        Integer maxAttempts,
        Long initialInterval,
        Long maxInterval
    ) {
        RetryTemplate template = new RetryTemplate();
        LinearBackoffPolicy backoffPolicy = new LinearBackoffPolicy();
        backoffPolicy.setInitialInterval(initialInterval);
        backoffPolicy.setMaxInterval(maxInterval);
        template.setBackOffPolicy(backoffPolicy);
        template.setRetryPolicy(new HttpStatusRetryPolicy(
            retryStatuses, maxAttempts
        ));
        return template;
    }

    public static RetryTemplate exponential(
        Set<Integer> retryStatuses,
        Integer maxAttempts,
        Long initialInterval,
        Double multiplier,
        Long maxInterval
    ) {
        RetryTemplate template = new RetryTemplate();
        ExponentialBackOffPolicy backoffPolicy = new ExponentialBackOffPolicy();
        backoffPolicy.setInitialInterval(initialInterval);
        backoffPolicy.setMultiplier(multiplier);
        backoffPolicy.setMaxInterval(maxInterval);
        template.setBackOffPolicy(backoffPolicy);
        template.setRetryPolicy(new HttpStatusRetryPolicy(
            retryStatuses, maxAttempts
        ));
        return template;
    }
}
