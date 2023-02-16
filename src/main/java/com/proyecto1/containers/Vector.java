package com.proyecto1.containers;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author sebas
 * Esta clase Vector es una estructura secuencial que permite funcionalidades
 * de colas, pilas y vectores
 */
public class Vector<T> implements Iterable<T> {
    T[] data;
    private static final int NEW_SIZE = 4;
    private int size;
    private int capacity;

    public Vector() {
        this.size = 0;
        this.capacity = Vector.NEW_SIZE;
        this.data = (T[])new Object[this.capacity];
    }

    public Iterator<T> iterator() {
        return new VectorIterator<>(this);
    }

    public T Get(int pos) {
        if (pos < 0 || pos > (this.size - 1)) {
            return null;
        }
        return this.data[pos];
    }

    public int size() {
        return this.size;
    }

    public int capacity() {
        return this.capacity;
    }

    public boolean empty() {
        return this.size == 0;
    }

    public boolean remove(int pos) {
        if (this.empty() || pos > (this.size - 1) || pos < 0) return false;

        System.arraycopy(this.data, pos + 1, this.data, pos, this.data.length - pos - 1);

        this.size--;

        return true;
    }

    public boolean reserve(int newCap) {
        if (this.capacity <= newCap) {
            return false;
        }
        this.capacity = newCap;
        this.data = Arrays.copyOf(this.data, this.capacity);
        return true;
    }

    public void clear() {
        Arrays.fill(this.data, null);
    }

    public void pushBack(T v) {
        this.needCapacity();

        this.data[this.size] = v;
        this.size++;
    }

    public void pushFront(T v) {
        this.needCapacity();

        System.arraycopy(this.data, 0, this.data, 1, this.data.length - 1);
        this.data[0] = v;
        this.size++;
    }

    public boolean insert(T v, int pos) {
        if (pos > (this.size - 1) || pos < 0) return false;

        this.needCapacity();

        System.arraycopy(this.data, pos, this.data, pos + 1, this.data.length - 1 - pos);
        this.data[pos] = v;
        this.size++;
        return true;
    }

    public boolean popBack() {
        if (this.size == 0) return false;

        this.size--;
        this.data[this.size] = null;

        return true;
    }

    public T popFront() {
        if (this.size == 0) return null;

        T poped = this.data[0];
        System.arraycopy(this.data, 1, this.data, 0, this.data.length - 1);
        this.size--;
        return poped;
    }

    private void needCapacity() {
        if (this.size == this.capacity) {
            this.capacity += Vector.NEW_SIZE;
            this.data = Arrays.copyOf(this.data, this.capacity);
        }
    }
}

class VectorIterator<T> implements Iterator<T> {
    int i;
    int size;
    T[] data;
    public VectorIterator(Vector<T> vector) {
        this.data = vector.data;
        this.i = 0;
        this.size = vector.size();
    }

    @Override
    public boolean hasNext() {
        return this.i < this.size;
    }

    @Override
    public T next() {
        T v = this.data[this.i];
        this.i++;
        return v;
    }
}