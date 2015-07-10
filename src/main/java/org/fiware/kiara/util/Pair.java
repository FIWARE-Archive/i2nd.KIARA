package org.fiware.kiara.util;

public class Pair<T1, T2> {
    private T1 first;
    private T2 second;

    public Pair(T1 first, T2 second) { 
        this.first = first;
        this.second = second;
    }

    public T1 getFirst() { 
        return first; 
    }

    public T2 getSecond() { 
        return second;
    }

    public void copy(Pair<T1, T2> pair) {
        first = pair.first;
        second = pair.second;
    }
}
