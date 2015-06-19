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
package org.fiware.kiara.ps.rtps.messages.elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;

import org.fiware.kiara.serialization.impl.BinaryInputStream;
import org.fiware.kiara.serialization.impl.BinaryOutputStream;
import org.fiware.kiara.serialization.impl.SerializerImpl;
import org.fiware.kiara.ps.rtps.messages.RTPSSubmessageElement;

public class SequenceNumberSet extends RTPSSubmessageElement {
	
	private SequenceNumber m_base;
	private ArrayList<SequenceNumber> m_set;
	
	public SequenceNumberSet() {
		this.m_set = new ArrayList<SequenceNumber>();
		this.m_base = new SequenceNumber();
	}
	
	public void setBase(SequenceNumber base) {
		this.m_base = base;
	}
	
	public boolean add(SequenceNumber seqNum) {
		long base64 = m_base.toLong();
		long in64 = seqNum.toLong();
		if (in64 >= base64 && in64 <= base64 + 255) {
			this.m_set.add(seqNum);
			return true;
		} 
		return false;
	}

	@Override
	public void serialize(SerializerImpl impl, BinaryOutputStream message, String name) throws IOException {
		
		if (this.m_base.toLong() == 0) {
			return;
		}
		
		this.m_base.serialize(impl, message, name);
		
		if (this.m_set.isEmpty()) {
			impl.serializeUI32(message, name, 0);
			return;
		}
		
		SequenceNumber maxSeq = this.getMaxSeqNum();
		
		if (maxSeq != null) {
			int numBits = (int) (maxSeq.toLong() - this.m_base.toLong() + 1);
				
			if (numBits > 256) {
				return;
			}
			
			impl.serializeUI32(message, name, numBits);
			
			byte n_longs = (byte) ((numBits + 31) / 32);
			int[] bitmap = new int[n_longs];
			
			for (int i=0; i < n_longs; ++i) {
				bitmap[i] = 0;
			}
			
			int deltaN = 0;
			//int initialIndex = 0;
			
			//BitSet bitset = new BitSet(32);
			for (SequenceNumber sn : this.m_set) {
				deltaN = (int) (sn.toLong() - this.m_base.toLong());
				//System.out.println((1 << (31 - deltaN % 32)));
				//System.out.println(31 - (deltaN % 32));
				bitmap[(int)(deltaN/32)] = (bitmap[(int)(deltaN/32)] | (1 << (31 - deltaN % 32)));
			}
			
			for (int i=0; i < n_longs; ++i) {
				impl.serializeI32(message, name, bitmap[i]);
			}
			
		}
		
	}
	
	@Override
	public void deserialize(SerializerImpl impl, BinaryInputStream message, String name) throws IOException {
		this.m_base.deserialize(impl, message, name);
		
		int numBits = impl.deserializeUI32(message, name);
		
		byte n_longs = (byte) ((numBits + 31) / 32);
		int[] bitmap = new int[n_longs];
		
		for (int i=0; i < n_longs; ++i) {
			bitmap[i] = impl.deserializeI32(message, name);
			for (byte bit = 0; bit < 32; ++bit) {
				if ((bitmap[i] & (1<<(31-bit%32)))==(1<<(31-bit%32))) {
					SequenceNumber seqNum = new SequenceNumber(this.m_base, (i * 32) + bit);
					this.add(seqNum);
				}
			}
		}
		
		
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof SequenceNumberSet) {
			boolean retVal = true;
			
			retVal &= this.m_base.equals(((SequenceNumberSet) other).m_base);
			retVal &= this.m_set.equals(((SequenceNumberSet) other).m_set);
			
			return retVal;
		}
		return false;
	}

	public SequenceNumber getMaxSeqNum() {
		
		SequenceNumber ret = null;
		long retVal = 0;
		
		for (SequenceNumber sn : this.m_set) {
			if (sn.toLong() >= retVal) {
				ret = sn;
				retVal = sn.toLong();
			}
		}
		
		return ret;
	}

	@Override
	public short getSerializedSize() {
		// TODO Auto-generated method stub
		return 0;
	}

}
