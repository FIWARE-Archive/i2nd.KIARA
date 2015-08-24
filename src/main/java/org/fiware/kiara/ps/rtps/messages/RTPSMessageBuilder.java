package org.fiware.kiara.ps.rtps.messages;

import javax.sound.midi.Sequence;

import org.fiware.kiara.ps.rtps.common.EncapsulationKind;
import org.fiware.kiara.ps.rtps.common.TopicKind;
import org.fiware.kiara.ps.rtps.history.CacheChange;
import org.fiware.kiara.ps.rtps.messages.common.types.ChangeKind;
import org.fiware.kiara.ps.rtps.messages.common.types.RTPSEndian;
import org.fiware.kiara.ps.rtps.messages.common.types.SubmessageFlags;
import org.fiware.kiara.ps.rtps.messages.common.types.SubmessageId;
import org.fiware.kiara.ps.rtps.messages.elements.Count;
import org.fiware.kiara.ps.rtps.messages.elements.EntityId;
import org.fiware.kiara.ps.rtps.messages.elements.ExtraFlags;
import org.fiware.kiara.ps.rtps.messages.elements.GUIDPrefix;
import org.fiware.kiara.ps.rtps.messages.elements.OctectsToInlineQos;
import org.fiware.kiara.ps.rtps.messages.elements.Pad;
import org.fiware.kiara.ps.rtps.messages.elements.ParameterList;
import org.fiware.kiara.ps.rtps.messages.elements.ProtocolVersion;
import org.fiware.kiara.ps.rtps.messages.elements.SequenceNumber;
import org.fiware.kiara.ps.rtps.messages.elements.SequenceNumberSet;
import org.fiware.kiara.ps.rtps.messages.elements.SerializedPayload;
import org.fiware.kiara.ps.rtps.messages.elements.Timestamp;
import org.fiware.kiara.ps.rtps.messages.elements.Unused;
import org.fiware.kiara.ps.rtps.messages.elements.VendorId;
import org.fiware.kiara.ps.rtps.messages.elements.parameters.ParameterKey;
import org.fiware.kiara.ps.rtps.messages.elements.parameters.ParameterSentinel;
import org.fiware.kiara.ps.rtps.messages.elements.parameters.ParameterStatus;

public class RTPSMessageBuilder {
	
	public static RTPSMessage createMessage(RTPSEndian endian) {
		RTPSMessage retVal = new RTPSMessage(128, endian); // TODO Change buffer size
		
		return retVal;
	}
	
	public static RTPSMessage createMessage(int bufferSize, RTPSEndian endian) {
		RTPSMessage retVal = new RTPSMessage(bufferSize, endian);
		
		return retVal;
	}
	
	public static boolean addHeader(RTPSMessage message, GUIDPrefix prefix) {
		
		RTPSMessageHeader header = new RTPSMessageHeader();
		
		message.setHeader(header);
		
		return true;
	}
	
	public static boolean addSubmessageInfoTS(RTPSMessage message, java.sql.Timestamp t, boolean invalidateFlag) {
		
		RTPSSubmessage infoTs = new RTPSSubmessage();
		RTPSSubmessageHeader subHeader = new RTPSSubmessageHeader();
		
		// Submessage ID
		subHeader.setSubmessageId(SubmessageId.INFO_TS);
		//short size = 8;
		
		// Flags
		SubmessageFlags flags = new SubmessageFlags((byte) 0x0);
		if (message.getEndiannes() == RTPSEndian.LITTLE_ENDIAN) {
			flags.setBitValue(0, true);
		} 
		if (invalidateFlag) {
			flags.setBitValue(1, true);
			//size = 0;
		}
		infoTs.setSubmessageEndian(message.getEndiannes());
		subHeader.setFlags(flags);
		
		// Bytes to next header
		//subHeader.setOctectsToNextHeader(size); 
		
		// InfoTS message
		infoTs.setSubmessageHeader(subHeader);
		if (!invalidateFlag) {
			// Only present if !invalid
			infoTs.addSubmessageElement(new Timestamp(t));
		}
		//infoTs.initSerializer();
		
		// Add submessage
		message.addSubmessage(infoTs);
		
		return true;
	}
	
