package org.axonframework.messaging.deadletter;

import org.axonframework.messaging.Message;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import javax.annotation.Nonnull;

public interface RetryableDeadLetterQueue<M extends Message<?>, D extends RetryableDeadLetter<M>> extends DeadLetterQueue<M, D> {


}
