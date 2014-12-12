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
 * @file TestServiceProxy.java
 * This file contains the proxy implementation.
 *
 * This file was generated by using the tool Kiaragen.
 *
 */


package com.kiara.complextypes;

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
class TestServiceProxy implements TestServiceClient {

	public TestServiceProxy(Serializer ser, Transport transport) {
		m_ser = (com.kiara.serialization.impl.SerializerImpl) ser;
		m_transport = (com.kiara.transport.impl.TransportImpl) transport;
	}


	private int return_param_func_required_size() {
		int op_size = 0;

		op_size += 4 + CDRSerializer.alignment(op_size, Integer.SIZE); // MessageID
		op_size += 4 + CDRSerializer.alignment(op_size, 4) + "TestService".length() + 1; // Interface name
		op_size += 4 + CDRSerializer.alignment(op_size, 4) + "return_param_func".length() + 1; // Operation name
		op_size = MyStruct.getMaxCdrSerializedSize(op_size);
		op_size += 4 + CDRSerializer.alignment(op_size, 4);

		return op_size;
	}

	private int only_param_func_required_size() {
		int op_size = 0;

		op_size += 4 + CDRSerializer.alignment(op_size, Integer.SIZE); // MessageID
		op_size += 4 + CDRSerializer.alignment(op_size, 4) + "TestService".length() + 1; // Interface name
		op_size += 4 + CDRSerializer.alignment(op_size, 4) + "only_param_func".length() + 1; // Operation name
		op_size = MyStruct.getMaxCdrSerializedSize(op_size);

		return op_size;
	}

	private int only_return_func_required_size() {
		int op_size = 0;

		op_size += 4 + CDRSerializer.alignment(op_size, Integer.SIZE); // MessageID
		op_size += 4 + CDRSerializer.alignment(op_size, 4) + "TestService".length() + 1; // Interface name
		op_size += 4 + CDRSerializer.alignment(op_size, 4) + "only_return_func".length() + 1; // Operation name

		return op_size;
	}

	private int oneway_return_param_func_required_size() {
		int op_size = 0;

		op_size += 4 + CDRSerializer.alignment(op_size, Integer.SIZE); // MessageID
		op_size += 4 + CDRSerializer.alignment(op_size, 4) + "TestService".length() + 1; // Interface name
		op_size += 4 + CDRSerializer.alignment(op_size, 4) + "oneway_return_param_func".length() + 1; // Operation name
		op_size = MyStruct.getMaxCdrSerializedSize(op_size);
		op_size += 4 + CDRSerializer.alignment(op_size, 4);

		return op_size;
	}

	private int oneway_only_param_func_required_size() {
		int op_size = 0;

		op_size += 4 + CDRSerializer.alignment(op_size, Integer.SIZE); // MessageID
		op_size += 4 + CDRSerializer.alignment(op_size, 4) + "TestService".length() + 1; // Interface name
		op_size += 4 + CDRSerializer.alignment(op_size, 4) + "oneway_only_param_func".length() + 1; // Operation name
		op_size = MyStruct.getMaxCdrSerializedSize(op_size);

		return op_size;
	}

	private int oneway_only_return_func_required_size() {
		int op_size = 0;

		op_size += 4 + CDRSerializer.alignment(op_size, Integer.SIZE); // MessageID
		op_size += 4 + CDRSerializer.alignment(op_size, 4) + "TestService".length() + 1; // Interface name
		op_size += 4 + CDRSerializer.alignment(op_size, 4) + "oneway_only_return_func".length() + 1; // Operation name

		return op_size;
	}

	public MyStruct return_param_func(/*in*/ MyStruct param1, /*in*/ int param2) {
		if (m_ser != null && m_transport != null) {
			ByteBuffer buffer = ByteBuffer.allocate(return_param_func_required_size());
			final TransportMessage trequest = m_transport.createTransportMessage(null);
	        trequest.setPayload(buffer);

	        final Object messageId = m_ser.getNewMessageId();
	        m_ser.serializeMessageId(trequest, messageId);
			m_ser.serializeService(trequest, "TestService");
			m_ser.serializeOperation(trequest, "return_param_func");
			m_ser.serialize(trequest, "", param1);
			m_ser.serializeI32(trequest, "", param2);
			trequest.getPayload().flip();

			final TransportMessageDispatcher dispatcher = new TransportMessageDispatcher(messageId, m_ser, m_transport);
	        m_transport.send(trequest);

	        try {
			TransportMessage tresponse = dispatcher.get();
			if (tresponse != null && tresponse.getPayload() != null) {
	                final ByteBuffer buf = tresponse.getPayload();
				buf.rewind();
				final Object responseMessageId = m_ser.deserializeMessageId(buf);

					MyStruct ret = m_ser.deserialize(tresponse, "", MyStruct.class);
					return ret;
	            }
	        } catch (Exception ex) {
	            throw new RuntimeException(ex);
	        }

		}

		return null;
	}

