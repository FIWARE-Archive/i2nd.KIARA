/* KIARA - Middleware for efficient and QoS/Security-aware invocation of services and exchange of messages
 *
 * Copyright (C) 2015 Proyectos y Sistemas de Mantenimiento S.L. (eProsima)
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
package org.fiware.kiara.ps.qos.policies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.fiware.kiara.ps.qos.parameter.ParameterId;
import org.fiware.kiara.ps.rtps.messages.elements.Parameter;
import org.fiware.kiara.serialization.impl.BinaryInputStream;
import org.fiware.kiara.serialization.impl.SerializerImpl;

/**
 * Class PartitionQosPolicy, to indicate the Partition Qos.
 *
 * @author Rafael Lara {@literal <rafaellara@eprosima.com>}
 */
public class PartitionQosPolicy extends Parameter {

    // TODO
    public QosPolicy parent;

    private final List<String> m_names;

    public PartitionQosPolicy() {
        super(ParameterId.PID_PARTITION, (short) 0);
        this.parent = new QosPolicy(false);
        this.m_names = new ArrayList<>();
    }

    public void pushBack(String name) {
        this.m_names.add(name);
        this.parent.hasChanged = true;
    }

    public void clear() {
        this.m_names.clear();
    }

    public List<String> getNames() {
        return this.m_names;
    }

    public void setNames(List<String> names) {
        m_names.clear();
        m_names.addAll(names);
    }

    public void copy(PartitionQosPolicy value) {
        parent.copy(value.parent);
        setNames(value.m_names);
    }

    @Override
    public void deserializeContent(SerializerImpl impl, BinaryInputStream message, String name) throws IOException {
        // Do nothing

    }

}
