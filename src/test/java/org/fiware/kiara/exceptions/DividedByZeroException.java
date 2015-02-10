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
 *
 *
 * @file .java
 * This file contains the class representing a user defined structure.
 *
 * This file was generated by using the tool Kiaragen.
 *
 */
 
 
package org.fiware.kiara.exceptions;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

import org.fiware.kiara.serialization.impl.Serializable;
import org.fiware.kiara.serialization.impl.SerializerImpl;
import org.fiware.kiara.serialization.impl.CDRSerializer;
import org.fiware.kiara.transport.impl.TransportMessage;

/**
 * Class definition for the user defined type DividedByZeroException. 
 *
 * @author Kiaragen tool.
 *
 */
@SuppressWarnings({ "serial", "unused" })
public class DividedByZeroException extends Exception implements Serializable {
	
	/*
	 *	Attributes
	 */
	 private int code;
	 private java.lang.String message;
	
	/*
	 *	Default constructor
	 */
	public DividedByZeroException() {
		this.code = 0;
		this.message = "";
	}
	
	/*
	 * This method serializes a DividedByZeroException.
	 *
	 * @see org.fiware.kiara.serialization.impl.Serializable#serialize(org.fiware.kiara.serialization.impl.SerializerImpl, org.fiware.kiara.transport.impl.TransportMessage, java.lang.String)
	 */
	public void serialize(SerializerImpl impl, TransportMessage message, String name) {
		impl.serializeI32(message, name, this.code);
		impl.serializeString(message, name, this.message);
	}

	/*
	 * This method deserializes a DividedByZeroException.
	 *
	 * @see org.fiware.kiara.serialization.impl.Serializable#deserialize(org.fiware.kiara.serialization.impl.SerializerImpl, org.fiware.kiara.transport.impl.TransportMessage, java.lang.String)
	 */
	public void deserialize(SerializerImpl impl, TransportMessage message, String name) {
		this.code = impl.deserializeI32(message, name);
		this.message = impl.deserializeString(message, name);
	}

	

	/*
	 * @param other An object instance of Object
	 */
	 @Override
	public boolean equals(Object other) {
		boolean comparison = true;
		
		if (other instanceof DividedByZeroException) {
		
			comparison = comparison && (this.code == ((DividedByZeroException) other).code);

			comparison = comparison && (this.message.compareTo(((DividedByZeroException) other).message) == 0);

		}
		
		return comparison;
	}
	
	/*
	 *This method calculates the maximum size in CDR for this class.
	 * 
	 * @param current_alignment Integer containing the current position in the buffer.
	 */
	public static int getMaxCdrSerializedSize(int current_alignment)
	{
	    int current_align = current_alignment;
	    
	    current_align += 4 + CDRSerializer.alignment(current_align, 4) + 22 + 1;
	            
	    current_align += 4 + CDRSerializer.alignment(current_align, 4);
	    current_align += 4 + CDRSerializer.alignment(current_align, 4) + 255 + 1;
	 
	    return current_align;
	}
	
	/*
	 * Method to get the attribute code.
	 */
	public int getCode() {
		return this.code;
	}

	/*
	 * Method to set the attribute code.
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/*
	 * Method to get the attribute message.
	 */
	public java.lang.String getMessage() {
		return this.message;
	}

	/*
	 * Method to set the attribute message.
	 */
	public void setMessage(java.lang.String message) {
		this.message = message;
	}

	
}
