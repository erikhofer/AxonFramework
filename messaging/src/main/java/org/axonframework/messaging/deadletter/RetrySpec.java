package org.axonframework.messaging.deadletter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.axonframework.common.BuilderUtils.*;

/**
 * @author Steven van Beelen
 * @since 4.6.0
 */
public abstract class RetrySpec {

    private long maxRetries = 10;
    private List<Throwable> exceptionsToRetry = new ArrayList<>();
    private Instant retryAt;

    private RetrySpec() {
        // Private constructor as this is a utility builder class.
    }

    public RetrySpec maxRetries(long maxRetries) {
        assertStrictPositive(maxRetries, "The maximum amount of retries should be a positive long");
        this.maxRetries = maxRetries;
        return this;
    }

    public RetrySpec retryOn(Throwable exception) {
        assertNonNull(exception, "An exception to retry on cannot be null");
        exceptionsToRetry.add(exception);
        return this;
    }

    public RetrySpec retryOn(List<Throwable> exceptions) {
        assertNonNull(exceptions, "");
        assertThat(exceptions, e -> !e.isEmpty(), "");
        this.exceptionsToRetry = new ArrayList<>(exceptions);
        return this;
    }
}
