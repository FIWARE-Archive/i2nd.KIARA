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


package com.kiara.complextypes;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

import com.kiara.serialization.impl.Serializable;
import com.kiara.serialization.impl.SerializerImpl;
import com.kiara.serialization.impl.CDRSerializer;
import com.kiara.transport.impl.TransportMessage;

/**
 * Class definition for the user defined type MyStruct.
 *
 * @author Kiaragen tool.
 *
 */
public class MyStruct implements Serializable {

	/*
	 *	Attributes
	 */

	private int myInt;

	private java.lang.String myString;

	private List<List<Integer>> arrayInt;

	private List<java.lang.String> arrayString;

	private List<Integer> sequenceInt;


	/*
	 *	Default constructor
	 */
	public MyStruct() {

		this.myInt = 0;

		this.myString = "";

		this.arrayInt = new ArrayList<List<Integer>>();

		this.arrayString = new ArrayList<java.lang.String>();

		this.sequenceInt = new ArrayList<Integer>();
	}

	/*
	 * This method serializes a MyStruct.
	 *
	 * @see com.kiara.serialization.impl.Serializable#serialize(com.kiara.serialization.impl.SerializerImpl, com.kiara.transport.impl.TransportMessage, java.lang.String)
	 */
	public void serialize(SerializerImpl impl, TransportMessage message, String name) {

		impl.serializeI32(message, name, this.myInt);

		impl.serializeString(message, name, this.myString);

		impl.serializeArrayI32(message, name, this.arrayInt, 10, 5);

		impl.serializeArrayString(message, name, this.arrayString, 10);

		impl.serializeSequenceI32(message, name, this.sequenceInt);
	}

	/*
	 * This method deserializes a MyStruct.
	 *
	 * @see com.kiara.serialization.impl.Serializable#deserialize(com.kiara.serialization.impl.SerializerImpl, com.kiara.transport.impl.TransportMessage, java.lang.String)
	 */
	public void deserialize(SerializerImpl impl, TransportMessage message, String name) {

			this.myInt = impl.deserializeI32(message, name);

			this.myString = impl.deserializeString(message, name);

			this.arrayInt = impl.deserializeArrayI32(message, name, 10, 5);


			this.arrayString = impl.deserializeArrayString(message, name, 10);


		this.sequenceInt = impl.deserializeSequenceI32(message, name, 1);
	}

	/*
	 * @param other An object instance of Object
	 */
	 @Override
	public boolean equals(Object other) {
		boolean comparison = true;

		if (other instanceof MyStruct) {

			comparison = comparison && (this.myInt == ((MyStruct) other).myInt);

			comparison = comparison && (this.myString.compareTo(((MyStruct) other).myString) == 0);

			comparison = comparison && Objects.deepEquals(this.arrayInt, ((MyStruct) other).arrayInt);

			comparison = comparison && Objects.deepEquals(this.arrayString, ((MyStruct) other).arrayString);

			comparison = comparison && this.sequenceInt.equals(((MyStruct) other).sequenceInt);

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

	    current_align += 4 + CDRSerializer.alignment(current_align, 4);
	    current_align += 4 + CDRSerializer.alignment(current_align, 4) + 255 + 1;
	    current_align += ((10 * 5) * 4) + CDRSerializer.alignment(current_align, 4);
	    for(int a = 0; a < (10); ++a)
	    {
	        current_align += 4 + CDRSerializer.alignment(current_align, 4) + 10 + 1;
	    }
	    current_align += 4 + CDRSerializer.alignment(current_align, 4);
	    current_align += (8 * 4) + CDRSerializer.alignment(current_align, 4);


	    return current_align;
	}


	/*
	 * Method to get the attribute myInt.
	 */
	public int getMyInt() {
		return this.myInt;
	}

	/*
	 * Method to set the attribute myInt.
	 */
	public void setMyInt(int myInt) {
		this.myInt = myInt;
	}

	/*
	 * Method to get the attribute myString.
	 */
	public java.lang.String getMyString() {
		return this.myString;
	}

	/*
	 * Method to set the attribute myString.
	 */
	public void setMyString(java.lang.String myString) {
		this.myString = myString;
	}

	/*
	 * Method to get the attribute arrayInt.
	 */
	public List<List<Integer>> getArrayInt() {
		return this.arrayInt;
	}

	/*
	 * Method to set the attribute ArrayInt.
	 */
	public void setArrayInt(List<List<Integer>> arrayInt) {
		this.arrayInt = arrayInt;
	}

	/*
	 * Method to get the attribute arrayString.
	 */
	public List<java.lang.String> getArrayString() {
		return this.arrayString;
	}

	/*
	 * Method to set the attribute ArrayString.
	 */
	public void setArrayString(List<java.lang.String> arrayString) {
		this.arrayString = arrayString;
	}

	/*
	 * Method to get the attribute sequenceInt.
	 */
	public List<Integer> getSequenceInt() {
		return this.sequenceInt;
	}

	/*
	 * Method to set the attribute sequenceInt.
	 */
	public void setSequenceInt(List<Integer> sequenceInt) {
		this.sequenceInt = sequenceInt;
	}
}
