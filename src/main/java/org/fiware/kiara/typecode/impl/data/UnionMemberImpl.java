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
package org.fiware.kiara.typecode.impl.data;

import java.util.ArrayList;

import org.fiware.kiara.typecode.data.DataTypeDescriptor;
import org.fiware.kiara.typecode.data.UnionMember;

/**
*
* @author Rafael Lara {@literal <rafaellara@eprosima.com>}
*
*/
public class UnionMemberImpl<T> extends MemberImpl implements UnionMember {
    
    private ArrayList<T> m_labels;
    
    private boolean m_isDefault;

    public UnionMemberImpl(DataTypeDescriptor typeDescriptor, String name, ArrayList<T> labels, boolean isDefault) {
        super(typeDescriptor, name);
        this.m_labels = labels;
        this.m_isDefault = isDefault;
    }
    
    public ArrayList<T> getLabels() {
        return this.m_labels;
    }
    
    public boolean isDefault() {
        return this.m_isDefault;
    }
    
}
