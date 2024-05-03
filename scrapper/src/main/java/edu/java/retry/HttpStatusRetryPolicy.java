package edu.java.retry;

import java.util.Set;
import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.web.client.HttpClientErrorException;

public class HttpStatusRetryPolicy extends SimpleRetryPolicy {
    private final Set<Integer> statuses;

    public HttpStatusRetryPolicy(Set<Integer> statuses, Integer maxAttempts) {
        super(maxAttempts);
        this.statuses = statuses;
    }

    @Override
    public boolean canRetry(RetryContext context) {
        return context.getLastThrowable() instanceof HttpClientErrorException e
            ? statuses.contains(e.getStatusCode().value())
            : super.canRetry(context);
    }
}
