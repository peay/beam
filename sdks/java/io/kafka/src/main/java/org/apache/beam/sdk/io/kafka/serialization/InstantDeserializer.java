/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.beam.sdk.io.kafka.serialization;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.joda.time.Instant;

/**
 * Kafka {@link Deserializer} for {@link Instant}.
 *
 * <p>This uses the same encoding as {@link org.apache.beam.sdk.coders.InstantCoder}.
 */
public class InstantDeserializer implements Deserializer<Instant> {
    private LongDeserializer longDeserializer = null;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        if (longDeserializer == null) {
            longDeserializer = new LongDeserializer();
        }
    }

    @Override
    public Instant deserialize(String topic, byte[] bytes) {
        return new Instant(longDeserializer.deserialize(topic, bytes) + Long.MIN_VALUE);
    }

    @Override
    public void close() {
        longDeserializer.close();
    }
}