	public void only_param_func(/*in*/ MyStruct param1) {
		if (m_ser != null && m_transport != null) {
			ByteBuffer buffer = ByteBuffer.allocate(only_param_func_required_size());
			final TransportMessage trequest = m_transport.createTransportMessage(null);
	        trequest.setPayload(buffer);

	        final Object messageId = m_ser.getNewMessageId();
	        m_ser.serializeMessageId(trequest, messageId);
			m_ser.serializeService(trequest, "TestService");
			m_ser.serializeOperation(trequest, "only_param_func");
			m_ser.serialize(trequest, "", param1);
			trequest.getPayload().flip();

			final TransportMessageDispatcher dispatcher = new TransportMessageDispatcher(messageId, m_ser, m_transport);
	        m_transport.send(trequest);

	        try {
			TransportMessage tresponse = dispatcher.get();
			if (tresponse != null && tresponse.getPayload() != null) {
	                final ByteBuffer buf = tresponse.getPayload();
				buf.rewind();
				final Object responseMessageId = m_ser.deserializeMessageId(buf);

					return;
	            }
	        } catch (Exception ex) {
	            throw new RuntimeException(ex);
	        }

		}

		return;
	}

	public MyStruct only_return_func() {
		if (m_ser != null && m_transport != null) {
			ByteBuffer buffer = ByteBuffer.allocate(only_return_func_required_size());
			final TransportMessage trequest = m_transport.createTransportMessage(null);
	        trequest.setPayload(buffer);

	        final Object messageId = m_ser.getNewMessageId();
	        m_ser.serializeMessageId(trequest, messageId);
			m_ser.serializeService(trequest, "TestService");
			m_ser.serializeOperation(trequest, "only_return_func");
			trequest.getPayload().flip();

			final TransportMessageDispatcher dispatcher = new TransportMessageDispatcher(messageId, m_ser, m_transport);
	        m_transport.send(trequest);

	        try {
			TransportMessage tresponse = dispatcher.get();
			if (tresponse != null && tresponse.getPayload() != null) {
	                final ByteBuffer buf = tresponse.getPayload();
				buf.rewind();
				final Object responseMessageId = m_ser.deserializeMessageId(buf);

					MyStruct ret = m_ser.deserialize(tresponse, "", MyStruct.class);
					return ret;
	            }
	        } catch (Exception ex) {
	            throw new RuntimeException(ex);
	        }

		}

		return null;
	}

	public void oneway_return_param_func(/*in*/ MyStruct param1, /*in*/ int param2) {
		if (m_ser != null && m_transport != null) {
			ByteBuffer buffer = ByteBuffer.allocate(oneway_return_param_func_required_size());
			final TransportMessage trequest = m_transport.createTransportMessage(null);
	        trequest.setPayload(buffer);

	        final Object messageId = m_ser.getNewMessageId();
	        m_ser.serializeMessageId(trequest, messageId);
			m_ser.serializeService(trequest, "TestService");
			m_ser.serializeOperation(trequest, "oneway_return_param_func");
			m_ser.serialize(trequest, "", param1);
			m_ser.serializeI32(trequest, "", param2);
			trequest.getPayload().flip();

			final TransportMessageDispatcher dispatcher = new TransportMessageDispatcher(messageId, m_ser, m_transport);
	        m_transport.send(trequest);

	        try {
			TransportMessage tresponse = dispatcher.get();
			if (tresponse != null && tresponse.getPayload() != null) {
	                final ByteBuffer buf = tresponse.getPayload();
				buf.rewind();
				final Object responseMessageId = m_ser.deserializeMessageId(buf);

					return;
	            }
	        } catch (Exception ex) {
	            throw new RuntimeException(ex);
	        }

		}

		return;
	}

