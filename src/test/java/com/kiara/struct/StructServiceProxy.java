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
 * @file StructServiceProxy.java
 * This file contains the proxy implementation.
 *
 * This file was generated by using the tool Kiaragen.
 *
 */


package com.kiara.struct;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import com.kiara.netty.TransportMessageDispatcher;
import com.kiara.serialization.Serializer;
import com.kiara.serialization.impl.CDRSerializer;
import com.kiara.transport.Transport;
import com.kiara.transport.impl.TransportMessage;

import java.nio.ByteBuffer;

/**
 * Class containing the proxy implementation for all the services.
 *
 * @author Kiaragen tool.
 *
 */
class StructServiceProxy implements StructServiceClient {

	public StructServiceProxy(Serializer ser, Transport transport) {
		m_ser = (com.kiara.serialization.impl.SerializerImpl) ser;
		m_transport = (com.kiara.transport.impl.TransportImpl) transport;
	}


	private int sendReceivePrimitives_required_size() {
		int op_size = 0;

		op_size += 4 + CDRSerializer.alignment(op_size, Integer.SIZE); // MessageID
		op_size += 4 + CDRSerializer.alignment(op_size, 4) + "StructService".length() + 1; // Interface name
		op_size += 4 + CDRSerializer.alignment(op_size, 4) + "sendReceivePrimitives".length() + 1; // Operation name
		op_size = PrimitiveTypesStruct.getMaxCdrSerializedSize(op_size);

		return op_size;
	}

	private int sendReceiveStruct_required_size() {
		int op_size = 0;

		op_size += 4 + CDRSerializer.alignment(op_size, Integer.SIZE); // MessageID
		op_size += 4 + CDRSerializer.alignment(op_size, 4) + "StructService".length() + 1; // Interface name
		op_size += 4 + CDRSerializer.alignment(op_size, 4) + "sendReceiveStruct".length() + 1; // Operation name
		op_size = OuterStruct.getMaxCdrSerializedSize(op_size);

		return op_size;
	}

	public PrimitiveTypesStruct sendReceivePrimitives(/*in*/ PrimitiveTypesStruct value) {
		if (m_ser != null && m_transport != null) {
			ByteBuffer buffer = ByteBuffer.allocate(sendReceivePrimitives_required_size());
			final TransportMessage trequest = m_transport.createTransportMessage(null);
	        trequest.setPayload(buffer);

	        final Object messageId = m_ser.getNewMessageId();
	        m_ser.serializeMessageId(trequest, messageId);
			m_ser.serializeService(trequest, "StructService");
			m_ser.serializeOperation(trequest, "sendReceivePrimitives");
			m_ser.serialize(trequest, "", value);
			trequest.getPayload().flip();

			final TransportMessageDispatcher dispatcher = new TransportMessageDispatcher(messageId, m_ser, m_transport);
	        m_transport.send(trequest);

	        try {
			TransportMessage tresponse = dispatcher.get();
			if (tresponse != null && tresponse.getPayload() != null) {
	                final ByteBuffer buf = tresponse.getPayload();
				buf.rewind();
				final Object responseMessageId = m_ser.deserializeMessageId(buf);

					PrimitiveTypesStruct ret = m_ser.deserialize(tresponse, "", PrimitiveTypesStruct.class);
					return ret;
	            }
	        } catch (Exception ex) {
	            throw new RuntimeException(ex);
	        }

		}

		return null;
	}

	public OuterStruct sendReceiveStruct(/*in*/ OuterStruct value) {
		if (m_ser != null && m_transport != null) {
			ByteBuffer buffer = ByteBuffer.allocate(sendReceiveStruct_required_size());
			final TransportMessage trequest = m_transport.createTransportMessage(null);
	        trequest.setPayload(buffer);

	        final Object messageId = m_ser.getNewMessageId();
	        m_ser.serializeMessageId(trequest, messageId);
			m_ser.serializeService(trequest, "StructService");
			m_ser.serializeOperation(trequest, "sendReceiveStruct");
			m_ser.serialize(trequest, "", value);
			trequest.getPayload().flip();

			final TransportMessageDispatcher dispatcher = new TransportMessageDispatcher(messageId, m_ser, m_transport);
	        m_transport.send(trequest);

	        try {
			TransportMessage tresponse = dispatcher.get();
			if (tresponse != null && tresponse.getPayload() != null) {
	                final ByteBuffer buf = tresponse.getPayload();
				buf.rewind();
				final Object responseMessageId = m_ser.deserializeMessageId(buf);

					OuterStruct ret = m_ser.deserialize(tresponse, "", OuterStruct.class);
					return ret;
	            }
	        } catch (Exception ex) {
	            throw new RuntimeException(ex);
	        }

		}

		return null;
	}


	public void sendReceivePrimitives(/*in*/ PrimitiveTypesStruct value, final sendReceivePrimitives_AsyncCallback callback) {

		if (m_ser != null && m_transport != null) {
			ByteBuffer buffer = ByteBuffer.allocate(sendReceivePrimitives_required_size());
			final TransportMessage trequest = m_transport.createTransportMessage(null);
	        trequest.setPayload(buffer);

	        final Object messageId = m_ser.getNewMessageId();
	        m_ser.serializeMessageId(trequest, messageId);
			m_ser.serializeService(trequest, "StructService");
			m_ser.serializeOperation(trequest, "sendReceivePrimitives");
			m_ser.serialize(trequest, "", value);

			trequest.getPayload().flip();

			final TransportMessageDispatcher dispatcher = new TransportMessageDispatcher(messageId, m_ser, m_transport);


			Futures.addCallback(dispatcher, new FutureCallback<TransportMessage> () {

				public void onSuccess(TransportMessage result) {
					final ByteBuffer buf = result.getPayload();
				buf.rewind();

					callback.process(result, m_ser);
				}

				public void onFailure(Throwable t) {
					callback.onFailure(t);
				}

			});

			m_transport.send(trequest);

		}

		return;
	}

	public void sendReceiveStruct(/*in*/ OuterStruct value, final sendReceiveStruct_AsyncCallback callback) {

		if (m_ser != null && m_transport != null) {
			ByteBuffer buffer = ByteBuffer.allocate(sendReceiveStruct_required_size());
			final TransportMessage trequest = m_transport.createTransportMessage(null);
	        trequest.setPayload(buffer);

	        final Object messageId = m_ser.getNewMessageId();
	        m_ser.serializeMessageId(trequest, messageId);
			m_ser.serializeService(trequest, "StructService");
			m_ser.serializeOperation(trequest, "sendReceiveStruct");
			m_ser.serialize(trequest, "", value);

			trequest.getPayload().flip();

			final TransportMessageDispatcher dispatcher = new TransportMessageDispatcher(messageId, m_ser, m_transport);


			Futures.addCallback(dispatcher, new FutureCallback<TransportMessage> () {

				public void onSuccess(TransportMessage result) {
					final ByteBuffer buf = result.getPayload();
				buf.rewind();

					callback.process(result, m_ser);
				}

				public void onFailure(Throwable t) {
					callback.onFailure(t);
				}

			});

			m_transport.send(trequest);

		}

		return;
	}


	private com.kiara.serialization.impl.SerializerImpl m_ser = null;
    private com.kiara.transport.impl.TransportImpl m_transport = null;


}
