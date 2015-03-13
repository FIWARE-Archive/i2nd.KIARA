/* KIARA - Middleware for efficient and QoS/Security-aware invocation of services and exchange of messages
 *
 * Copyright (C) 2014 Proyectos y Sistemas de Mantenimiento S.L. (eProsima)
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
package org.fiware.kiara.dynamic.impl.data;

import org.fiware.kiara.dynamic.data.DynamicData;
import org.fiware.kiara.dynamic.data.DynamicMember;
import org.fiware.kiara.dynamic.data.DynamicStruct;
import org.fiware.kiara.typecode.data.StructTypeDescriptor;

/**
*
* @author Rafael Lara {@literal <rafaellara@eprosima.com>}
*
*/
public class DynamicStructImpl extends DynamicMemberedImpl implements DynamicStruct {

    public DynamicStructImpl(StructTypeDescriptor dataDescriptor) {
        super(dataDescriptor, "DynamicStructImpl");
    }

    @Override
    public DynamicData getMember(String name) {
        for (DynamicMember member : this.m_members) {
            if (member.getName().equals(name)) {
                return member.getDynamicData();
            }
        }
        return null;
    }
    
    

}
