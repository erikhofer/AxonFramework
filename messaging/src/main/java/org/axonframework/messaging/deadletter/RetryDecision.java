package org.axonframework.messaging.deadletter;

import java.time.Instant;
import java.util.Optional;

public interface RetryDecision {

    // TODO: 18-07-22 note that the exact moment is likely not met in a busy system, as there's no guarantee there are threads available to perform the retry at "that" moment
    Optional<Instant> releaseAt();

    String describe();
}
