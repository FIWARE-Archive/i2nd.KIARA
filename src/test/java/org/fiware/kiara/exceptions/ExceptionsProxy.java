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
 * @file ExceptionsTestProxy.java
 * This file contains the proxy implementation.
 *
 * This file was generated by using the tool Kiaragen.
 *
 */
 
 
package org.fiware.kiara.exceptions;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import org.fiware.kiara.netty.TransportMessageDispatcher;
import org.fiware.kiara.serialization.Serializer;
import org.fiware.kiara.serialization.impl.CDRSerializer;
import org.fiware.kiara.transport.Transport;
import org.fiware.kiara.transport.impl.TransportMessage;

import java.nio.ByteBuffer;

/**
 * Class containing the proxy implementation for all the services. 
 *
 * @author Kiaragen tool.
 *
 */
class ExceptionsProxy implements ExceptionsClient {
	
	public ExceptionsProxy(Serializer ser, Transport transport) {
		m_ser = (org.fiware.kiara.serialization.impl.SerializerImpl) ser;
		m_transport = (org.fiware.kiara.transport.impl.TransportImpl) transport;
	}
	

	private int divide_required_size() {
		int op_size = 0;
		
		op_size += 4 + CDRSerializer.alignment(op_size, Integer.SIZE); // MessageID
		op_size += 4 + CDRSerializer.alignment(op_size, 4) + "ExceptionsTest".length() + 1; // Interface name
		op_size += 4 + CDRSerializer.alignment(op_size, 4) + "divide".length() + 1; // Operation name
		op_size += 4 + CDRSerializer.alignment(op_size, 4);
		op_size += 4 + CDRSerializer.alignment(op_size, 4);
		
		return op_size;
	}

	private int function_required_size() {
		int op_size = 0;
		
		op_size += 4 + CDRSerializer.alignment(op_size, Integer.SIZE); // MessageID
		op_size += 4 + CDRSerializer.alignment(op_size, 4) + "ExceptionsTest".length() + 1; // Interface name
		op_size += 4 + CDRSerializer.alignment(op_size, 4) + "function".length() + 1; // Operation name
		
		return op_size;
	}
	
	public float divide(/*in*/ float n1, /*in*/ float n2) {	
		if (m_ser != null && m_transport != null) {
			ByteBuffer buffer = ByteBuffer.allocate(divide_required_size());
			final TransportMessage trequest = m_transport.createTransportMessage(null);
	        trequest.setPayload(buffer);
	        
	        final Object messageId = m_ser.getNewMessageId(); 
	        m_ser.serializeMessageId(trequest, messageId);   
			m_ser.serializeService(trequest, "ExceptionsTest");
			m_ser.serializeOperation(trequest, "divide");
			m_ser.serializeFloat32(trequest, "", n1);
			m_ser.serializeFloat32(trequest, "", n2);
			trequest.getPayload().flip();
			
			final TransportMessageDispatcher dispatcher = new TransportMessageDispatcher(messageId, m_ser, m_transport);
	        m_transport.send(trequest);
	        
	        try {
	        	TransportMessage tresponse = dispatcher.get();
	        	if (tresponse != null && tresponse.getPayload() != null) {
	                final ByteBuffer buf = tresponse.getPayload();
	        		buf.rewind();
	        		
	        		// Deserialize response message ID
	        		final Object responseMessageId = m_ser.deserializeMessageId(buf);
	        		
	        		// Deserialize return code (0 = OK, anything else = WRONG)
	        		int retCode = m_ser.deserializeUI32(tresponse, "");
	        		if (retCode == 0) { // Function execution was OK.
						float ret = m_ser.deserializeFloat32(tresponse, "");
						return ret;
		            } else {

		    			String name = m_ser.deserializeString(tresponse, "");

		        	        if (name.equals("DividedByZeroException")) {
		        	        	DividedByZeroException exception = new DividedByZeroException();
		        	        	exception.deserialize(m_ser, tresponse, "");
		        	        	throw exception;
		        	        }
		        	}
					
				}
	        } catch (Exception ex) {
	            throw new RuntimeException(ex);
	        }
			
		}
		
		return (float) 0.0;
	}

	public int function() {	
		if (m_ser != null && m_transport != null) {
			ByteBuffer buffer = ByteBuffer.allocate(function_required_size());
			final TransportMessage trequest = m_transport.createTransportMessage(null);
	        trequest.setPayload(buffer);
	        
	        final Object messageId = m_ser.getNewMessageId(); 
	        m_ser.serializeMessageId(trequest, messageId);   
			m_ser.serializeService(trequest, "ExceptionsTest");
			m_ser.serializeOperation(trequest, "function");
			trequest.getPayload().flip();
			
			final TransportMessageDispatcher dispatcher = new TransportMessageDispatcher(messageId, m_ser, m_transport);
	        m_transport.send(trequest);
	        
	        try {
	        	TransportMessage tresponse = dispatcher.get();
	        	if (tresponse != null && tresponse.getPayload() != null) {
	                final ByteBuffer buf = tresponse.getPayload();
	        		buf.rewind();
	        		
	        		// Deserialize response message ID
	        		final Object responseMessageId = m_ser.deserializeMessageId(buf);
	        		
	        		// Deserialize return code (0 = OK, anything else = WRONG)
	        		int retCode = m_ser.deserializeUI32(tresponse, "");
	        		if (retCode == 0) { // Function execution was OK.
						int ret = m_ser.deserializeI32(tresponse, "");
						return ret;
		            } else {

		    			String name = m_ser.deserializeString(tresponse, "");

		        	        if (name.equals("ExceptionOne")) {
		        	        	FirstException exception = new FirstException();
		        	        	exception.deserialize(m_ser, tresponse, "");
		        	        	throw exception;
		        	        }

		        	        if (name.equals("ExceptionTwo")) {
		        	        	SecondException exception = new SecondException();
		        	        	exception.deserialize(m_ser, tresponse, "");
		        	        	throw exception;
		        	        }
		        	}
					
				}
	        } catch (Exception ex) {
	            throw new RuntimeException(ex);
	        }
			
		}
		
		return 0;
	}

	
	public void divide(/*in*/ float n1, /*in*/ float n2, final divide_AsyncCallback callback) {
		
		if (m_ser != null && m_transport != null) {
			ByteBuffer buffer = ByteBuffer.allocate(divide_required_size());
			final TransportMessage trequest = m_transport.createTransportMessage(null);
	        trequest.setPayload(buffer);
	        
	        final Object messageId = m_ser.getNewMessageId(); 
	        m_ser.serializeMessageId(trequest, messageId);   
			m_ser.serializeService(trequest, "ExceptionsTest");
			m_ser.serializeOperation(trequest, "divide");
			m_ser.serializeFloat32(trequest, "", n1);
			m_ser.serializeFloat32(trequest, "", n2);
			
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

	public void function(final function_AsyncCallback callback) {
		
		if (m_ser != null && m_transport != null) {
			ByteBuffer buffer = ByteBuffer.allocate(function_required_size());
			final TransportMessage trequest = m_transport.createTransportMessage(null);
	        trequest.setPayload(buffer);
	        
	        final Object messageId = m_ser.getNewMessageId(); 
	        m_ser.serializeMessageId(trequest, messageId);   
			m_ser.serializeService(trequest, "ExceptionsTest");
			m_ser.serializeOperation(trequest, "function");
			
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

	
	private org.fiware.kiara.serialization.impl.SerializerImpl m_ser = null;
    private org.fiware.kiara.transport.impl.TransportImpl m_transport = null;
    
    
}
