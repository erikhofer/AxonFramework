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

package org.axonframework.eventhandling.deadletter.jpa;

import org.axonframework.messaging.MetaData;
import org.axonframework.serialization.SimpleSerializedObject;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

/**
 * Represents an {@link org.axonframework.eventhandling.EventMessage} when stored into the database.
 *
 * @author Mitchell Herrijgers
 * @since 4.6.0
 */
@Embeddable
public class DeadLetterEntryMessage {

    @Basic(optional = false)
    private String messageType;

    @Column(nullable = false)
    private String eventIdentifier;

    @Basic(optional = false)
    private String timeStamp;

    @Basic(optional = false)
    private String payloadType;

    @Basic
    private String payloadRevision;

    @Basic(optional = false)
    @Lob
    @Column(length = 10000)
    private byte[] payload;

    @Basic
    @Lob
    @Column(length = 10000)
    private byte[] metaData;

    @Basic
    private String type;

    @Basic
    private String aggregateIdentifier;

    @Basic
    private Long sequenceNumber;

    @Basic
    private String tokenType;

    @Basic
    @Lob
    @Column(length = 10000)
    private byte[] token;

    protected DeadLetterEntryMessage() {
        // required by JPA
    }

    /**
     * Constructs a new {@link DeadLetterEntryMessage} using the provided parameters.
     */
    public DeadLetterEntryMessage(String messageType, String eventIdentifier, String timeStamp, String payloadType,
                                  String payloadRevision, byte[] payload, byte[] metaData, String type,
                                  String aggregateIdentifier, Long sequenceNumber, String tokenType, byte[] token) {
        this.messageType = messageType;
        this.eventIdentifier = eventIdentifier;
        this.timeStamp = timeStamp;
        this.payloadType = payloadType;
        this.payloadRevision = payloadRevision;
        this.payload = payload;
        this.metaData = metaData;
        this.type = type;
        this.aggregateIdentifier = aggregateIdentifier;
        this.sequenceNumber = sequenceNumber;
        this.tokenType = tokenType;
        this.token = token;
    }

    public String getMessageType() {
        return messageType;
    }

    public String getEventIdentifier() {
        return eventIdentifier;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getPayloadType() {
        return payloadType;
    }

    public String getPayloadRevision() {
        return payloadRevision;
    }

    public SimpleSerializedObject<byte[]> getPayload() {
        return new SimpleSerializedObject<>(
                payload,
                byte[].class,
                payloadType,
                payloadRevision);
    }

    public SimpleSerializedObject<byte[]> getMetaData() {
        return new SimpleSerializedObject<>(
                metaData,
                byte[].class,
                MetaData.class.getName(),
                null);
    }

    public String getType() {
        return type;
    }

    public String getAggregateIdentifier() {
        return aggregateIdentifier;
    }

    public long getSequenceNumber() {
        return sequenceNumber;
    }

    public SimpleSerializedObject<byte[]> getTrackingToken() {
        if (token == null) {
            return null;
        }
        return new SimpleSerializedObject<>(
                token,
                byte[].class,
                tokenType,
                null);
    }
}