	public static boolean addSubmessageInfoDST(RTPSMessage message, GUIDPrefix guidPrefix) {
		
		RTPSSubmessage infoDst = new RTPSSubmessage();
		RTPSSubmessageHeader subHeader = new RTPSSubmessageHeader();
		
		// Submessage ID
		subHeader.setSubmessageId(SubmessageId.INFO_DST);
		//short size = 12;
		
		// Flags
		SubmessageFlags flags = new SubmessageFlags((byte) 0x0);
		if (message.getEndiannes() == RTPSEndian.LITTLE_ENDIAN) {
			flags.setBitValue(0, true);
		} 
		infoDst.setSubmessageEndian(message.getEndiannes());
		subHeader.setFlags(flags);
		
		// Bytes to next header
		//subHeader.setOctectsToNextHeader(size);
		
		infoDst.setSubmessageHeader(subHeader);
		infoDst.addSubmessageElement(guidPrefix);
		
		message.addSubmessage(infoDst);
		
		return true;
	}
	
	public static boolean addSubmessageInfoSRC(RTPSMessage message, ProtocolVersion protocolVersion, VendorId vendorId, GUIDPrefix guidPrefix) {
		
		RTPSSubmessage infoSrc = new RTPSSubmessage();
		RTPSSubmessageHeader subHeader = new RTPSSubmessageHeader();
		
		// Submessage ID
		subHeader.setSubmessageId(SubmessageId.INFO_SRC);
		//short size = 12;
		
		// Flags
		SubmessageFlags flags = new SubmessageFlags((byte) 0x0);
		if (message.getEndiannes() == RTPSEndian.LITTLE_ENDIAN) {
			flags.setBitValue(0, true);
		}
		infoSrc.setSubmessageEndian(message.getEndiannes());
		subHeader.setFlags(flags);
		
		// Bytes to next header
		//subHeader.setOctectsToNextHeader(size);
		
		infoSrc.setSubmessageHeader(subHeader);
		infoSrc.addSubmessageElement(new Unused(4));
		infoSrc.addSubmessageElement(protocolVersion);
		infoSrc.addSubmessageElement(vendorId);
		infoSrc.addSubmessageElement(guidPrefix);
		
		
		message.addSubmessage(infoSrc);
		
		return true;
	}
	
	public static boolean addSubmessageInfoTSNow(RTPSMessage message, boolean invalidateFlag) {
		java.sql.Timestamp t = new java.sql.Timestamp(System.currentTimeMillis());
		return addSubmessageInfoTS(message, t, invalidateFlag);
	}
	
	public static boolean addSubmessageHeartbeat(RTPSMessage message, EntityId readerId, EntityId writerId, SequenceNumber firstSN, SequenceNumber lastSN, Count count, boolean isFinal, boolean livelinessFlag) {
		
		RTPSSubmessage submessageHeartbeat = new RTPSSubmessage();
		
		RTPSSubmessageHeader subHeader = new RTPSSubmessageHeader();
		
		// Submessage ID
		subHeader.setSubmessageId(SubmessageId.HEARTBEAT);
		//
		// Flags
		SubmessageFlags flags = new SubmessageFlags((byte) 0x0);
		if (message.getEndiannes() == RTPSEndian.LITTLE_ENDIAN) {
			flags.setBitValue(0, true);
		} 
		submessageHeartbeat.setSubmessageEndian(message.getEndiannes());
		
		if (isFinal) {
			flags.setBitValue(1, true);
		}
		
		if (livelinessFlag) {
			flags.setBitValue(2, true);
		}
		
		subHeader.setFlags(flags);
		
		submessageHeartbeat.addSubmessageElement(readerId);
		submessageHeartbeat.addSubmessageElement(writerId);
		
		submessageHeartbeat.addSubmessageElement(firstSN);
		submessageHeartbeat.addSubmessageElement(lastSN);
		
		submessageHeartbeat.addSubmessageElement(count);
		
		submessageHeartbeat.setSubmessageHeader(subHeader);

		message.addSubmessage(submessageHeartbeat);
		
		return true;
	}
	
