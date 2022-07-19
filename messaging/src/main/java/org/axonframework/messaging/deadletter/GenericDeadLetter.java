package org.axonframework.messaging.deadletter;

import org.axonframework.common.IdentifierFactory;
import org.axonframework.messaging.Message;

import java.time.Instant;
import java.util.Objects;
import javax.annotation.Nullable;

/**
 * Generic implementation of the {@link DeadLetter} allowing any type of {@link Message} to be dead lettered.
 *
 * @author Steven van Beelen
 * @since 4.6.0
 */
class GenericDeadLetter<M extends Message<?>> implements DeadLetter<M> {

    private final String identifier;
    private final QueueIdentifier queueIdentifier;
    private final M message;
    private final Cause cause;
    private final Instant enqueueAt;

    public GenericDeadLetter(QueueIdentifier queueIdentifier,
                             M message,
                             Throwable cause,
                             Instant enqueueAt) {
        this(IdentifierFactory.getInstance().generateIdentifier(),
             queueIdentifier, message, cause != null ? new GenericCause(cause) : null, enqueueAt);
    }

    public GenericDeadLetter(String identifier,
                             QueueIdentifier queueIdentifier,
                             M message,
                             Throwable cause,
                             Instant enqueueAt) {
        this(identifier, queueIdentifier, message, cause != null ? new GenericCause(cause) : null, enqueueAt);
    }

    public GenericDeadLetter(String identifier,
                             QueueIdentifier queueIdentifier,
                             M message,
                             Cause cause,
                             Instant enqueueAt) {
        this.identifier = identifier;
        this.queueIdentifier = queueIdentifier;
        this.message = message;
        this.cause = cause;
        this.enqueueAt = enqueueAt;
    }

    @Override
    public String identifier() {
        return identifier;
    }

    @Override
    public QueueIdentifier queueIdentifier() {
        return queueIdentifier;
    }

    @Override
    public M message() {
        return message;
    }

    @Nullable
    @Override
    public Cause cause() {
        return cause;
    }

    @Override
    public Instant enqueuedAt() {
        return enqueueAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        // Check does not include evict and release operations to allow easy letter removal in the DeadLetterQueue.
        GenericDeadLetter<?> that = (GenericDeadLetter<?>) o;
        return Objects.equals(identifier, that.identifier)
                && Objects.equals(queueIdentifier, that.queueIdentifier)
                && Objects.equals(message, that.message)
                && Objects.equals(cause, that.cause)
                && Objects.equals(enqueueAt, that.enqueueAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, queueIdentifier, message, cause, enqueueAt);
    }

    @Override
    public String toString() {
        return "GenericDeadLetter{" +
                "identifier='" + identifier + '\'' +
                ", queueIdentifier=" + queueIdentifier +
                ", message=" + message +
                ", cause=" + cause +
                ", enqueueAt=" + enqueueAt +
                '}';
    }
}
