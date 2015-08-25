/* KIARA - Middleware for efficient and QoS/Security-aware invocation of services and exchange of messages
 *
 * Copyright (C) 2015 German Research Center for Artificial Intelligence (DFKI)
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
package org.fiware.kiara.ps.rtps.common;

import static org.fiware.kiara.ps.rtps.common.ChangeForReaderStatus.UNSENT;
import org.fiware.kiara.ps.rtps.history.CacheChange;
import org.fiware.kiara.ps.rtps.messages.elements.SequenceNumber;

/**
 *
 * Struct ChangeForReader used to represent the state of a specific change with
 * respect to a specific reader, as well as its relevance.
 *
 * @author Dmitri Rubinstein {@literal <dmitri.rubinstein@dfki.de>}
 */
public class ChangeForReader {

    public ChangeForReaderStatus status;

    /**
     * Boolean specifying if this change is relevant
     */
    public boolean isRelevant;

    /**
     * Sequence number
     */
    public final SequenceNumber seqNum;

    private boolean m_isValid;
    private CacheChange m_change;

    public ChangeForReader() {
        status = UNSENT;
        isRelevant = true;
        seqNum = new SequenceNumber();
        m_isValid = false;
        m_change = null;
    }

    /**
     * Get the cache change
     *
     * @return Cache change
     */
    public CacheChange getChange() {
        return m_change;
    }

    /**
     * Set the cache change
     *
     * @param change Cache change
     * @return
     */
    public boolean setChange(CacheChange change) {
        m_isValid = true;
        seqNum.copy(change.getSequenceNumber());
        m_change = change;
        return true;
    }

    /**
     * Set change as not valid
     */
    void notValid() {
        isRelevant = false;
        m_isValid = false;
        m_change = null;
    }

    public boolean isValid() {
        return m_isValid;
    }

}