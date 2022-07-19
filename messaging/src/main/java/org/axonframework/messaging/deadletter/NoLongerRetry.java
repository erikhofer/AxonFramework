package org.axonframework.messaging.deadletter;

import java.time.Instant;

public class NoLongerRetry implements RetryDecision {

    @Override
    public boolean shouldRetry() {
        return false;
    }

    @Override
    public Instant releaseAt() {
        return Instant.MIN;
    }

    @Override
    public String describe() {
        return "Will no longer retry.";
    }
}
