package org.fiware.kiara.ps.rtps.messages;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;




//import org.fiware.kiara.ps.rtps.BinaryInputStream;
//import org.fiware.kiara.ps.rtps.BinaryOutputStream;
//import org.fiware.kiara.ps.rtps.CDRSerializer;
import org.fiware.kiara.ps.rtps.messages.common.types.RTPSEndian;
import org.fiware.kiara.serialization.impl.BinaryInputStream;
import org.fiware.kiara.serialization.impl.BinaryOutputStream;
import org.fiware.kiara.serialization.impl.CDRSerializer;


public class RTPSMessage {
	
	public static short OCTETSTOINLINEQOS_DATASUBMSG = 16;
	public static short DATA_EXTRA_INLINEQOS_SIZE = 4;
	public static short DATA_EXTRA_ENCODING_SIZE = 4;
	
	private RTPSMessageHeader m_header;
	
	private ArrayList<RTPSSubmessage> m_submessages;
	
	private byte[] m_buffer;
	private BinaryOutputStream m_bos;
	private BinaryInputStream m_bis;
	private CDRSerializer m_ser;
	//private int m_pos;
	private RTPSEndian m_endian;
	
	public RTPSMessage(int payloadSize, RTPSEndian endian) { // TODO Endian should be chosen from the user's side
		
		this.m_submessages = new ArrayList<RTPSSubmessage>();
		
		this.m_endian = endian; 
		
		this.m_buffer = new byte[payloadSize];
		
		this.m_bos = new BinaryOutputStream(payloadSize);
		//this.m_bos.setBuffer(this.m_buffer);
		
		this.m_bis = new BinaryInputStream(m_buffer);

		this.m_ser = new CDRSerializer((this.m_endian == RTPSEndian.LITTLE_ENDIAN) ? true : false);
		
		//this.m_pos = 0;
	}
	
	public boolean setHeader(RTPSMessageHeader header) {
		if (this.m_header != null) {
			return false;
		}
		this.m_header = header;
		return true;
	}
	
	public void serialize() {
		try {
			this.m_header.serialize(this.m_ser, this.m_bos, "");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Iterator<RTPSSubmessage> it = this.m_submessages.iterator();
		while (it.hasNext()) {
			RTPSSubmessage subMsg = it.next();
			if (it.hasNext()) {
				subMsg.serialize(this.m_ser, this.m_bos, false);
			} else {
				subMsg.serialize(this.m_ser, this.m_bos, true);
			}
		}
		
		//this.m_pos = this.m_bos.getPosition();
		this.m_buffer = this.m_bos.toByteArray();
		this.m_bis.setBuffer(this.m_buffer);
		
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof RTPSMessage) {
			RTPSMessage instance = (RTPSMessage) other;
			boolean retVal = true;
			if (this.m_header != null) {
				retVal &= this.m_header.equals(instance.m_header);
			}
			retVal &= this.m_submessages.equals(instance.m_submessages);
			
			return retVal;
		}
		return false;
	}
	
	public CDRSerializer getSerializer() {
		return this.m_ser;
	}
	
	public BinaryInputStream getBinaryInputStream() {
		return this.m_bis;
	}
	
	public void initBinaryOutputStream() {
		this.m_bis.setBuffer(this.m_buffer);
	}
	
	public byte[] getBuffer() {
		return this.m_buffer;
	}
	
	public void setBuffer(byte[] buffer) {
		this.m_buffer = buffer;
	}
	
	public int getSize() {
		if (this.m_buffer != null) {
			return this.m_buffer.length;
		}
		return -1;
	}
	
	public RTPSEndian getEndiannes() {
		return this.m_endian;
	}
	
	public void setEndiannes(RTPSEndian endian) {
		if (this.m_endian != endian) {
			this.m_endian = endian;
			this.m_ser = new CDRSerializer((this.m_endian == RTPSEndian.LITTLE_ENDIAN) ? true : false);
		}
	}
	
	public void addSubmessage(RTPSSubmessage submessage) {
		this.m_submessages.add(submessage);
	}
	
	public void clearSubmessages() {
		this.m_submessages.clear();
	}

}