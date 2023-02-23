package com.proyecto1.containers;

/**
 * @author sebas
 * @param <T> First type
 * @param <U> Second type
 */
public class Pair<T, U> {
    public T primary;
    public U second;

    public Pair(T primary, U second) {
        this.primary = primary;
        this.second = second;
    }
}
