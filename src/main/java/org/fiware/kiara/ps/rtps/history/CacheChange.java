package org.fiware.kiara.ps.rtps.history;

import org.fiware.kiara.ps.rtps.messages.common.types.ChangeKind;
import org.fiware.kiara.ps.rtps.messages.elements.GUID;
import org.fiware.kiara.ps.rtps.messages.elements.InstanceHandle;
import org.fiware.kiara.ps.rtps.messages.elements.SequenceNumber;
import org.fiware.kiara.ps.rtps.messages.elements.SerializedPayload;
import org.fiware.kiara.ps.rtps.messages.elements.Timestamp;

public class CacheChange implements Comparable {
	
	private ChangeKind m_changeKind;
	
	private SerializedPayload m_payload;
	
	private GUID m_writerGUID;
	
	private SequenceNumber m_sequenceNumber;
	
	private InstanceHandle m_instanceHandle;
	
	boolean m_isRead = false;
	
	Timestamp m_sourceTimestamp;
	
	public CacheChange() {
		this.m_changeKind = ChangeKind.ALIVE;
		this.m_payload = new SerializedPayload();
		this.m_writerGUID = new GUID();
		this.m_sequenceNumber = new SequenceNumber();
		this.m_instanceHandle = new InstanceHandle();
		this.m_sourceTimestamp = new Timestamp();
	}
	
	public ChangeKind getKind() {
		return this.m_changeKind;
	}
	
	public void setKind(ChangeKind changeKind) {
		this.m_changeKind = changeKind;
	}
	
	public SerializedPayload getSerializedPayload() {
		return this.m_payload;
	}
	
	public void setSerializedPayload(SerializedPayload payload) {
		this.m_payload = payload;
	}
	
	public GUID getWriterGUID() {
		return this.m_writerGUID;
	}
	
	public void setWriterGUID(GUID guid) {
		this.m_writerGUID = guid;
	}
	
	public SequenceNumber getSequenceNumber() {
		return this.m_sequenceNumber;
	}
	
	public void setSequenceNumber(SequenceNumber seqNum) {
		this.m_sequenceNumber = seqNum;
	}

	public InstanceHandle getInstanceHandle() {
		return m_instanceHandle;
	}

	public void setInstanceHandle(InstanceHandle m_instanceHandle) {
		this.m_instanceHandle = m_instanceHandle;
	}
	
	public boolean isRead() {
		return m_isRead;
	}

	public void setRead(boolean isRead) {
		this.m_isRead = isRead;
	}

	public Timestamp getSourceTimestamp() {
		return m_sourceTimestamp;
	}

	public void setSourceTimestamp(Timestamp m_sourceTimestamp) {
		this.m_sourceTimestamp = m_sourceTimestamp;
	}

	@Override
	public int compareTo(Object o) {
		if (this.m_sequenceNumber.equals(((CacheChange) o).m_sequenceNumber)){
			return 0;
		} else if (this.m_sequenceNumber.isGreaterOrEqualThan(((CacheChange) o).m_sequenceNumber)){
			return 1;
		} else if (this.m_sequenceNumber.isLowerOrEqualThan(((CacheChange) o).m_sequenceNumber)){
			return -1;
		}
		return 0;
	}

	

}