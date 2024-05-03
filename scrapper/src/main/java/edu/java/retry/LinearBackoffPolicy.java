package edu.java.retry;

import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.BackOffContext;
import org.springframework.retry.backoff.BackOffInterruptedException;
import org.springframework.retry.backoff.BackOffPolicy;

@Data
public class LinearBackoffPolicy implements BackOffPolicy {
    private Long initialInterval;
    private Long maxInterval;

    @Override
    public BackOffContext start(RetryContext context) {
        return new LinearBackoffContext();
    }

    @Override
    @SneakyThrows
    public void backOff(BackOffContext backOffContext) throws BackOffInterruptedException {
        Thread.sleep(Math.min(
            maxInterval,
            initialInterval * ++((LinearBackoffContext) backOffContext).attempts
        ));
    }

    static class LinearBackoffContext implements BackOffContext {
        private Integer attempts = 0;
    }
}
