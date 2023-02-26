package com.proyecto1.containers;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author sebas
 *         Esta clase Vector es una estructura secuencial que permite
 *         funcionalidades
 *         de colas, pilas y vectores
 *  @param <T> Tipo de dato del contenedor
 */
public class Vector<T> implements Iterable<T> {
    public T[] data;
    private static final int NEW_SIZE = 4;
    private int size;
    private int capacity;

    /**
     * Inicializa un vector vacio con la capacidad basica
     */
    public Vector() {
        init();
    }

    /**
     * Inicializa un vector vacio con la capacidad solicitada
     * @param c Capacidad solicitada
     */
    public Vector(int c) {
        init();
        this.reserve(c);
    }

    public void pushBack(T[] arr) {
        this.reserve(arr.length);
        for (T v : arr)
            this.pushBack(v);
    }

    private void init() {
        this.size = 0;
        this.capacity = Vector.NEW_SIZE;
        this.data = (T[]) new Object[this.capacity];
    }

    public Iterator<T> iterator() {
        return new VectorIterator<>(this);
    }

    /**
     * @param pos Posicion del elemento que se quiere acceder
     * @return Si el elemento existe se le retornara el elemento, por contrario Null
     */
    public T get(int pos) {
        if (pos < 0 || pos > (this.size - 1)) {
            return null;
        }
        return this.data[pos];
    }

    /**
     * @param pos Posicion del elemento que se quiere acceder
     * @param value Valor para nuevo para el elemento
     * @return Si no exite el elemento se retornara Null, por contrario se retornara el nuevo valor del elemento
     */
    public T set(int pos, T value) {
        if (pos < 0 || pos > (this.size - 1)) {
            return null;
        }
        return this.data[pos] = value;
    }

    /**
     * @return Tamono del vector
     */
    public int size() {
        return this.size;
    }

    /**
     * @return Capacidad del vector
     */
    public int capacity() {
        return this.capacity;
    }

    /**
     * @return True si es vacio, false de lo contrario
     */
    public boolean empty() {
        return this.size == 0;
    }

    /**
     * @param pos Posicion del elemento a eliminar
     * @return True si se elimino, false de lo contrario
     */
    public boolean remove(int pos) {
        if (this.empty() || pos > (this.size - 1) || pos < 0)
            return false;

        System.arraycopy(this.data, pos + 1, this.data, pos, this.data.length - pos - 1);

        this.size--;

        return true;
    }

    /**
     * @param newCap Capacidad nueva
     * @return True si se seteo la nueva capacidad, false de lo contrario
     */
    public boolean reserve(int newCap) {
        if (this.capacity <= newCap) {
            return false;
        }
        this.capacity = newCap;
        this.data = Arrays.copyOf(this.data, this.capacity);
        return true;
    }

    /**
     * Vaciar vector
     */
    public void clear() {
        Arrays.fill(this.data, null);
        this.size = 0;
    }

    /**
     * @param v Elemento nuevo en el final del vector
     */
    public void pushBack(T v) {
        this.needCapacity();

        this.data[this.size] = v;
        this.size++;
    }

    /**
     * Elimina el primer elemento en el frente del vector
     * @return Elemento eliminado
     */
    public T popFront() {
        if (this.size == 0)
            return null;

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

/**
 * Implementacion de iterador para la clase Vector
 * @param <T> Tipo de dato del contendedor
 */
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