	public static boolean addSubmessageAckNack(RTPSMessage message, EntityId readerId, EntityId writerId, SequenceNumberSet set, Count count, boolean isFinal) {
		
		RTPSSubmessage submessageAckNack = new RTPSSubmessage();
		
		// Submessage ID
		RTPSSubmessageHeader subHeader = new RTPSSubmessageHeader();
		subHeader.setSubmessageId(SubmessageId.ACKNACK);
		
		// Endian flag
		SubmessageFlags flags = new SubmessageFlags((byte) 0x0);
		if (message.getEndiannes() == RTPSEndian.LITTLE_ENDIAN) {
			flags.setBitValue(0, true);
		} 
		submessageAckNack.setSubmessageEndian(message.getEndiannes());
		
		if (isFinal) {
			flags.setBitValue(1, true);
		}
		
		subHeader.setFlags(flags);
		
		submessageAckNack.addSubmessageElement(readerId);
		submessageAckNack.addSubmessageElement(writerId);
		submessageAckNack.addSubmessageElement(set);
		submessageAckNack.addSubmessageElement(count);
		
		submessageAckNack.setSubmessageHeader(subHeader);
		
		message.addSubmessage(submessageAckNack);
		
		return true;
	}
	
	public static boolean addSubmessageGap(RTPSMessage message, SequenceNumber gapStart, SequenceNumberSet gapList, EntityId readerId, EntityId writerId) {
		
		RTPSSubmessage submessageGap = new RTPSSubmessage();
		
		// Submessage ID
		RTPSSubmessageHeader subHeader = new RTPSSubmessageHeader();
		subHeader.setSubmessageId(SubmessageId.GAP);
		
		// Endian flag
		SubmessageFlags flags = new SubmessageFlags((byte) 0x0);
		if (message.getEndiannes() == RTPSEndian.LITTLE_ENDIAN) {
			flags.setBitValue(0, true);
		} 
		submessageGap.setSubmessageEndian(message.getEndiannes());
		
		subHeader.setFlags(flags);
		
		submessageGap.addSubmessageElement(readerId);
		submessageGap.addSubmessageElement(writerId);
		submessageGap.addSubmessageElement(gapStart);
		submessageGap.addSubmessageElement(gapList);
		
		submessageGap.setSubmessageHeader(subHeader);
		
		message.addSubmessage(submessageGap);
		
		return true;
	}
	
