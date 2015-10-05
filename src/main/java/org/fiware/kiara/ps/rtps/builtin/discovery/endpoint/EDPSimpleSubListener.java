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
package org.fiware.kiara.ps.rtps.builtin.discovery.endpoint;

import java.util.List;
import java.util.concurrent.locks.Lock;

import org.fiware.kiara.ps.rtps.builtin.data.ParticipantProxyData;
import org.fiware.kiara.ps.rtps.builtin.data.ReaderProxyData;
import org.fiware.kiara.ps.rtps.common.MatchingInfo;
import org.fiware.kiara.ps.rtps.history.CacheChange;
import org.fiware.kiara.ps.rtps.messages.common.types.ChangeKind;
import org.fiware.kiara.ps.rtps.messages.elements.GUID;
import org.fiware.kiara.ps.rtps.messages.elements.InstanceHandle;
import org.fiware.kiara.ps.rtps.messages.elements.SerializedPayload;
import org.fiware.kiara.ps.rtps.reader.RTPSReader;
import org.fiware.kiara.ps.rtps.reader.ReaderListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Rafael Lara {@literal <rafaellara@eprosima.com>}
 *
 */
public class EDPSimpleSubListener extends ReaderListener {

    public EDPSimple edpSimple;

    public ReaderProxyData readerProxyData;

    private static final Logger logger = LoggerFactory.getLogger(EDPSimpleSubListener.class);

    public EDPSimpleSubListener(EDPSimple edpSimple) {
        this.edpSimple = edpSimple;
        this.readerProxyData = new ReaderProxyData();
    }

    @Override
    public void onReaderMatched(RTPSReader reader, MatchingInfo info) {
        // Do Nothing
    }

    @Override
    public void onNewCacheChangeAdded(RTPSReader reader, CacheChange change) {
        // TODO Auto-generated method stub
        if (!computeKey(change)) {
            logger.warn("Received change with no Key");
        }
        if (change.getKind() == ChangeKind.ALIVE) {
            SerializedPayload pl = change.getSerializedPayload();

            this.readerProxyData.clear();
            if (this.readerProxyData.readFromCDRMessage(change)) {
                change.setInstanceHandle(this.readerProxyData.getKey());
                if (this.readerProxyData.getGUID().getGUIDPrefix().equals(this.edpSimple.m_RTPSParticipant.getGUID().getGUIDPrefix())) {
                    logger.info("Message from own RTPSParticipant, ignoring");
                    this.edpSimple.subReaderPair.getSecond().removeChange(change);
                    return;
                }

                // Look if it is an updated information
                ReaderProxyData rdata = new ReaderProxyData();
                ParticipantProxyData pdata = new ParticipantProxyData();
                ParticipantProxyData pdataAux = new ParticipantProxyData();
                if (this.edpSimple.m_PDP.addReaderProxyData(this.readerProxyData, true, rdata, pdata)) { // Added new data
                    // Check locators
                    if (rdata.getUnicastLocatorList().isEmpty() && rdata.getMulticastLocatorList().isEmpty()) {
                        rdata.getUnicastLocatorList().copy(pdata.getDefaultMulticastLocatorList());
                        rdata.getMulticastLocatorList().copy(pdata.getDefaultMulticastLocatorList());
                    }
                    rdata.setIsAlive(true);
                    this.edpSimple.pairingReaderProxy(rdata);
                } else if (pdataAux.equals(pdata) == true) {
                    logger.warn("Message from UNKNOWN RTPSParticipant, removing");
                    this.edpSimple.subReaderPair.getSecond().removeChange(change);
                    return;
                } else { // Not added, it was already there
                    Lock mutex = this.edpSimple.subReaderPair.getSecond().getMutex();
                    mutex.lock();
                    try {
                        List<CacheChange> changes = this.edpSimple.subReaderPair.getSecond().getChanges();
                        for (int i = 0; i < changes.size(); ++i) {
                            CacheChange ch = changes.get(i);
                            if (ch.getInstanceHandle().equals(change.getInstanceHandle())) {
                                this.edpSimple.subReaderPair.getSecond().removeChange(ch);
                                i--;
                            }
                        }
                        rdata.update(this.readerProxyData);
                        this.edpSimple.pairingReaderProxy(rdata);
                    } finally {
                        mutex.unlock();
                    }
                }
            }
        } else {
            logger.info("Disposed Remote Reader, removing...");
            GUID auxGUID = change.getInstanceHandle().toGUID();
            this.edpSimple.subReaderPair.getSecond().removeChange(change);
            this.edpSimple.removeReaderProxy(auxGUID);
        }
    }

    public boolean computeKey(CacheChange change) {
        if (change.getInstanceHandle().equals(new InstanceHandle())) {

        }
        return true;
    }

}
