/*
 * Copyright (c) 2010-2019. Axon Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.axonframework.axonserver.connector;

import io.axoniq.axonserver.grpc.control.ClientIdentification;
import org.axonframework.axonserver.connector.event.StubServer;
import org.axonframework.config.TagsConfiguration;
import org.junit.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link AxonServerConnectionManager}.
 *
 * @author Milan Savic
 */
public class AxonServerConnectionManagerTest {

    private StubServer stubServer = new StubServer(8124);

    @Before
    public void setUp() throws IOException {
        stubServer.start();
    }

    @After
    public void tearDown() throws InterruptedException {
        stubServer.shutdown();
    }

    @Test
    public void checkWhetherConnectionPreferenceIsSent() {
        TagsConfiguration tags = new TagsConfiguration(Collections.singletonMap("key", "value"));
        AxonServerConfiguration configuration = AxonServerConfiguration.builder().build();
        AxonServerConnectionManager axonServerConnectionManager = new AxonServerConnectionManager(configuration, tags);

        assertNotNull(axonServerConnectionManager.getChannel());

        List<ClientIdentification> clientIdentificationRequests = stubServer.getPlatformService()
                                                                            .getClientIdentificationRequests();
        assertEquals(1, clientIdentificationRequests.size());
        Map<String, String> expectedTags = clientIdentificationRequests.get(0).getTagsMap();
        assertNotNull(expectedTags);
        assertEquals(1, expectedTags.size());
        assertEquals("value", expectedTags.get("key"));
    }
}