	public void oneway_only_param_func(/*in*/ MyStruct param1) {
		if (m_ser != null && m_transport != null) {
			ByteBuffer buffer = ByteBuffer.allocate(oneway_only_param_func_required_size());
			final TransportMessage trequest = m_transport.createTransportMessage(null);
	        trequest.setPayload(buffer);

	        final Object messageId = m_ser.getNewMessageId();
	        m_ser.serializeMessageId(trequest, messageId);
			m_ser.serializeService(trequest, "TestService");
			m_ser.serializeOperation(trequest, "oneway_only_param_func");
			m_ser.serialize(trequest, "", param1);
			trequest.getPayload().flip();

			final TransportMessageDispatcher dispatcher = new TransportMessageDispatcher(messageId, m_ser, m_transport);
	        m_transport.send(trequest);

	        try {
			TransportMessage tresponse = dispatcher.get();
			if (tresponse != null && tresponse.getPayload() != null) {
	                final ByteBuffer buf = tresponse.getPayload();
				buf.rewind();
				final Object responseMessageId = m_ser.deserializeMessageId(buf);

					return;
	            }
	        } catch (Exception ex) {
	            throw new RuntimeException(ex);
	        }

		}

		return;
	}

	public void oneway_only_return_func() {
		if (m_ser != null && m_transport != null) {
			ByteBuffer buffer = ByteBuffer.allocate(oneway_only_return_func_required_size());
			final TransportMessage trequest = m_transport.createTransportMessage(null);
	        trequest.setPayload(buffer);

	        final Object messageId = m_ser.getNewMessageId();
	        m_ser.serializeMessageId(trequest, messageId);
			m_ser.serializeService(trequest, "TestService");
			m_ser.serializeOperation(trequest, "oneway_only_return_func");
			trequest.getPayload().flip();

			final TransportMessageDispatcher dispatcher = new TransportMessageDispatcher(messageId, m_ser, m_transport);
	        m_transport.send(trequest);

	        try {
			TransportMessage tresponse = dispatcher.get();
			if (tresponse != null && tresponse.getPayload() != null) {
	                final ByteBuffer buf = tresponse.getPayload();
				buf.rewind();
				final Object responseMessageId = m_ser.deserializeMessageId(buf);

					return;
	            }
	        } catch (Exception ex) {
	            throw new RuntimeException(ex);
	        }

		}

		return;
	}


	public void return_param_func(/*in*/ MyStruct param1, /*in*/ int param2, final return_param_func_AsyncCallback callback) {

		if (m_ser != null && m_transport != null) {
			ByteBuffer buffer = ByteBuffer.allocate(return_param_func_required_size());
			final TransportMessage trequest = m_transport.createTransportMessage(null);
	        trequest.setPayload(buffer);

	        final Object messageId = m_ser.getNewMessageId();
	        m_ser.serializeMessageId(trequest, messageId);
			m_ser.serializeService(trequest, "TestService");
			m_ser.serializeOperation(trequest, "return_param_func");
			m_ser.serialize(trequest, "", param1);
			m_ser.serializeI32(trequest, "", param2);

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

	public void only_param_func(/*in*/ MyStruct param1, final only_param_func_AsyncCallback callback) {

		if (m_ser != null && m_transport != null) {
			ByteBuffer buffer = ByteBuffer.allocate(100);
			final TransportMessage trequest = m_transport.createTransportMessage(null);
	        trequest.setPayload(buffer);

	        final Object messageId = m_ser.getNewMessageId();
	        m_ser.serializeMessageId(trequest, messageId);
			m_ser.serializeService(trequest, "TestService");
			m_ser.serializeOperation(trequest, "only_param_func");
			m_ser.serialize(trequest, "", param1);

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

	public void only_return_func(final only_return_func_AsyncCallback callback) {

		if (m_ser != null && m_transport != null) {
			ByteBuffer buffer = ByteBuffer.allocate(100);
			final TransportMessage trequest = m_transport.createTransportMessage(null);
	        trequest.setPayload(buffer);

	        final Object messageId = m_ser.getNewMessageId();
	        m_ser.serializeMessageId(trequest, messageId);
			m_ser.serializeService(trequest, "TestService");
			m_ser.serializeOperation(trequest, "only_return_func");

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