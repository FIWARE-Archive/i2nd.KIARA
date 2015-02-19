/* KIARA - Middleware for efficient and QoS/Security-aware invocation of services and exchange of messages
 *
 * Copyright (C) 2014 German Research Center for Artificial Intelligence (DFKI)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.fiware.kiara.serialization.impl;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Dmitri Rubinstein {@literal <dmitri.rubinstein@dfki.de>}
 * @param <T>
 */
public class SequenceAsListSerializer<T> implements Serializer<List<T>> {

    private final Serializer<T> elementSerializer;

    public <M extends Serializer<T>> SequenceAsListSerializer(M elementSerializer) {
        this.elementSerializer = elementSerializer;
    }

    @Override
    public void write(SerializerImpl impl, BinaryOutputStream message, String name, List<T> sequence) throws IOException {
        impl.serializeI32(message, "", sequence.size());

        final int length = sequence.size();
        for (int i = 0; i < length; ++i) {
            elementSerializer.write(impl, message, name, sequence.get(i));
        }
    }

    @Override
    public List<T> read(SerializerImpl impl, BinaryInputStream message, String name) throws IOException {
        int length = impl.deserializeI32(message, "");

        List<T> array = new ArrayList<>(length);

        for (int i = 0; i < length; ++i) {
            array.add(elementSerializer.read(impl, message, name));
        }

        return array;
    }

}
