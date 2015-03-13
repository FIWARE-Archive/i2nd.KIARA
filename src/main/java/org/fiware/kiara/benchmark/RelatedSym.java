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
package org.fiware.kiara.benchmark;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import org.fiware.kiara.serialization.impl.BasicSerializers;
import org.fiware.kiara.serialization.impl.BinaryInputStream;
import org.fiware.kiara.serialization.impl.BinaryOutputStream;

import org.fiware.kiara.serialization.impl.Serializable;
import org.fiware.kiara.serialization.impl.SerializerImpl;
import org.fiware.kiara.serialization.impl.CDRSerializer;
import org.fiware.kiara.serialization.impl.ListAsArraySerializer;
import org.fiware.kiara.serialization.impl.ListAsSequenceSerializer;
import org.fiware.kiara.serialization.impl.Serializer;
import org.fiware.kiara.serialization.impl.MapAsMapSerializer;
import org.fiware.kiara.serialization.impl.SetAsSetSerializer;
import org.fiware.kiara.serialization.impl.ObjectSerializer;
import org.fiware.kiara.serialization.impl.EnumSerializer;

/**
 * Class definition for the user defined type RelatedSym.
 *
 * @author Kiaragen tool.
 *
 */
public class RelatedSym implements Serializable {

    /*
     *	Attributes
     */
    private double symbol;
    private long orderQuantity;
    private int side;
    private long transactTime;
    private int quoteType;
    private int securityID;
    private int securityIDSource;
    private double dummy1;
    private int dummy2;

    /*
     *      Attribute Serializers
     */
    /*
     *	Default constructor
     */
    public RelatedSym() {
        this.symbol = 0.0;
        this.orderQuantity = 0;
        this.side = 0;
        this.transactTime = 0;
        this.quoteType = 0;
        this.securityID = 0;
        this.securityIDSource = 0;
        this.dummy1 = 0.0;
        this.dummy2 = 0;
    }

    /*
     * This method serializes a RelatedSym.
     *
     * @see org.fiware.kiara.serialization.impl.Serializable#serialize(org.fiware.kiara.serialization.impl.SerializerImpl, org.fiware.kiara.serialization.impl.BinaryOutputStream, java.lang.String)
     */
    @Override
    public void serialize(SerializerImpl impl, BinaryOutputStream message, String name) throws IOException {
        impl.serializeFloat64(message, name, this.symbol);
        impl.serializeUI64(message, name, this.orderQuantity);
        impl.serializeUI32(message, name, this.side);
        impl.serializeUI64(message, name, this.transactTime);
        impl.serializeUI32(message, name, this.quoteType);
        impl.serializeUI32(message, name, this.securityID);
        impl.serializeUI32(message, name, this.securityIDSource);
        impl.serializeFloat64(message, name, this.dummy1);
        impl.serializeI32(message, name, this.dummy2);
    }

    /*
     * This method deserializes a RelatedSym.
     *
     * @see org.fiware.kiara.serialization.impl.Serializable#deserialize(org.fiware.kiara.serialization.impl.SerializerImpl, org.fiware.kiara.serialization.impl.BinaryInputStream, java.lang.String)
     */
    @Override
    public void deserialize(SerializerImpl impl, BinaryInputStream message, String name) throws IOException {
        this.symbol = impl.deserializeFloat64(message, name);
        this.orderQuantity = impl.deserializeUI64(message, name);
        this.side = impl.deserializeUI32(message, name);
        this.transactTime = impl.deserializeUI64(message, name);
        this.quoteType = impl.deserializeUI32(message, name);
        this.securityID = impl.deserializeUI32(message, name);
        this.securityIDSource = impl.deserializeUI32(message, name);
        this.dummy1 = impl.deserializeFloat64(message, name);
        this.dummy2 = impl.deserializeI32(message, name);
    }

    /*
     * @param other An object instance of Object
     */
    @Override
    public boolean equals(Object other) {
        boolean comparison = true;

        if (other instanceof RelatedSym) {

            comparison = comparison && (this.symbol == ((RelatedSym) other).symbol);

            comparison = comparison && (this.orderQuantity == ((RelatedSym) other).orderQuantity);

            comparison = comparison && (this.side == ((RelatedSym) other).side);

            comparison = comparison && (this.transactTime == ((RelatedSym) other).transactTime);

            comparison = comparison && (this.quoteType == ((RelatedSym) other).quoteType);

            comparison = comparison && (this.securityID == ((RelatedSym) other).securityID);

            comparison = comparison && (this.securityIDSource == ((RelatedSym) other).securityIDSource);

            comparison = comparison && (this.dummy1 == ((RelatedSym) other).dummy1);

            comparison = comparison && (this.dummy2 == ((RelatedSym) other).dummy2);

        }

        return comparison;
    }

    /*
     * Method to get the attribute symbol.
     */
    public double getSymbol() {
        return this.symbol;
    }

    /*
     * Method to set the attribute symbol.
     */
    public void setSymbol(double symbol) {
        this.symbol = symbol;
    }

    /*
     * Method to get the attribute orderQuantity.
     */
    public long getOrderQuantity() {
        return this.orderQuantity;
    }

    /*
     * Method to set the attribute orderQuantity.
     */
    public void setOrderQuantity(long orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    /*
     * Method to get the attribute side.
     */
    public int getSide() {
        return this.side;
    }

    /*
     * Method to set the attribute side.
     */
    public void setSide(int side) {
        this.side = side;
    }

    /*
     * Method to get the attribute transactTime.
     */
    public long getTransactTime() {
        return this.transactTime;
    }

    /*
     * Method to set the attribute transactTime.
     */
    public void setTransactTime(long transactTime) {
        this.transactTime = transactTime;
    }

    /*
     * Method to get the attribute quoteType.
     */
    public int getQuoteType() {
        return this.quoteType;
    }

    /*
     * Method to set the attribute quoteType.
     */
    public void setQuoteType(int quoteType) {
        this.quoteType = quoteType;
    }

    /*
     * Method to get the attribute securityID.
     */
    public int getSecurityID() {
        return this.securityID;
    }

    /*
     * Method to set the attribute securityID.
     */
    public void setSecurityID(int securityID) {
        this.securityID = securityID;
    }

    /*
     * Method to get the attribute securityIDSource.
     */
    public int getSecurityIDSource() {
        return this.securityIDSource;
    }

    /*
     * Method to set the attribute securityIDSource.
     */
    public void setSecurityIDSource(int securityIDSource) {
        this.securityIDSource = securityIDSource;
    }

    /*
     * Method to get the attribute dummy1.
     */
    public double getDummy1() {
        return this.dummy1;
    }

    /*
     * Method to set the attribute dummy1.
     */
    public void setDummy1(double dummy1) {
        this.dummy1 = dummy1;
    }

    /*
     * Method to get the attribute dummy2.
     */
    public int getDummy2() {
        return this.dummy2;
    }

    /*
     * Method to set the attribute dummy2.
     */
    public void setDummy2(int dummy2) {
        this.dummy2 = dummy2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.symbol, this.orderQuantity, this.side, this.transactTime, this.quoteType, this.securityID, this.securityIDSource, this.dummy1, this.dummy2);
    }
}