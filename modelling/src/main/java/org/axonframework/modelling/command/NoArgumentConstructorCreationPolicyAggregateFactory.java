/*
 * Copyright (c) 2010-2022. Axon Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.axonframework.modelling.command;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Default implementation of {@link CreationPolicyAggregateFactory} that invokes the default, No-Arguments
 * constructor of the class.
 * @author Stefan Andjelkovic
 * @since 4.6.0
 * @param <A> aggregate type
 */
public class NoArgumentConstructorCreationPolicyAggregateFactory<A> implements CreationPolicyAggregateFactory<A> {

    private final Class<? extends A> aggregateClass;

    /**
     * Construct an instance of the {@link NoArgumentConstructorCreationPolicyAggregateFactory} for the given type.
     * @param aggregateClass aggregate type
     */
    public NoArgumentConstructorCreationPolicyAggregateFactory(@Nonnull Class<? extends A> aggregateClass) {
        this.aggregateClass = aggregateClass;
    }

    /**
     * Creates the aggregate instance based on the previously provided type. Invokes the default,
     * no-argument constructor of the class.
     * @param identifier ignored identifier
     * @return Aggregate instance
     */
    @Nonnull
    @Override
    public A create(@Nullable Object identifier) {
        try {
            return aggregateClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };
}