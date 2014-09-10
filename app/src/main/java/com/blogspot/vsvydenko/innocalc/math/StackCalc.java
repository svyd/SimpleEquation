package com.blogspot.vsvydenko.innocalc.math;

import java.util.LinkedList;

/**
 * Created by vsvydenko on 10.09.14.
 */
public class StackCalc<T> {

    private LinkedList<T> storage = new LinkedList<T>();

    public void push(T item) {
        storage.addFirst(item);
    }

    public T pop() {
        return storage.removeFirst();
    }

    public boolean isEmpty() {
        return storage.isEmpty();
    }

    public int size(){
        return storage.size();
    }

    public void clear() {
        storage.clear();
    }

}
