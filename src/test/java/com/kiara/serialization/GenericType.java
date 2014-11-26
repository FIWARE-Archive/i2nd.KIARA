package com.kiara.serialization;

import com.kiara.serialization.impl.Serializable;
import com.kiara.serialization.impl.SerializerImpl;
import com.kiara.transport.impl.TransportMessage;

public class GenericType implements Serializable {

    private int myInt;
    private String myString;

    public GenericType() {
        this.myInt =0;
        this.myString = "";
    }

    public GenericType(int myInt, String myString) {
        this.myInt = myInt;
        this.myString = myString;
    }

    public void serialize(SerializerImpl impl, TransportMessage message, String name) {
        impl.serializeI32(message, name, this.myInt);
        impl.serializeString(message, this.myString);
    }

    public void deserialize(SerializerImpl impl, TransportMessage message,String name) {
        this.myInt = impl.deserializeI32(message, name);
        this.myString = impl.deserializeString(message);
    }

    public boolean equals(GenericType other) {
        if (other.myInt == this.myInt && other.myString.compareTo(this.myString) == 0) {
            return true;
        }

        return false;
    }

    public String getClassName() {
        return "GenericType";
    }

}