	public static boolean addSubmessageData(RTPSMessage message, CacheChange change, TopicKind topicKind, EntityId readerId, boolean expectsInlineQos, ParameterList inlineQos) {
		
		// Submessage ID
		RTPSSubmessageHeader subHeader = new RTPSSubmessageHeader();
		subHeader.setSubmessageId(SubmessageId.DATA);
		
		RTPSSubmessage submessageData = new RTPSSubmessage();
		
		// Endian flag
		SubmessageFlags flags = new SubmessageFlags((byte) 0x0);
		if (message.getEndiannes() == RTPSEndian.LITTLE_ENDIAN) {
			flags.setBitValue(0, true);
		} 
		submessageData.setSubmessageEndian(message.getEndiannes());
		//submessageData.initSerializer();
		
		// Data flags
		boolean dataFlag = false;
		boolean keyFlag = false;
		boolean inlineQosFlag = false;
		if (change.getKind() == ChangeKind.ALIVE && change.getSerializedPayload().getLength() > 0 && change.getSerializedPayload().getData() != null) {
			dataFlag = true;
			keyFlag = true;
		} else {
			dataFlag = false;
			keyFlag = true;
		}
		
		if (topicKind == TopicKind.NO_KEY) {
			keyFlag = false;
		}
		
		if (inlineQos != null || expectsInlineQos || change.getKind() != ChangeKind.ALIVE) {
			if (topicKind == TopicKind.WITH_KEY) {
				flags.setBitValue(1, true);
				inlineQosFlag = true;
				keyFlag = false;
			}
		}
		
		if (dataFlag) {
			flags.setBitValue(2, dataFlag);
		}
		
		if (keyFlag) {
			flags.setBitValue(3, keyFlag);
		}
		
		subHeader.setFlags(flags);
		
		// DATA Submessage creation
		
		boolean error_code = false;
		
		ExtraFlags extraFlags = new ExtraFlags();
		submessageData.addSubmessageElement(extraFlags);
		
		short octectsToInlineQos = 16;
		OctectsToInlineQos octects2Qos = new OctectsToInlineQos(octectsToInlineQos);
		submessageData.addSubmessageElement(octects2Qos);
		
		submessageData.addSubmessageElement(readerId);
		
		submessageData.addSubmessageElement(change.getWriterGUID().getEntityId());
		
		submessageData.addSubmessageElement(change.getSequenceNumber());
		
		if (inlineQosFlag) {
			// TODO Insertar por este orden: InstanceHandle, Status, inlineQos, Sentinel
			if (inlineQos != null) {
				if (inlineQos.getHasChanged()) {
					submessageData.addSubmessageElement(inlineQos);
				}
			}
			if (topicKind == TopicKind.WITH_KEY) {
				
			}
			if (inlineQos != null) {
				
			}
		}
		
		// Add SerializedPayload
		if (dataFlag) {
			
			//SerializedPayload payload = new SerializedPayload();
			//payload.setEncapsulationKind(change.getSerializedPayload().getEncapsulation());
			// payload.options are serialized automatically (no existing attribute)
			//payload.setData(change.getSerializedPayload().getData(), change.getSerializedPayload().getSize());
			//submessageData.setSubmessageEndian(change.getSerializedPayload().getEncapsulation());
			submessageData.addSubmessageElement(change.getSerializedPayload());
			
		}
		
		if (keyFlag) {
			

			SerializedPayload payload = new SerializedPayload();
			if (submessageData.getSubmessageEndian() == RTPSEndian.BIG_ENDIAN) {
				payload.setEncapsulationKind(EncapsulationKind.PL_CDR_BE);
			} else {
				payload.setEncapsulationKind(EncapsulationKind.PL_CDR_LE);
			}
			// payload.options are serialized automatically (no existing attribute)
			
			// Add ParameterKey, ParameterStatus and ParameterSentinel to payload
			payload.addParameter(new ParameterKey(change.getInstanceHandle()));
			payload.addParameter(new ParameterStatus(change.getKind()));
			payload.addParameter(new ParameterSentinel());
			submessageData.addSubmessageElement(payload);
			
		}
		
		subHeader.setOctectsToNextHeader(submessageData.getLength() /*(short) 24*/);
		submessageData.setSubmessageHeader(subHeader);
		
		
		/*byte status = 0;
		if (change.getKind() == ChangeKind.NOT_ALIVE_DISPOSED) {
			status = (byte) (status | 0x1);
		} else if (change.getKind() == ChangeKind.NOT_ALIVE_UNREGISTERED) {
			status = (byte) (status | 0x2);
		} else if (change.getKind() == ChangeKind.NOT_ALIVE_DISPOSED_UNREGISTERED) {
			status = (byte) (status | 0x3);
		}*/
		
		
		
		message.addSubmessage(submessageData);
		
		return true;
	}
	
	public static boolean addSubmessagePad(RTPSMessage message, short nBytes) {
		
		RTPSSubmessage submessagePad = new RTPSSubmessage();
		
		// Submessage ID
		RTPSSubmessageHeader subHeader = new RTPSSubmessageHeader();
		subHeader.setSubmessageId(SubmessageId.PAD);
		
		// Endian flag
		SubmessageFlags flags = new SubmessageFlags((byte) 0x0);
		if (message.getEndiannes() == RTPSEndian.LITTLE_ENDIAN) {
			flags.setBitValue(0, true);
		} 
		submessagePad.setSubmessageEndian(message.getEndiannes());
		
		subHeader.setFlags(flags);
		
		submessagePad.addSubmessageElement(new Pad(nBytes));
		submessagePad.setSubmessageHeader(subHeader);
		
		message.addSubmessage(submessagePad);
		
		return true;
	}

}